package com.example.pa_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GameInfoClassementFragment : Fragment() {
    private var ranking: List<Ranking> = listOf(
        Ranking(
            1,
            User(
                "koum@gmail.com",
                "Winnie Ali",
                5,
                "winnie123456789",
                "https://w7.pngwing.com/pngs/886/300/png-transparent-user-other-furniture-child-thumbnail.png",
                "ok"
            ),
            700
        ),
        Ranking(
            2,
            User(
                "koum@gmail.com",
                "Mohamed Youss",
                5,
                "winnie123456789",
                "https://w7.pngwing.com/pngs/886/300/png-transparent-user-other-furniture-child-thumbnail.png",
                "ok"
            ),
            900
        ),
        Ranking(
            3,
            User(
                "koum@gmail.com",
                "Anatol Bagh",
                5,
                "winnie123456789",
                "https://w7.pngwing.com/pngs/886/300/png-transparent-user-other-furniture-child-thumbnail.png",
                "ok"
            ),
            750
        ),
        Ranking(
            4,
            User(
                "koum@gmail.com",
                "Carim Al",
                5,
                "winnie123456789",
                "https://w7.pngwing.com/pngs/886/300/png-transparent-user-other-furniture-child-thumbnail.png",
                "ok"
            ),
            7900
        ),
        Ranking(
            5,
            User(
                "koum@gmail.com",
                "Yami j",
                5,
                "winnie123456789",
                "https://w7.pngwing.com/pngs/886/300/png-transparent-user-other-furniture-child-thumbnail.png",
                "ok"
            ),
            746
        ),
        Ranking(
            6,
            User(
                "koum@gmail.com",
                "Balooi Jds",
                5,
                "winnie123456789",
                "https://w7.pngwing.com/pngs/886/300/png-transparent-user-other-furniture-child-thumbnail.png",
                "ok"
            ),
            900
        ),
        Ranking(
            7,
            User(
                "koum@gmail.com",
                "Rien du Tout",
                5,
                "winnie123456789",
                "https://w7.pngwing.com/pngs/886/300/png-transparent-user-other-furniture-child-thumbnail.png",
                "ok"
            ),
            850
        ),
    )
    private lateinit var rv: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_info_classement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRanking(view)
    }

    private fun getRanking(view: View) {
        val sortedRanking = ranking.sortedByDescending { it.score }
        rv = view.findViewById<RecyclerView>(R.id.list_classement_recyclerview)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = RankingsAdapter(sortedRanking, listener)

    }

    private val listener = RankingsAdapter.OnClickListener { ranking ->
        // Add action to navigate
        findNavController().navigate(
            GameInfoClassementFragmentDirections.actionGameInfoClassementFragmentToFragmentDetailUser(
                ranking.user!!
            )
        )
    }
}
