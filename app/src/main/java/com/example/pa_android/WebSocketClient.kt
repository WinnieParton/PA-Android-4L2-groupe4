package com.example.pa_android

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class WebSocketClient(private val listener: ChatWebSocketListener) {
    private val client: OkHttpClient = OkHttpClient()

    private var webSocket: WebSocket? = null

    fun connect(url: String) {
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, listener)
    }

    fun disconnect() {
        webSocket?.close(1000, null)
    }

    fun sendMessage(message: String) {
        webSocket?.send(message)
    }
}
