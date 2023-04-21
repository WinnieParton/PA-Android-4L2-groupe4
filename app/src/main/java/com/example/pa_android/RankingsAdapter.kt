package com.example.pa_android

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

class RankingsAdapter(
    private val rankings: List<Ranking>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<RankingViewHolder>() {

    override fun getItemCount(): Int = rankings.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        return RankingViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_classement, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        holder.updateView(rankings[position], position)

        holder.itemView.findViewById<LinearLayout>(R.id.item_user_ranking).setOnClickListener {
            onClickListener.onClick(rankings[position])
        }
    }

    class OnClickListener(val clickListener: (ranking: Ranking) -> Unit) {
        fun onClick(ranking: Ranking) = clickListener(ranking)
    }
}