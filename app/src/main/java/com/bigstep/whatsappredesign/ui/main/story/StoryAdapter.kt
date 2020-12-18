package com.bigstep.whatsappredesign.ui.main.story

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bigstep.whatsappredesign.R
import com.bigstep.whatsappredesign.data.model.story.StoryObject
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.chat_item.view.*
import kotlinx.android.synthetic.main.story_item.view.*

class StoryAdapter (private val context: Context,
                    private var storyList: ArrayList<StoryObject>
) : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>()  {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder =
        StoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.story_item, parent,
                false
            )
        )

    override fun getItemCount(): Int = storyList.size

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(storyList[position],position)
    }


    class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(storyObject: StoryObject, position: Int) {
            if (position == 0)
                itemView.separator.visibility = View.VISIBLE
            itemView.user_name.text = storyObject.title.capitalize()

            val storyView = itemView.story_image
            storyView.setStoryObject(storyObject)

//            Glide.with(itemView.story_image.context)
//                .load(storyObject.imageIcon)
//                .into(itemView.story_image)
        }

    }

    fun addData(list: List<StoryObject>) {
        storyList.addAll(list)
        notifyDataSetChanged()
    }

}