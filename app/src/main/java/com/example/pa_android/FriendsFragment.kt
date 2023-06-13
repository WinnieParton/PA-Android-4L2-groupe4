package com.example.pa_android

import android.os.Bundle
import android.telecom.DisconnectCause.REJECTED
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.projetfinaljeu.ApiClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FriendsFragment(user: User) : Fragment() {
    val userInfo = user
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
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private var myfriends = mutableListOf<User>()
    private var friendssend = mutableListOf<User>()
    private var friendsreceive = mutableListOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_friends, container, false)

        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)
        // Set up the view pager adapter
        GlobalScope.launch(Dispatchers.Default) {
            try {
                val response = ApiClient.listMyFriend(userInfo.id)
                val response1 = ApiClient.listFriendSend(userInfo.id)
                val response2 = ApiClient.listFriendReceived(userInfo.id)

                for (jsonElement in response.get("requests").asJsonArray) {
                    val it = jsonElement.asJsonObject
                    myfriends.add(
                        User(
                            it.get("user").asJsonObject.get("id").asString,
                            it.get("user").asJsonObject.get("name").asString,
                            it.get("user").asJsonObject.get("email").asString,
                            it.get("user").asJsonObject.get("role").asString,
                            it.get("status").asString
                        )
                    )
                }
                for (jsonElement in response1.get("requests").asJsonArray) {
                    val it = jsonElement.asJsonObject
                    friendssend.add(
                        User(
                            it.get("user").asJsonObject.get("id").asString,
                            it.get("user").asJsonObject.get("name").asString,
                            it.get("user").asJsonObject.get("email").asString,
                            it.get("user").asJsonObject.get("role").asString,
                            it.get("status").asString
                        )
                    )
                }
                for (jsonElement in response2.get("requests").asJsonArray) {
                    val it = jsonElement.asJsonObject
                    friendsreceive.add(
                        User(
                            it.get("user").asJsonObject.get("id").asString,
                            it.get("user").asJsonObject.get("name").asString,
                            it.get("user").asJsonObject.get("email").asString,
                            it.get("user").asJsonObject.get("role").asString,
                            it.get("status").asString
                        )
                    )
                }
                withContext(Dispatchers.Main) {
                    val adapter = MyPagerAdapter(childFragmentManager)
                    viewPager.adapter = adapter
                    // Connect the tab layout to the view pager
                    tabLayout.setupWithViewPager(viewPager)
                }
            } catch (e: Exception) {
                println("${e.message}")
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
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

    }
    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // Return the appropriate fragment based on the tab position
            return when (position) {
                0 -> FriendInfoFragment(myfriends, userInfo)
                1 -> FriendInfoFragment(friendsreceive, userInfo)
                2 -> FriendInfoFragment(friendssend, userInfo)
                else -> throw IllegalArgumentException("Invalid tab position: $position")
            }
        }
        override fun getCount(): Int {
            // Return the total number of tabs
            return 3
        }
        override fun getPageTitle(position: Int): CharSequence? {
            // Set the tab titles
            return when (position) {
                0 -> "My Friends"
                1 -> "Invitation Pending"
                2 -> "Send Invitation"
                else -> throw IllegalArgumentException("Invalid tab position: $position")
            }
        }
    }
}