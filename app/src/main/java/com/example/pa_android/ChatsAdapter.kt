package com.example.pa_android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ChatsAdapter(
    private val chats: List<Chat>,
    private val onClickListener: OnClickListener,
    private val type: String
) : RecyclerView.Adapter<ChatViewHolder>() {

    override fun getItemCount(): Int = chats.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.updateView(chats[position])

        if(type =="chat")
            holder.itemView.findViewById<CardView>(R.id.item_chat).setOnClickListener {
                onClickListener.onClick(chats[position])
            }
    }

    class OnClickListener(val clickListener: (chat: Chat) -> Unit) {
        fun onClick(chat: Chat) = clickListener(chat)
    }
}