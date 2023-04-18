package com.example.pa_android

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val title_game = v.findViewById<TextView>(R.id.title_game)
    private val name_editeurText = v.findViewById<TextView>(R.id.name_editeur)
    private val id_image_jeu_itemImg = v.findViewById<ImageView>(R.id.my_image_view)

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    fun updateView(chat: Chat) {
        if (chat.name != null)
            title_game.makeBoldText(chat.name)

        name_editeurText.text = chat.detailed_description

        /* if (chat.header_image?.isNotEmpty() == true || chat.header_image != null)
             Glide.with(itemView)
                 .load(chat.header_image)
                 .into(id_image_jeu_itemImg)*/
    }
}