package com.bigstep.whatsappredesign.ui.chats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigstep.hiltmindorks.utils.Status
import com.bigstep.whatsappredesign.R
import com.bigstep.whatsappredesign.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_chats.*

@AndroidEntryPoint
class ChatsFragment : Fragment() {

    private val chatsViewModel: ChatsViewModel by viewModels()
    private lateinit var adapter: ChatsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chats, container, false)!!
        return view
    }

    private fun setUpObserver() {
        chatsViewModel.messages.observe(viewLifecycleOwner, Observer {

            when (it.status) {

                Status.SUCCESS -> {
                    Log.v("ChatsFragmentLogs", it.data.toString())
                    recyclerView.visibility = View.VISIBLE
                    shimmer_layout.visibility = View.GONE
                    shimmer_layout.stopShimmer()
                    it.data?.let {
                        adapter.addData(it)
                    }

                }

                Status.LOADING -> {
                    recyclerView.visibility = View.GONE
                    shimmer_layout.visibility = View.VISIBLE
                    shimmer_layout.startShimmer()
                }

                Status.ERROR -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    shimmer_layout.visibility = View.GONE
                    shimmer_layout.stopShimmer()
                }

            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpObserver()
        setUpUi()
    }

    private fun setUpUi() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ChatsAdapter(requireContext(), arrayListOf(), Constants.TYPE_MESSAGES)
        recyclerView.adapter = adapter
    }
}