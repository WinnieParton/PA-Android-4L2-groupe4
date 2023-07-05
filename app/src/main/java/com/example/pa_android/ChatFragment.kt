package com.example.pa_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.text.Typography.less


class ChatFragment : Fragment() {
    private lateinit var rv: RecyclerView
    private var chats: List<Chat> = listOf( )

    val chatList = mutableListOf<Chat>()
    val webSocketClient = WebSocketClient(ChatWebSocketListener())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Connect to the WebSocket server
        webSocketClient.connect("ws://localhost:8080/private-chat-message")
        getChat(view)
        chatList.addAll(chats)

        view.findViewById<Button>(R.id.send_button).setOnClickListener {
            val message = view.findViewById<EditText>(R.id.message_text).text.toString()
            webSocketClient.sendMessage(message)
            view.findViewById<EditText>(R.id.message_text).text.clear()
            /*chatList.add(
                Chat(
                    chats.size + 1,
                    "https://gamesgamescdn.com/system/static/thumbs/slider_image/70730/original_1499set000_easter-2023_462x250_en.jpg?1681111279",
                    "Winnie Parton",
                    view.findViewById<EditText>(R.id.message_text).text.toString(),
                    true)
            )*/
            chats = chatList
            getChat(view)
            view.findViewById<EditText>(R.id.message_text).text.clear()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        webSocketClient.disconnect()
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
}