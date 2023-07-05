package com.example.pa_android

import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.Response

class ChatWebSocketListener : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        println("Connected to the server")
        // You can perform any initialization or setup here
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        println("Received message: $text")
        // Process the received message, e.g., display it in the UI
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        println("Closing connection: $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        println("Connection failure: ${t.message}")
    }
}
