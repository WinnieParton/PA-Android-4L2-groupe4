package com.example.pa_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class UsersAdapter(
    private val users: List<User>,
    private val onClickListener: OnClickListener,
    private val type: String
) : RecyclerView.Adapter<UserViewHolder>() {

    override fun getItemCount(): Int = users.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.updateView(users[position], type)

        if (type == "search") {
            holder.itemView.findViewById<CardView>(R.id.card_btn_no).visibility = View.GONE

            holder.itemView.findViewById<CardView>(R.id.card_btn).setOnClickListener {
                onClickListener.onClick(users[position], "PENDING")
            }
        }
        else {
            if(users[position].status == "ACCEPTED")
                holder.itemView.findViewById<RelativeLayout>(R.id.relative_user).setOnClickListener {
                    onClickListener.onClick(users[position], "")
                }
            if(users[position].status == "PENDING") {
                holder.itemView.findViewById<CardView>(R.id.card_btn).setOnClickListener {
                    onClickListener.onClick(users[position], "ACCEPTED")
                }

                holder.itemView.findViewById<CardView>(R.id.card_btn_no).setOnClickListener {
                    onClickListener.onClick(users[position], "REJECTED")
                }
            }
        }
    }

    class OnClickListener(val clickListener: (user: User, status: String) -> Unit) {
        fun onClick(user: User, status: String) = clickListener(user, status)
    }
}