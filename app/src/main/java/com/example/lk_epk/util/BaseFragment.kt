package com.example.lk_epk.util

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lk_epk.tcp.NettyClientListener
import com.example.lk_epk.tcp.NettyTcpClient
import com.example.lk_epk.tcp.SocketBack


abstract class BaseFragment : Fragment(), NettyClientListener<String>, SocketBack {
//    lateinit var manager: IConnectionManager
    lateinit var activityContext: Context
    private var ip: String = "172.16.20.5"
    private var port: Int = 5005
    lateinit var nettyTcpClient: NettyTcpClient
    var connectStatue: Boolean = false

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
        messageResponse(msg)
    }

    override fun onClientStatusConnectChanged(statusCode: Int, index: Int) {
        connectStatue = if (statusCode == Constant.STATUS_CONNECT_SUCCESS) {
            Log.d(TAG, "STATUS_CONNECT_SUCCESS:")
            true
        } else {
            Log.d(TAG, "onServiceStatusConnectChanged:$statusCode")
            false
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