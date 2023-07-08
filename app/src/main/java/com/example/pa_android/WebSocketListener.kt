package com.example.pa_android

import android.util.Log
import okhttp3.WebSocket
import okhttp3.Response
import okhttp3.WebSocketListener

open class WebSocketListener(
): WebSocketListener() {

    private val TAG = "Test"

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        //viewModel.setStatus(true)
       // webSocket.send("Android Device Connected")
        println( "onOpen: $response")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        //viewModel.addMessage(Pair(false, text))
        println( "onMessage: $text")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        println("onClosing: $code $reason")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        //viewModel.setStatus(false)
        println("onClosed: $code $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        println( "onFailure: ${t.message} $response")
        super.onFailure(webSocket, t, response)
    }
}