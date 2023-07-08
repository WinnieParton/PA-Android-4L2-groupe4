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
    private val ranking_by_game = v.findViewById<TextView>(R.id.ranking_by_game)


    @SuppressLint("SetTextI18n", "ResourceAsColor")
    fun updateView(game: Game, type:String) {
        if (game.name != null)
            title_game.makeBoldText(game.name)

        name_editeurText.text = game.detailed_description

        if (game.header_image?.isNotEmpty() == true || game.header_image != null)
            Glide.with(itemView)
                .load(game.header_image)
                .into(id_image_jeu_itemImg)
        if (type == "all_game")
            ranking_by_game.visibility = View.GONE
        else
            ranking_by_game.text = "Score: ${game.score}"
    }
}