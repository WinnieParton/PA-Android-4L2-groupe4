package com.example.pa_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FriendsFragment : Fragment() {
    private lateinit var rv: RecyclerView
    private var users: List<User> = listOf(
        User(
            "koumwinnie@gmail.com",
            "Winnie Parton",
            1,
            "winnie123456789",
            "https://icon2.cleanpng.com/20180319/vwq/kisspng-computer-icons-user-profile-avatar-profile-transparent-png-5ab03f3dba6729.3105587215214999657635.jpg",
            null
        ),
        User(
            "arthur@gmail.com",
            "Arthur",
            2,
            "winnie123456789",
            "https://w7.pngwing.com/pngs/81/570/png-transparent-profile-logo-computer-icons-user-user-blue-heroes-logo-thumbnail.png",
            "no"
        ),
        User(
            "monel@gmail.com",
            "Monel",
            3,
            "winnie123456789",
            "https://w7.pngwing.com/pngs/886/300/png-transparent-user-other-furniture-child-thumbnail.png",
            "ok"
        ),
        User(
            "winnie@gmail.com",
            "Winnie",
            4,
            "winnie123456789",
            "https://w7.pngwing.com/pngs/312/283/png-transparent-man-s-face-avatar-computer-icons-user-profile-business-user-avatar-blue-face-heroes-thumbnail.png",
            null
        ),
        User(
            "koum@gmail.com",
            "Koum",
            5,
            "winnie123456789",
            "https://w7.pngwing.com/pngs/886/300/png-transparent-user-other-furniture-child-thumbnail.png",
            "ok"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<FloatingActionButton>(R.id.add_fab).setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToResearchUserFragment()
            )
        }
        getUser(view)

    }

    private fun getUser(view: View) {
        rv = view.findViewById<RecyclerView>(R.id.list_user_recyclerview)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = UsersAdapter(users, listener,"list")
    }

    private val listener = UsersAdapter.OnClickListener { user ->
        // Add action to navigate
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToFragmentDetailUser(user)
        )

    }
}