package com.example.pa_android

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GameViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val title_game = v.findViewById<TextView>(R.id.title_game)
    private val name_editeurText = v.findViewById<TextView>(R.id.name_editeur)
    private val id_image_jeu_itemImg = v.findViewById<ImageView>(R.id.my_image_view)

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    fun updateView(game: Game) {
        if (game.name != null)
            title_game.makeBoldText(game.name)

        name_editeurText.text = game.detailed_description

        if (game.header_image?.isNotEmpty() == true || game.header_image != null)
            Glide.with(itemView)
                .load(game.header_image)
                .into(id_image_jeu_itemImg)
    }
}