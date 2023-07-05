package com.example.pa_android

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetfinaljeu.ApiClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate


class MessageListFragment(user: User) : Fragment() {
    val userInfo = user
    private lateinit var rv: RecyclerView

    // Make sure to use the FloatingActionButton for all the FABs
    private lateinit var mAddFab: FloatingActionButton
    private lateinit var mAddAlarmFab: FloatingActionButton
    private lateinit var mAddPersonFab: FloatingActionButton
    private lateinit var addLogoutActionText: TextView
    private lateinit var mAddLogoutFab: FloatingActionButton
    // These are taken to make visible and invisible along with FABs
    private lateinit var addAlarmActionText: TextView
    private lateinit var addPersonActionText: TextView

    // to check whether sub FAB buttons are visible or not.
    private var isAllFabsVisible: Boolean? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAddFab = view.findViewById(R.id.add_fab)

        // FAB button
        mAddAlarmFab = view.findViewById(R.id.add_alarm_fab)
        mAddPersonFab = view.findViewById(R.id.add_person_fab)
        addLogoutActionText = view.findViewById(R.id.add_logout_action_text)
        mAddLogoutFab = view.findViewById(R.id.add_logout_fab)
        mAddLogoutFab.visibility = View.GONE
        addLogoutActionText.visibility = View.GONE
        addAlarmActionText = view.findViewById(R.id.add_alarm_action_text)
        addPersonActionText = view.findViewById(R.id.add_person_action_text)
        view.findViewById<FloatingActionButton?>(R.id.add_home_fab).visibility = View.GONE
        view.findViewById<TextView?>(R.id.add_home_action_text).visibility = View.GONE
        mAddAlarmFab.visibility = View.GONE
        mAddPersonFab.visibility = View.GONE
        addAlarmActionText.visibility = View.GONE
        addPersonActionText.visibility = View.GONE

        isAllFabsVisible = false

        mAddFab.setOnClickListener(View.OnClickListener {
            (if (!isAllFabsVisible!!) {
                mAddAlarmFab.show()
                mAddPersonFab.show()
                addAlarmActionText.visibility = View.VISIBLE
                addPersonActionText.visibility = View.VISIBLE
                addLogoutActionText.visibility = View.VISIBLE
                mAddLogoutFab.show()
                true
            } else {
                mAddAlarmFab.hide()
                mAddPersonFab.hide()
                addAlarmActionText.visibility = View.GONE
                addPersonActionText.visibility = View.GONE
                addLogoutActionText.visibility = View.GONE
                mAddLogoutFab.hide()

                false
            }).also { isAllFabsVisible = it }
        })
        mAddPersonFab.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToResearchUserFragment(userInfo)
            )

        }

        mAddAlarmFab.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToGameFragment(userInfo)
            )
        }
        mAddLogoutFab.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            )
        }
        val dataSearch = mutableListOf<Chat>()
        val dataSearchUser = mutableListOf<User>()
        val clickbutton = view.findViewById<ImageView>(R.id.iconsearchbutton)
        val searchEditText = view.findViewById<EditText>(R.id.text_research_input)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar_home)
        progressBar.visibility = View.GONE
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        clickbutton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            dataSearchUser.clear()
            GlobalScope.launch(Dispatchers.Default) {
                try {
                    val response = ApiClient.researchByName(userInfo.id, searchEditText.text.trim().toString())

                    for (usr in response) {
                        val it = usr.asJsonObject
                        val friendsArray = it.get("friends").asJsonArray
                        var containsId = false
                        for (friend in friendsArray) {
                            if (friend.isJsonObject) {
                                val friendObject = friend.asJsonObject
                                if (friendObject.get("id").asInt == 15) {
                                    containsId = true
                                    break
                                }
                            }
                        }
                        dataSearchUser.add(
                            User(
                                it.get("id").asString,
                                it.get("name").asString,
                                it.get("email").asString,
                                it.get("role").asString,
                                if(containsId) "ACCEPTED" else ""
                            )
                        )
                    }
                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.GONE
                        getUser(dataSearchUser, view)
                    }
                } catch (e: Exception) {
                    activity?.runOnUiThread {
                        progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "Not found "+searchEditText.text.toString()+". Please write all name",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    println("Error connecting to server: ${e.message}")
                }
            }

        }
        GlobalScope.launch(Dispatchers.Default) {
            try {
                progressBar.visibility = View.VISIBLE
                val response = ApiClient.listConversation(userInfo.id)
                for (game in response) {
                    val it = game.asJsonObject
                    dataSearch.add(
                        Chat(
                            it.get("senderUser").asInt, it.get("receiverUser").asInt, it.get("message").asString,
                            it.get("senderName").asString,it.get("receiverName").asString,it.get("name").asString,
                            it.get("status").asString,it.get("currentDate").asString,true
                        )
                    )
                }
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    getChat(view, dataSearch)
                }
            } catch (e: Exception) {
            }
        }
    }
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }
    private fun getChat(view: View, chats: List<Chat>) {
        rv = view.findViewById(R.id.list_chat_recyclerview)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = ChatsAdapter(chats, listener, "listchat")
    }
    private fun getUser(users: List<User>,view: View) {
        rv = view.findViewById(R.id.list_chat_recyclerview)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = UsersAdapter(users, listenerUser, "message")
    }
    private val listener = ChatsAdapter.OnClickListener { chat ->

        // Add action to navigate
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToChatFragment(chat)
        )
    }

    private val listenerUser = UsersAdapter.OnClickListener { us, status ->
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToChatFragment(Chat(userInfo.id.toInt(), us.id.toInt(),
                "", userInfo.name, us.name, us.name, "UNREAD", "", true))
        )
    }
}