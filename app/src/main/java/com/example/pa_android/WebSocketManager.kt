package com.example.pa_android
import okhttp3.*
import org.json.JSONObject

class WebSocketManager(private val baseUrl: String) {
    private var webSocket: WebSocket? = null
    private val webSocketListeners = hashMapOf<String, WebSocketListener>()
    private val webSocketClients = hashMapOf<String, WebSocket>()
    fun connect(
        onConnected: () -> Unit,
        onMessageReceived: (String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val request = Request.Builder()
            .url(baseUrl + "/ws")
            .build()

        val okHttpClient = OkHttpClient()
        val webSocketClient = okHttpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                onConnected()
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                onMessageReceived(text)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                println("response")
                println(response)
                t.printStackTrace()
                println(onError)

                onError(t)
            }
        })

        webSocketClients["/ws"] = webSocketClient
    }

    fun subscribe(channel: String,chatMessage:String, onMessageReceived: (String) -> Unit) {
        val request = Request.Builder()
            .url(baseUrl + channel)
            .build()

        val okHttpClient = OkHttpClient()
        val webSocketClient = okHttpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                onMessageReceived(text)
            }
        })

        webSocketClients[channel] = webSocketClient

        // Envoyer la demande d'abonnement au canal
        /*val subscribeMessage = JSONObject().apply {
            put("type", "subscribe")
            put("channel", channel)
        }.toString()
        webSocketClient.send(subscribeMessage)*/
        sendMessage(channel,chatMessage)
    }

    fun unsubscribe(channel: String) {
        webSocketClients[channel]?.let { webSocket ->
            webSocketClients.remove(channel)

            // Envoyer la demande de désabonnement du canal
            val unsubscribeMessage = JSONObject().apply {
                put("type", "unsubscribe")
                put("channel", channel)
            }.toString()
            webSocket.send(unsubscribeMessage)

            webSocket.cancel()
        }
    }

    fun disconnect() {
        webSocketClients.values.forEach { webSocket ->
            webSocket.cancel()
        }
        webSocketClients.clear()
    }

    fun sendMessage(channel: String, message: String) {
        webSocketClients[channel]?.send(message)
        println("rrrrrrrrrrrrrrrrrr")

    }

    fun setListener(channel: String, webSocketListener: WebSocketListener) {
        webSocketListeners[channel] = webSocketListener
    }
}
