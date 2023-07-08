package com.example.pa_android

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import org.json.JSONObject
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.MediaType
import okhttp3.RequestBody

import okhttp3.WebSocketListener
import okio.ByteString
class ChatFragment : Fragment() {
    private lateinit var rv: RecyclerView
    private val chat: ChatFragmentArgs by navArgs()
    private var chats: List<Chat> = listOf( )
    val chatList = mutableListOf<Chat>()
    private lateinit var webSocketListener: WebSocketListener
    private val okHttpClient = OkHttpClient()
    private lateinit var viewModel: MainViewModel
    private lateinit var webSocketManager: WebSocketManager
    private lateinit var webSocket: WebSocket
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Créez une instance de OkHttpClient
        val client = OkHttpClient()

        // Créez une instance de la classe WebSocketListener pour gérer les événements WebSocket
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                Log.d("WebSocket", "Connexion WebSocket établie")

                // Envoyez un message à '/app/private/user' pour tester la méthode processGetPrivateMessage
                val chatMessage = JSONObject()
                chatMessage.put("senderUser", chat.chatInfo.senderUser)
                chatMessage.put("message", "")
                chatMessage.put("senderName", chat.chatInfo.senderName)
                chatMessage.put("receiverName", chat.chatInfo.receiverName)
                chatMessage.put("receiverUser", chat.chatInfo.receiverName)
                chatMessage.put("status", "UNREAD")
                chatMessage.put("currentDate", getCurrentLocalDate())
                chatMessage.put("send", true)
                val fullMessage = JSONObject().apply {
                    put("destination", "/chat/private")
                    put("message", chatMessage)
                }
                webSocket.send(fullMessage.toString())
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d("WebSocket", "Message reçu : $text")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                Log.d("WebSocket", "Message binaire reçu")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.d("WebSocket", "Fermeture de la connexion WebSocket")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
                Log.e("WebSocket", "Erreur WebSocket : ${t.message}")
                response?.let {
                    Log.e("WebSocket", "Erreur request : ${it.request.url}")
                    Log.e("WebSocket", "Erreur message : ${it.message}")
                }
                t.printStackTrace()
// Envoyez un message à '/app/private/user' pour tester la méthode processGetPrivateMessage
                val chatMessage = JSONObject()
                chatMessage.put("senderUser", chat.chatInfo.senderUser)
                chatMessage.put("message", "")
                chatMessage.put("senderName", chat.chatInfo.senderName)
                chatMessage.put("receiverName", chat.chatInfo.receiverName)
                chatMessage.put("receiverUser", chat.chatInfo.receiverName)
                chatMessage.put("status", "UNREAD")
                chatMessage.put("currentDate", getCurrentLocalDate())
                chatMessage.put("send", true)
                val fullMessage = JSONObject().apply {
                    put("destination", "/chat/private")
                    put("message", chatMessage)
                }
                webSocket.send(fullMessage.toString())
            }
        }

        // Créez une instance de la classe Request avec l'URL du serveur WebSocket
        val request = Request.Builder().url("ws://192.168.1.99:8080/ws").build()

        // Établissez la connexion WebSocket
        webSocket = client.newWebSocket(request, listener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getChat(view)
        chatList.addAll(chats)

        view.findViewById<Button>(R.id.send_button).setOnClickListener {
            val message = view.findViewById<EditText>(R.id.message_text).text.toString()
            val chatMessage = JSONObject()
            chatMessage.put("senderUser", chat.chatInfo.senderUser)
            chatMessage.put("message", message)
            chatMessage.put("senderName", chat.chatInfo.senderName)
            chatMessage.put("receiverName", chat.chatInfo.receiverName)
            chatMessage.put("receiverUser", chat.chatInfo.receiverName)
            chatMessage.put("status", "UNREAD")
            chatMessage.put("currentDate", getCurrentLocalDate())
            chatMessage.put("send", true)
            view.findViewById<EditText>(R.id.message_text).text.clear()
            chats = chatList
            getChat(view)
            // Send message through WebSocket
            sendToPrivateChat(chatMessage)
        }
    }
    private fun sendToPrivateChat(chatMessage:JSONObject) {
        //SocketHandler.getSocket().emit("private-chat-message", chatMessage)
        webSocketManager.sendMessage("/private-chat-message",chatMessage.toString())
    }
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
    private fun getChat(view: View) {
        rv = view.findViewById<RecyclerView>(R.id.chat_view)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = ChatsAdapter(chats, listener, "chat")

    }
    private val listener = ChatsAdapter.OnClickListener { chat ->
    }
    private fun createRequest(path: String): Request {
        val websocketURL = "ws://192.168.1.99:8080$path"
        return Request.Builder()
            .url(websocketURL)
            .build()
    }
     fun connectToSocket() {
         println("Connecting to socket...")
         val chatMessage = JSONObject()
         chatMessage.put("senderUser", chat.chatInfo.senderUser)
         chatMessage.put("message", "")
         chatMessage.put("senderName", chat.chatInfo.senderName)
         chatMessage.put("receiverName", chat.chatInfo.receiverName)
         chatMessage.put("receiverUser", chat.chatInfo.receiverName)
         chatMessage.put("status", "UNREAD")
         chatMessage.put("currentDate", getCurrentLocalDate())
         chatMessage.put("send", true)
         webSocketManager.connect(
             onConnected = {
                 // Connexion établie
                 /*webSocketManager.subscribe("/chat/private") { message ->
                     // Message reçu depuis "/chat/private"
                     println(" Message reçu depuis \"/chat/private\"")
                 }*/
             },
             onMessageReceived = { message ->
                 // Message reçu depuis "/ws"
                 println("  Message reçu depuis \"/ws\"")
                 println(message)

             },
             onError = { error ->
                 // Erreur de connexion
                 println("Erreur de connexion")

                 println(error)
// Connexion établie
                 /*webSocketManager.subscribe("/chat/private") { message ->
                     // Message reçu depuis "/chat/private"
                     println(" Message reçu depuis \"/chat/private\"")
                     println(message)
                 }*/
             }
         )
         webSocketManager.subscribe("/ws/private/user", chatMessage.toString()) { message ->
             // Traitement des messages du canal /chat/private
             println(" Message reçu depuis \"/chat/private\"")
             println(message)
         }
         //webSocketManager.sendMessage("/app/private/user", chatMessage.toString())

         // Envoyer des messages WebSocket
        // Utilisez webSocketSession.send(Frame.Text("message")) pour envoyer un message

        // Pour fermer la connexion WebSocket, utilisez webSocketSession.close()

        // Vous pouvez également ajouter des gestionnaires d'erreur et d'autres fonctionnalités WebSocket selon vos besoins

        println("Connected to socket...")
        // Connexion au socket
      /*  val options = IO.Options()
        options.transports = arrayOf<String>(NAME)
        options.extraHeaders = mapOf("Authorization" to listOf("Bearer ${getTokenFromLocalStorage()}"))

        try {
            socket = IO.socket("http://192.168.1.99:8080/ws", options)
            socket.connect()
            socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
            socket.on(Socket.EVENT_CONNECT, onConnect)
            socket.on(Socket.EVENT_DISCONNECT, onDisconnect)

        } catch (e: URISyntaxException) {
            println("Error connecting to socket: ${e.message}")
        }
*/
/*
        SocketHandler.getSocket().on("chat/private") { args ->
            println("args")
            println(args)
            println(args[0])
            println("args *******************")
            val data = args[0] as JSONObject
            if (args[0] != null) {
                val counter = args[0] as Int
                Log.i("I", counter.toString())

            }
        }*/
        println("Connecting to socket...")
    }
    /*private val onConnectError = Emitter.Listener { args ->
        val exception = args[0] as Exception
        println("Socket connection error: ${exception.message}")
        exception.printStackTrace()
       // socket.on(Socket.EVENT_CONNECT, onConnect)
    }
    // Define event listeners
    private val onConnect = Emitter.Listener {
        println("Socket connected")
        Log.d("ChatFragment", "Setting log level to Debug")
        Log.d("ChatFragment", "Other debug messages")
        SocketHandler.getSocket().on("chat/private", onNewMessage)
        SocketHandler.getSocket().on("user/${chat.user.name}/private", onNewMessage)
    }
    private val onDisconnect = Emitter.Listener {
        println("Socket disconnected")
    }
    private val onNewMessage = Emitter.Listener { args ->
        println(args)
        println("args")
        val data = args[0] as JSONObject
        val message = data.getString("message")

        // Traitement du nouveau message reçu
        // ...
    }
    */

    override fun onDestroyView() {
        super.onDestroyView()
        // Fermez la connexion WebSocket lorsque le fragment est détruit
        webSocket.close(1000, null)
    }
}
