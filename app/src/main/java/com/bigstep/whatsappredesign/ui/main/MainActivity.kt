package com.bigstep.whatsappredesign.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigstep.hiltmindorks.utils.Status
import com.bigstep.whatsappredesign.R
import com.bigstep.whatsappredesign.ui.calls.CallsFragment
import com.bigstep.whatsappredesign.ui.chats.ChatsFragment
import com.bigstep.whatsappredesign.ui.common.ViewPagerAdapter
import com.bigstep.whatsappredesign.ui.main.story.StoryAdapter
import com.bigstep.whatsappredesign.ui.main.story.StoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_activity.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val storiesViewModel: StoryViewModel by viewModels()
    private lateinit var storyAdapter: StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        initUi()
        setupObserver()
    }

    private fun initUi() {
        setSupportActionBar(toolbar)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ChatsFragment(), "Chats \u25CF")
        adapter.addFragment(CallsFragment(), "Calls \u25CF")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        story_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        storyAdapter = StoryAdapter(this, arrayListOf())
//        recyclerView.addItemDecoration(
//            DividerItemDecoration(
//                recyclerView.context,
//                (recyclerView.layoutManager as LinearLayoutManager).orientation
//            )
//        )
        story_list.adapter = storyAdapter
    }

    private fun setupObserver() {
        storiesViewModel.stories.observe(this, Observer {
            when (it.status) {

                Status.SUCCESS -> {
                    shimmer_story.visibility = View.GONE
                    shimmer_story.stopShimmer()
                    story_list.visibility = View.VISIBLE
                    it.data?.let {
                        storyAdapter.addData(it) }
                }

                Status.LOADING -> {
                    shimmer_story.visibility = View.VISIBLE
                    shimmer_story.startShimmer()
                }

                Status.ERROR -> {
                    status_label.visibility = View.GONE
                    story_list.visibility = View.GONE
                    shimmer_story.visibility = View.GONE
                    shimmer_story.stopShimmer()
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

            }
        })
    }
}