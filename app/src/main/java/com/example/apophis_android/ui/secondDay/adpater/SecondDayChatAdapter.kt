package com.example.apophis_android.ui.secondDay.adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apophis_android.R
import com.example.apophis_android.data.entity.ChatData
import java.lang.IllegalArgumentException

/**
 * Created By kimdahyee
 * on 01월 03일, 2020
 */
 
class SecondDayChatAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val chatDataList: MutableList<ChatData> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return when (chatDataList[position].tag) {
            0 -> R.layout.item_chat_left
            else -> R.layout.item_chat_right
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_chat_left -> {
                val view = layoutInflater.inflate(R.layout.item_chat_left, parent, false)
                ChatViewHolder(view)
            }
            R.layout.item_chat_right -> {
                val view = layoutInflater.inflate(R.layout.item_chat_right, parent, false)
                ChatViewHolder(view)
            }
            else ->
                throw IllegalArgumentException("ViewType [$viewType] is unexpected")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ChatViewHolder) {
            holder.bind(chatDataList[position].content)
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }
    }

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val content = itemView.findViewById<TextView>(R.id.chat_content)
        fun bind(chatDataList: MutableList<String>) {
            chatDataList.forEach {
                content.text = it
            }
        }
    }

    override fun getItemCount(): Int {
        return chatDataList.size
    }

    fun addChat(chatDataItem: ChatData) {
        chatDataList.add(chatDataItem)
        notifyItemInserted(chatDataList.size)
    }

    fun removeChat() {
        chatDataList.removeAt(chatDataList.size-1)
        notifyItemRemoved(chatDataList.size)
    }
}