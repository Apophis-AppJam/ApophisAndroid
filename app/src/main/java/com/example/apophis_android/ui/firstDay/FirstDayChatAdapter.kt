package com.example.apophis_android.ui.firstDay

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apophis_android.R
import com.example.apophis_android.data.entity.OurUserChat
import com.example.apophis_android.ui.ChipFactory
import com.example.apophis_android.ui.secondDay.SecondDayTimepickerActivity
import com.example.apophis_android.ui.secondDay.adpater.ChatAdapter
import com.google.android.material.chip.ChipGroup
import java.lang.IllegalArgumentException

/**
 * Created By hanjaehyeon
 */

class FirstDayChatAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val userChatList: MutableList<OurUserChat> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return when (userChatList[position].tag) {
            0 -> R.layout.item_chat_aponymous
            1 -> R.layout.item_chat_aponymous_image
            2 -> R.layout.item_chat_user
            3 -> R.layout.item_chat_user_image_portrait
            4 -> R.layout.item_chat_user_image_landscape
            5 -> R.layout.item_chip_choice
            6 -> R.layout.item_chat_compass
            7 -> R.layout.item_chat_short_answer
            else -> R.layout.item_chat_camera
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_chat_aponymous -> {
                val view = layoutInflater.inflate(R.layout.item_chat_aponymous, parent, false)
                AponymousViewHolder(view)
            }
            R.layout.item_chat_aponymous_image -> {
                val view = layoutInflater.inflate(R.layout.item_chat_aponymous_image, parent, false)
                AponymousImageViewHolder(view)
            }
            R.layout.item_chat_user -> {
                val view = layoutInflater.inflate(R.layout.item_chat_user, parent, false)
                UserViewHolder(view)
            }
            R.layout.item_chat_user_image_portrait -> {
                val view = layoutInflater.inflate(R.layout.item_chat_user_image_portrait, parent, false)
                UserImagePortraitViewHolder(view)
            }
            R.layout.item_chat_user_image_landscape -> {
                val view = layoutInflater.inflate(R.layout.item_chat_user_image_landscape, parent, false)
                UserImageLandscapeViewHolder(view)
            }
            R.layout.item_chip_choice -> {
                val view = layoutInflater.inflate(R.layout.item_chip_choice, parent, false)
                ChoiceChatViewHolder(view, layoutInflater)
            }
            R.layout.item_chat_compass -> {
                val view = layoutInflater.inflate(R.layout.item_chat_compass, parent, false)
                CompassViewHolder(view)
            }
            R.layout.item_chat_camera -> {
                val view = layoutInflater.inflate(R.layout.item_chat_camera, parent, false)
                CameraViewHolder(view)
            }
            else ->
                throw IllegalArgumentException("ViewType [$viewType] is unexpected")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AponymousViewHolder) {
            holder.bind(userChatList[position].content)
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }

        if (holder is AponymousImageViewHolder) {
            holder.bind(userChatList[position].content)
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }

        if (holder is UserViewHolder) {
            holder.bind(userChatList[position].content)
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }

        if (holder is UserImagePortraitViewHolder) {
            holder.bind(userChatList[position].content)
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }

        if (holder is UserImageLandscapeViewHolder) {
            holder.bind(userChatList[position].content)
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }

        if (holder is ChoiceChatViewHolder) {
            holder.bind(userChatList[position].content)
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }

        if (holder is CompassViewHolder) {
            holder.bind()
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }

        if (holder is CameraViewHolder) {
            holder.bind()
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }
    }

    inner class AponymousViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val content = itemView.findViewById<TextView>(R.id.tv_aponymous_chat)
        fun bind(text: MutableList<String>) {
            text.forEach {
                content.text = it
            }
        }
    }

    inner class AponymousImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.img_chat_aponymous)
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(imageUrl: MutableList<String>) {
            imageUrl.forEach {
                Glide.with(itemView).load(it).into(image)
            }
            image.background = context.getDrawable(R.drawable.round_rectangle_black_23dp)
            image.clipToOutline = true
        }
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val content = itemView.findViewById<TextView>(R.id.tv_user_chat)
        fun bind(chatDataList: MutableList<String>) {
            chatDataList.forEach {
                content.text = it
            }
        }
    }

    inner class UserImagePortraitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.img_chat_user_portrait)
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(imageUrl: MutableList<String>) {
            imageUrl.forEach {
                Glide.with(itemView).load(it).into(image)
            }
            image.background = context.getDrawable(R.drawable.round_rectangle_black_23dp)
            image.clipToOutline = true
        }
    }

    inner class UserImageLandscapeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.img_chat_user_landscape)
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(imageUrl: MutableList<String>) {
            imageUrl.forEach {
                Glide.with(itemView).load(it).into(image)
            }
            image.background = context.getDrawable(R.drawable.round_rectangle_black_23dp)
            image.clipToOutline = true
        }
    }

    inner class ChoiceChatViewHolder(itemView: View, inflater: LayoutInflater) : RecyclerView.ViewHolder(itemView) {
        private var chipGroup: ChipGroup = itemView.findViewById(R.id.chipgroup_choice)
        private val chipTextList: MutableList<String> = mutableListOf()
        private val inflater: LayoutInflater = inflater

        fun bind(chipItem: MutableList<String>) {
            chipGroup.removeAllViews()
            chipTextList.clear()
            chipItem.forEach {
                val chip = ChipFactory.newInstance(inflater)
                chip.text = it
                chip.isClickable = true
                chipGroup.addView(chip)
                chipTextList.add(it)

                chip.setOnClickListener {
                    itemClickListener.onItemClick(chip.text.toString())
                    // itemClickListener.onItemClick(chipTextList)
                }
            }
        }
    }

    inner class CompassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val btnCompass = itemView.findViewById<ImageView>(R.id.iv_chat_compass)

        fun bind() {
            btnCompass.setOnClickListener {
                val intent = Intent(context, CompassActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    inner class CameraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val btnCamera = itemView.findViewById<ImageView>(R.id.iv_chat_camera)

        fun bind() {
            btnCamera.setOnClickListener {
                val intent = Intent(context, CameraActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return userChatList.size
    }

    fun addChat(userChatItem: OurUserChat) {
        userChatList.add(userChatItem)
        notifyItemInserted(userChatList.size)
    }

    fun removeChat() {
        userChatList.removeAt(userChatList.size-1)
        notifyItemRemoved(userChatList.size)
    }

    /* chip click listener */
    interface OnItemClickListener {
        fun onItemClick(data: String)
        // fun onItemClick(dataList: MutableList<String>)
    }

    private lateinit var itemClickListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

}