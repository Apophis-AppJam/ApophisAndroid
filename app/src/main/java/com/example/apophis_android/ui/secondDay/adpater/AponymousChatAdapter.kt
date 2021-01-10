package com.example.apophis_android.ui.secondDay.adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apophis_android.R
import com.example.apophis_android.data.entity.OurAponymousChat
import java.lang.IllegalArgumentException

/**
 * Created By kimdahyee
 * on 01월 07일, 2020
 */

class AponymousChatAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //private var aponymousChatList: MutableList<ChatData> = mutableListOf()
    private var aponymousChatList: MutableList<OurAponymousChat> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return when (aponymousChatList[position].tag) {
            0 -> R.layout.item_chat_aponymous
            else -> R.layout.item_chat_aponymous_image
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_chat_aponymous -> {
                val view = layoutInflater.inflate(R.layout.item_chat_aponymous, parent, false)
                AnonymousViewHolder(view)
            }
            R.layout.item_chat_aponymous_image -> {
                val view = layoutInflater.inflate(R.layout.item_chat_aponymous_image, parent, false)
                AponymousImageViewHolder(view)
            }
            else ->
                throw IllegalArgumentException("ViewType [$viewType] is unexpected")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AnonymousViewHolder) {
            holder.bind(aponymousChatList[position].content)
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }

        if (holder is AponymousImageViewHolder) {
            holder.bind(aponymousChatList[position].content)
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }
    }

    inner class AnonymousViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val content = itemView.findViewById<TextView>(R.id.tv_aponymous_chat)
        fun bind(text: String) {
            content.text = text
        }
    }

    inner class AponymousImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.img_chat_aponymous)
        fun bind(imageUrl: String) {
            Glide.with(itemView).load(imageUrl).into(image)
        }
    }

    override fun getItemCount(): Int {
        return aponymousChatList.size
    }

    fun addChat(chatDataItem: OurAponymousChat) {
        aponymousChatList.add(chatDataItem)
        notifyItemInserted(aponymousChatList.size)
    }

}