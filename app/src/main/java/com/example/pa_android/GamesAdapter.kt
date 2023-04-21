package com.example.pa_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GamesAdapter(
    private val games: List<Game>,
    private val onClickListener: OnClickListener,
    private val type: String,
) : RecyclerView.Adapter<GameViewHolder>() {

    override fun getItemCount(): Int = games.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_game_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.updateView(games[position])

        if (type == "all_game")
            holder.itemView.findViewById<TextView>(R.id.ranking_by_game).visibility = View.GONE

        holder.itemView.findViewById<TextView>(R.id.item_click_button).setOnClickListener {
            onClickListener.onClick(games[position])
        }
    }

    class OnClickListener(val clickListener: (game: Game) -> Unit) {
        fun onClick(game: Game) = clickListener(game)
    }
}