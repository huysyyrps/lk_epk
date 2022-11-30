package com.example.lk_epk.util

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lk_epk.MyApplication
import com.example.lk_epk.R
import com.example.lk_epk.tcp.SocketBack
import com.example.lk_epk.util.Constant.TAG_TEN
import com.xuhao.didi.core.iocore.interfaces.IPulseSendable
import com.xuhao.didi.core.iocore.interfaces.ISendable
import com.xuhao.didi.core.pojo.OriginalData
import com.xuhao.didi.socket.client.sdk.OkSocket
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo
import com.xuhao.didi.socket.client.sdk.client.OkSocketOptions
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager
import java.nio.charset.Charset
import java.util.*


abstract class BaseFragment : Fragment(), SocketBack {
    lateinit var manager: IConnectionManager
    lateinit var activityContext: Context
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(getLayout(), container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化控件
        initView()
        initData()
        //链接socket
        connectSocket()
    }

    fun getActivityContext(context: Context) {
        activityContext = context
    }

    val txglqSockerAdapter: SocketActionAdapter = object : SocketActionAdapter() {
        override fun onSocketConnectionSuccess(info: ConnectionInfo?, action: String?) {
            LogUtil.i("BaseFragment-ConnectionSuccess", "连接成功")
            ProgressDialogUtil.stopLoad()
            resources.getString(R.string.connect_success).showToast(MyApplication.context)
        }

        override fun onSocketDisconnection(info: ConnectionInfo?, action: String?, e: Exception?) {
            LogUtil.i("BaseFragment-Disconnection", "连接断开")
            ProgressDialogUtil.stopLoad()
            resources.getString(R.string.connect_discon).showToast(MyApplication.context)
        }

        override fun onSocketConnectionFailed(info: ConnectionInfo?, action: String?, e: Exception?) {
            LogUtil.i("BaseFragment-ConnectionFailed", "连接失败")
            ProgressDialogUtil.stopLoad()
            resources.getString(R.string.connect_fail1).showToast(MyApplication.context,5)
            manager.disconnect();//断开连接
            manager.unRegisterReceiver(this)// 取消注册监听
        }

        override fun onSocketReadResponse(
            info: ConnectionInfo?,
            action: String?,
            data: OriginalData
        ) {
            LogUtil.i("BaseFragment", "123")
            val str = String(data.bodyBytes, Charset.forName("utf-8"))
            //接受的数据
            LogUtil.i("BaseFragment-ReadResponse", str)
        }

        override fun onSocketWriteResponse(
            info: ConnectionInfo?,
            action: String?,
            data: ISendable
        ) {
            var bytes = data.parse()
            bytes = Arrays.copyOfRange(bytes, 0, bytes.size)
            val str = String(bytes, Charset.forName("utf-8")) //发出的数据
            LogUtil.i("BaseFragment-WriteResponse", str)
            writeData(str)
        }

        override fun onPulseSend(info: ConnectionInfo?, data: IPulseSendable?) {
            LogUtil.i("BaseFragment", "收到心跳,喂狗成功")
            manager.getPulseManager().feed()
        }
    }

    //链接socket
    fun connectSocket() {
        ProgressDialogUtil.startLoad(activityContext,resources.getString(R.string.connect_loding))
        var connectionInfo = ConnectionInfo("172.16.20.5", 5005)
        val builder = OkSocketOptions.Builder()
        //设置重连
        builder.setReconnectionManager(OkSocketOptions.getDefault().reconnectionManager)
        builder.setPulseFeedLoseTimes(2)
        builder.setConnectTimeoutSecond(TAG_TEN)
        //调用OkSocket,开启这次连接的通道,拿到通道Manager
        manager = OkSocket.open(connectionInfo)
        manager.option(builder.build());
        manager.registerReceiver(txglqSockerAdapter)
        //调用通道进行连接
        manager.connect()
    }

    //初始化布局
    abstract fun getLayout(): Int
    abstract fun initView()
    abstract fun initData()
}