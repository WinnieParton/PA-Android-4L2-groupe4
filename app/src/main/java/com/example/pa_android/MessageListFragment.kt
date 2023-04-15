package com.example.pa_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MessageListFragment : Fragment() {
    private var chats: List<Chat> = listOf(
        Chat(
            1,
            "https://gamesgamescdn.com/system/static/thumbs/slider_image/70730/original_1499set000_easter-2023_462x250_en.jpg?1681111279",
            "Winnie Parton",
            "how call me nar"
        ),
        Chat(
            2,
            "https://images.pexels.com/photos/6689072/pexels-photo-6689072.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            "Ali Arthur",
            "See you later"
        ),
        Chat(
            3,
            "https://www.gamereactor.fr/media/90/_3229073.jpg",
            "Mohamed Ali",
            "Noo man what's up"
        ),
        Chat(
            4,
            "https://gamesgamescdn.com/system/static/thumbs/slider_image/73499/original_EP-uphill-rush-12-462x250.jpg?1676468575",
            "Paul Dupond",
            "Yes, how bro"
        )
    )
    private lateinit var rv: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getChat(view)
    }

    private fun getChat(view: View) {
        rv = view.findViewById<RecyclerView>(R.id.list_chat_recyclerview)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = ChatsAdapter(chats, listener, "chat")

    }

    private val listener = ChatsAdapter.OnClickListener { chat ->
        // Add action to navigate
        //findNavController().navigate(
        //GameHomeFragmentDirections.actionGameHomeFragmentToGameDetailFragment(game, userArgs.userArgs)
        //)

    }
}