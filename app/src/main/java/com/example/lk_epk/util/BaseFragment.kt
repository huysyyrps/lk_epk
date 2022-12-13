package com.example.lk_epk.util

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lk_epk.MyApplication
import com.example.lk_epk.R
import com.example.lk_epk.tcp.NettyClientListener
import com.example.lk_epk.tcp.NettyTcpClient
import com.example.lk_epk.tcp.SocketBack
import com.xuhao.didi.core.iocore.interfaces.IPulseSendable
import com.xuhao.didi.core.iocore.interfaces.ISendable
import com.xuhao.didi.core.pojo.OriginalData
import com.xuhao.didi.socket.client.sdk.OkSocket
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.Charset
import java.util.*


abstract class BaseFragment : Fragment(), SocketBack, NettyClientListener<String> {
//    lateinit var manager: IConnectionManager
    lateinit var activityContext: Context
    private var ip: String = "172.16.20.5"
    private var port: Int = 5005
    lateinit var nettyTcpClient: NettyTcpClient

    companion object {
        private val TAG = "BaseFragment"
    }

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

//    val txglqSockerAdapter: SocketActionAdapter = object : SocketActionAdapter() {
//        override fun onSocketConnectionSuccess(info: ConnectionInfo?, action: String?) {
//            LogUtil.i("BaseFragment-ConnectionSuccess", "连接成功")
//            ProgressDialogUtil.stopLoad()
//            resources.getString(R.string.connect_success).showToast(MyApplication.context)
//            //连接成功,开启心跳
//            OkSocket.open(info)
//                .pulseManager
//                .setPulseSendable {
//                    val body = "1234".toByteArray(Charset.defaultCharset()) // 心跳数据
//                    val bb: ByteBuffer = ByteBuffer.allocate(4 + body.size)
//                    bb.order(ByteOrder.BIG_ENDIAN)
//                    bb.putInt(body.size)
//                    bb.put(body)
//                    bb.array()
//                }
//                .pulse() //开始心跳,开始心跳后,心跳管理器会自动进行心跳触发
//
//        }
//
//        override fun onSocketDisconnection(info: ConnectionInfo?, action: String?, e: Exception?) {
//            LogUtil.i("BaseFragment-Disconnection", "连接断开")
//            ProgressDialogUtil.stopLoad()
//            resources.getString(R.string.connect_discon).showToast(MyApplication.context)
//        }
//
//        override fun onSocketConnectionFailed(
//            info: ConnectionInfo?,
//            action: String?,
//            e: Exception?
//        ) {
//            LogUtil.i("BaseFragment-ConnectionFailed", "连接失败")
//            ProgressDialogUtil.stopLoad()
//            resources.getString(R.string.connect_fail1).showToast(MyApplication.context, 5)
//            manager.disconnect();//断开连接
//            manager.unRegisterReceiver(this)// 取消注册监听
//        }
//
//        override fun onSocketReadResponse(
//            info: ConnectionInfo?,
//            action: String?,
//            data: OriginalData
//        ) {
//            LogUtil.i("BaseFragment", "123")
//            val str = String(data.bodyBytes, Charset.forName("utf-8"))
//            //接受的数据
//            LogUtil.i("BaseFragment-ReadResponse", str)
//        }
//
//        override fun onSocketWriteResponse(
//            info: ConnectionInfo?,
//            action: String?,
//            data: ISendable
//        ) {
//            var bytes = data.parse()
//            bytes = Arrays.copyOfRange(bytes, 0, bytes.size)
//            val str = String(bytes, Charset.forName("utf-8")) //发出的数据
//            LogUtil.i("BaseFragment-WriteResponse", str)
//            writeData(str)
//        }
//
//        override fun onPulseSend(info: ConnectionInfo?, data: IPulseSendable?) {
//            LogUtil.i("BaseFragment", "收到心跳,喂狗成功")
//            manager.getPulseManager().feed()
//        }
//    }

    //链接socket
    fun connectSocket() {
        nettyTcpClient = NettyTcpClient.Builder()
            .setHost(ip)                    //设置服务端地址
            .setTcpPort(port)               //设置服务端端口号
            .setMaxReconnectTimes(5)        //设置最大重连次数
            .setReconnectIntervalTime(5)    //设置重连间隔时间。单位：秒
            .setSendheartBeat(true)        //设置发送心跳
            .setHeartBeatInterval(5)        //设置心跳间隔时间。单位：秒
            .setHeartBeatData("I'm is HeartBeatData") //设置心跳数据，可以是String类型，也可以是byte[]，以后设置的为准
            .setIndex(0)                    //设置客户端标识.(因为可能存在多个tcp连接)
            .build()

        nettyTcpClient.setListener(this) //设置TCP监听
        connect()
//        manager = OkSocketManager.manager
//        if (::manager.isInitialized && manager.isConnect) {
//            resources.getString(R.string.connect_succeeded).showToast(MyApplication.context)
//        } else {
//            manager.registerReceiver(txglqSockerAdapter)
//            //调用通道进行连接
//            manager.connect()
//        }
    }

    private fun connect() {
        Log.d(TAG, "connect")
        if (!nettyTcpClient.connectStatus) {
            nettyTcpClient.connect()//连接服务器
        } else {
            nettyTcpClient.disconnect()
        }
    }

    override fun onMessageResponseClient(msg: String, index: Int) {
        Log.d(TAG, "onMessageResponse:$msg")
    }

    override fun onClientStatusConnectChanged(statusCode: Int, index: Int) {
        if (statusCode == Constant.STATUS_CONNECT_SUCCESS) {
            Log.d(TAG, "STATUS_CONNECT_SUCCESS:")
        } else {
            Log.d(TAG, "onServiceStatusConnectChanged:$statusCode")
        }
    }
    fun disconnect(view: View) {
        nettyTcpClient.disconnect()
    }

    //初始化布局
    abstract fun getLayout(): Int
    abstract fun initView()
    abstract fun initData()
}