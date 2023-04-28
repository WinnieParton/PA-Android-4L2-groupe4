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
    private val card_btn_no = v.findViewById<CardView>(R.id.card_btn_no)


    @SuppressLint("SetTextI18n", "ResourceAsColor")
    fun updateView(user: User, type: String) {
        user_name.text = user.name
        user_email.text = user.email
        if(type != "search") {
            if (user.status == "PENDING") {
                val redColor = ContextCompat.getColor(itemView.context, R.color.bg_green)
                val colorStateList = ColorStateList.valueOf(redColor)
                card_btn.backgroundTintMode = PorterDuff.Mode.SRC_ATOP
                card_btn.backgroundTintList = colorStateList
                img_card_btn.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.task_alt
                    )
                );
                card_btn_no.visibility = View.VISIBLE
            } else {
                card_btn_no.visibility = View.GONE
                card_btn.visibility = View.GONE
            }
        }
    }
}