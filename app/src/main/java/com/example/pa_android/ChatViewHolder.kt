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
    private val textdate_send = v.findViewById<TextView>(R.id.dateTimeSend)
    private val text_receive = v.findViewById<TextView>(R.id.text_receive)
    private val textdate_receive = v.findViewById<TextView>(R.id.dateTimeReceive)

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    fun updateView(chat: Chat, type: String) {

        if(type == "listchat") {
            if (chat.name != null)
                title_game.makeBoldText(chat.name)
            name_editeurText.text = chat.message
        }else{
            if(chat.send){
                text_send.text = chat.message
                textdate_send.text = chat.currentDate
                text_receive.visibility = View.GONE
                textdate_receive.visibility = View.GONE
            }else{
                text_receive.text = chat.message
                textdate_receive.text = chat.currentDate
                text_send.visibility = View.GONE
                textdate_send.visibility = View.GONE
            }
        }
    }
}