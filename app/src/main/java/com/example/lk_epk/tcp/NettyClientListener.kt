package com.example.lk_epk.tcp

/**
 *
 * @FileName:
 *          com.safframework.netty4android.client.listener.NettyClientListener
 * @version: V1.0 <描述当前版本功能>
 */
interface NettyClientListener<T> {

    /**
     * 当接收到系统消息
     * @param msg 消息
     * @param index tcp 客户端的标识，因为一个应用程序可能有很多个长链接
     */
    fun onMessageResponseClient(msg: T, index: Int)

    /**
     * 当服务状态发生变化时触发
     * @param statusCode 状态变化
     * @param index tcp 客户端的标识，因为一个应用程序可能有很多个长链接
     */
    fun onClientStatusConnectChanged(statusCode: Int, index: Int)
}