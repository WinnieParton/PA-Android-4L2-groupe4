package com.example.pa_android

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class UserViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val user_name = v.findViewById<TextView>(R.id.user_name)
    private val user_email = v.findViewById<TextView>(R.id.user_email)
    private val img_card_btn = v.findViewById<ImageView>(R.id.img_card_btn)
    private val card_btn = v.findViewById<CardView>(R.id.card_btn)


    @SuppressLint("SetTextI18n", "ResourceAsColor")
    fun updateView(user: User) {
        user_name.text = user.username
        user_email.text = user.email

        if (user.status=="ok") {
            val redColor = ContextCompat.getColor(itemView.context, R.color.bg_green)
            val colorStateList = ColorStateList.valueOf(redColor)
            card_btn.backgroundTintMode = PorterDuff.Mode.SRC_ATOP
            card_btn.backgroundTintList = colorStateList
            img_card_btn.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.task_alt));
        }
        else if (user.status=="no") {
            val redColor = ContextCompat.getColor(itemView.context, R.color.bg_red)
            val colorStateList = ColorStateList.valueOf(redColor)
            card_btn.backgroundTintMode = PorterDuff.Mode.SRC_ATOP
            card_btn.backgroundTintList = colorStateList
            img_card_btn.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.cancel));
        }
    }
}