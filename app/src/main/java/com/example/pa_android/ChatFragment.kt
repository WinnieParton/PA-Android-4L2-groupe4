package com.example.pa_android

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
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
import com.example.projetfinaljeu.ApiClient
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody

import okhttp3.WebSocketListener
import okio.ByteString
class ChatFragment : Fragment() {
    private lateinit var rv: RecyclerView
    private val chat: ChatFragmentArgs by navArgs()
    val chatDataList = mutableListOf<Chat>()
    private var countDownTimer: CountDownTimer? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.send_button).setOnClickListener {
            val message = view.findViewById<EditText>(R.id.message_text).text.toString()
            sendToPrivateChat(Chat(chat.chatInfo.senderUser, chat.chatInfo.receiverUser,
                message,chat.chatInfo.senderName, chat.chatInfo.receiverName, chat.chatInfo.receiverName,
                "UNREAD",getCurrentFormattedDate(),true ))
            view.findViewById<EditText>(R.id.message_text).text.clear()
        }
        countDownTimer = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Do nothing on each tick
            }

            override fun onFinish() {
                // Code to run after 1 second
                println("This will run after 1 second!")
                chatDataList.clear()
                GlobalScope.launch(Dispatchers.Default) {
                    try {
                        val response = ApiClient.listChatInConversation(chat.user.id, chat.chatInfo.receiverUser.toString())
                        for (game in response) {
                            val it = game.asJsonObject
                            chatDataList.add(
                                Chat(
                                    it.get("senderUser").asInt, it.get("receiverUser").asInt, it.get("message").asString,
                                    it.get("senderName").asString,it.get("receiverName").asString,it.get("name").asString,
                                    it.get("status").asString,it.get("currentDate").asString,it.get("send").asBoolean
                                )
                            )
                        }
                        withContext(Dispatchers.Main) {
                            getChat(view, chatDataList)
                            // Start the timer again for the next second
                            countDownTimer?.start()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            }
        }

        countDownTimer?.start()
    }
    private fun sendToPrivateChat(chatMessage:Chat) {
        GlobalScope.launch(Dispatchers.Default) {
            try {
                ApiClient.postSendMessage(chatMessage)
                withContext(Dispatchers.Main) { }
            }
            catch (e: Exception) {
            }
        }
    }
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
    private fun getChat(view: View, chatDataList: List<Chat>) {
        rv = view.findViewById(R.id.chat_view)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = ChatsAdapter(chatDataList, listener, "chat")
    }
    private val listener = ChatsAdapter.OnClickListener { chat ->
    }
    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
    }
}
