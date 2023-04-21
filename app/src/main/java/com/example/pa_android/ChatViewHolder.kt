package com.example.pa_android

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class ChatViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val title_game = v.findViewById<TextView>(R.id.title_game)
    private val name_editeurText = v.findViewById<TextView>(R.id.name_editeur)

    private val text_send = v.findViewById<TextView>(R.id.text_send)
    private val text_receive = v.findViewById<TextView>(R.id.text_receive)

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    fun updateView(chat: Chat, type: String, chats: List<Chat>, position: Int) {

        if(type == "listchat") {
            if (chat.name != null)
                title_game.makeBoldText(chat.name)
            name_editeurText.text = chat.detailed_description
        }else{
            if(chat.send){
                text_send.text = chat.detailed_description
                text_receive.visibility = View.GONE
            }else{
                text_receive.text = chat.detailed_description
                text_send.visibility = View.GONE
            }
        }
    }
}