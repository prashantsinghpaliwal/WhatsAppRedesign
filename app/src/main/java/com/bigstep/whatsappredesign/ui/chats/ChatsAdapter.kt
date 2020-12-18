package com.bigstep.whatsappredesign.ui.chats

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bigstep.whatsappredesign.R
import com.bigstep.whatsappredesign.data.model.call.CallObject
import com.bigstep.whatsappredesign.data.model.message.TotalMessage
import com.bigstep.whatsappredesign.utils.Constants
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.call_item.view.*
import kotlinx.android.synthetic.main.chat_item.view.*

class ChatsAdapter(
    private val context: Context,
    private val objectList: ArrayList<Any>,
    private val listType: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int = listType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (listType == Constants.TYPE_MESSAGES)
            return ChatsViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.chat_item, parent,
                    false
                )
            )
        else return CallsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.call_item, parent,
                false
            )
        )
    }


    override fun getItemCount(): Int = objectList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (listType == Constants.TYPE_MESSAGES)
            (holder as ChatsViewHolder).bind(objectList[position] as TotalMessage, context)
        else (holder as CallsViewHolder).bind(objectList[position] as CallObject, context)
    }


    class ChatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(totalMessage: TotalMessage, context: Context) {
            itemView.contact_name.text = totalMessage.senderObject.displayName.capitalize()
            itemView.message.text = totalMessage.message.body
            Glide.with(itemView.contact_image.context)
                .load(totalMessage.senderObject.imageIcon)
                .into(itemView.contact_image)

            if (totalMessage.message.unreadCount == 0) {

                if (totalMessage.message.messageStatus.equals("Received", true))
                    itemView.message_status_img.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_message_received
                        )
                    )
                else itemView.message_status_img.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_double_check
                    )
                )

            } else {
                itemView.unread_count.visibility = View.VISIBLE
                itemView.message_status_img.visibility = View.INVISIBLE
                itemView.unread_count.text = totalMessage.message.unreadCount.toString()
            }
        }

    }

    class CallsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(callObject: CallObject, context: Context) {
            itemView.owner_name.text = callObject.name.capitalize()
            itemView.date.text = callObject.date
            Glide.with(itemView.owner_image.context)
                .load(callObject.imageIcon)
                .into(itemView.owner_image)

            if (callObject.callType.equals("Video", true)) {

                itemView.call_type.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_baseline_call_24
                    )
                )

            } else itemView.call_type.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_baseline_videocam_24
                )
            )

            when (callObject.callStatus) {

                Constants.CALL_MADE_STATUS -> itemView.date.setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(context, R.drawable.ic_baseline_call_made_24)
                    , null, null, null
                )

                Constants.CALL_MISSED_STATUS -> itemView.date.setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(context, R.drawable.ic_baseline_call_missed_24)
                    , null, null, null
                )

                else -> itemView.date.setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(context, R.drawable.ic_baseline_call_received_24)
                    , null, null, null
                )
            }

            val drawablePadding = context.getResources().getDimensionPixelSize(R.dimen.dimen_6dp)
            itemView.date.setCompoundDrawablePadding(drawablePadding)
        }

    }

    fun addData(list: List<Any>) {
        objectList.addAll(list)
        notifyDataSetChanged()
    }

}