package com.example.lk_epk.util

import com.example.lk_epk.util.Constant.TAG_TEN
import com.xuhao.didi.core.iocore.interfaces.ISendable
import com.xuhao.didi.socket.client.impl.client.PulseManager
import com.xuhao.didi.socket.client.sdk.OkSocket
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo
import com.xuhao.didi.socket.client.sdk.client.OkSocketOptions
import com.xuhao.didi.socket.client.sdk.client.action.ISocketActionListener
import com.xuhao.didi.socket.client.sdk.client.connection.AbsReconnectionManager
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager
import java.lang.Exception

object OkSocketManager {
    val manager: IConnectionManager
        get() {
            var connectionInfo = ConnectionInfo("172.16.20.5", 5005)
            val builder = OkSocketOptions.Builder()
            //设置重连
            builder.setReconnectionManager(OkSocketOptions.getDefault().reconnectionManager)
            builder.setPulseFeedLoseTimes(2)
            builder.setConnectTimeoutSecond(TAG_TEN)
            //调用OkSocket,开启这次连接的通道,拿到通道Manager
            val manager = OkSocket.open(connectionInfo)
            manager.option(builder.build());
            return manager
        }
}