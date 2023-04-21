package com.example.pa_android

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RankingViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val classement_id = v.findViewById<TextView>(R.id.classement_id)
    private val user_name = v.findViewById<TextView>(R.id.user_name)
    private val classement_value = v.findViewById<TextView>(R.id.classement_value)

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    fun updateView(ranking: Ranking, position: Int) {
        classement_id.text = (position + 1).toString()
        user_name.text = ranking.user?.username
        classement_value.text = ranking.score.toString()
    }
}