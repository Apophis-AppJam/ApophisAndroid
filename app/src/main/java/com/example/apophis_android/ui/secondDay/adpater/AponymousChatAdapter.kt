package com.example.apophis_android.ui.secondDay.adpater

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apophis_android.R
import com.example.apophis_android.data.entity.AponymousChat
import com.example.apophis_android.data.entity.ChatData

/**
 * Created By kimdahyee
 * on 01월 07일, 2020
 */

class AponymousChatAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var aponymousChatList: MutableList<ChatData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_chat_left, parent, false)
        return AnonymousViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AnonymousViewHolder) {
            holder.bind(aponymousChatList[position].content)
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }
    }

    inner class AnonymousViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val content = itemView.findViewById<TextView>(R.id.aponymous_chat_content)
        fun bind(chatDataList: MutableList<String>) {
            chatDataList.forEach {
                content.text = it
            }
        }
    }

    override fun getItemCount(): Int {
        return aponymousChatList.size
    }

    fun addChat(chatDataItem: ChatData) {
        aponymousChatList.add(chatDataItem)
        notifyItemInserted(aponymousChatList.size)
    }

}