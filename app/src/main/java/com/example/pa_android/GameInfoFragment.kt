package com.example.pa_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class GameInfoFragment : Fragment() {
    // Make sure to use the FloatingActionButton for all the FABs
    private lateinit var mAddFab: FloatingActionButton
    private lateinit var mAddPersonFab: FloatingActionButton
    private lateinit var mHomeFab: FloatingActionButton
    private lateinit var homeActionText: TextView
    private lateinit var mAddAlarmFab: FloatingActionButton
    private lateinit var addAlarmActionText: TextView
    private lateinit var addLogoutActionText: TextView
    private lateinit var mAddLogoutFab: FloatingActionButton
    // These are taken to make visible and invisible along with FABs
    private lateinit var addPersonActionText: TextView

    // to check whether sub FAB buttons are visible or not.
    private var isAllFabsVisible: Boolean? = null

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private val game: GameInfoFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_info, container, false)
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)

        // Set up the view pager adapter
        val adapter = MyPagerAdapter(childFragmentManager)
        viewPager.adapter = adapter

        // Connect the tab layout to the view pager
        tabLayout.setupWithViewPager(viewPager)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.title_game).text = game.game.name
        if (game.game.header_image?.isNotEmpty() == true || game.game.header_image != null)
            Glide.with(view)
                .load(game.game.header_image)
                .into(view.findViewById(R.id.imageView))

        mAddFab = view.findViewById(R.id.add_fab)
        addLogoutActionText = view.findViewById(R.id.add_logout_action_text)
        mAddLogoutFab = view.findViewById(R.id.add_logout_fab)
        mAddLogoutFab.visibility = View.GONE
        addLogoutActionText.visibility = View.GONE
        // FAB button
        mAddPersonFab = view.findViewById(R.id.add_person_fab)
        addAlarmActionText = view.findViewById(R.id.add_alarm_action_text)
        mAddAlarmFab = view.findViewById(R.id.add_alarm_fab)
        addPersonActionText = view.findViewById(R.id.add_person_action_text)
        mHomeFab = view.findViewById(R.id.add_home_fab)
        homeActionText = view.findViewById(R.id.add_home_action_text)

        mAddPersonFab.visibility = View.GONE
        addPersonActionText.visibility = View.GONE
        mHomeFab.visibility = View.GONE
        homeActionText.visibility = View.GONE
        isAllFabsVisible = false
        mAddAlarmFab.visibility = View.GONE
        addAlarmActionText.visibility = View.GONE
        mAddFab.setOnClickListener(View.OnClickListener {
            (if (!isAllFabsVisible!!) {
                mAddPersonFab.show()
                addPersonActionText.visibility = View.VISIBLE
                mHomeFab.show()
                homeActionText.visibility = View.VISIBLE
                mAddAlarmFab.show()
                addAlarmActionText.visibility = View.VISIBLE
                addLogoutActionText.visibility = View.VISIBLE
                mAddLogoutFab.show()
                true
            } else {
                mAddPersonFab.hide()
                addPersonActionText.visibility = View.GONE
                mHomeFab.hide()
                homeActionText.visibility = View.GONE
                mAddAlarmFab.hide()
                addAlarmActionText.visibility = View.GONE
                addLogoutActionText.visibility = View.GONE
                mAddLogoutFab.hide()
                false
            }).also { isAllFabsVisible = it }
        })
        mAddPersonFab.setOnClickListener {
            findNavController().navigate(
                GameInfoFragmentDirections.actionGameInfoFragmentToResearchUserFragment(game.user)
            )

        }
        mAddAlarmFab.setOnClickListener {
            findNavController().navigate(
                GameInfoFragmentDirections.actionGameInfoFragmentToGameFragment(game.user)
            )
        }
        mHomeFab.setOnClickListener {
            findNavController().navigate(
                GameInfoFragmentDirections.actionGameInfoFragmentToHomeFragment(game.user)
            )
        }
        mAddLogoutFab.setOnClickListener {
            findNavController().navigate(
                GameInfoFragmentDirections.actionGameInfoFragmentToLoginFragment()
            )
        }
    }

    // Pager adapter for the view pager
    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // Return the appropriate fragment based on the tab position
            return when (position) {
                0 -> GameInfoDescriptionFragment(game.game.detailed_description!!)
                1 -> GameInfoClassementFragment()
                else -> throw IllegalArgumentException("Invalid tab position: $position")
            }
        }

        override fun getCount(): Int {
            // Return the total number of tabs
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            // Set the tab titles
            return when (position) {
                0 -> "DESCRIPTION"
                1 -> "CLASSEMENT"
                else -> throw IllegalArgumentException("Invalid tab position: $position")
            }
        }
    }
}