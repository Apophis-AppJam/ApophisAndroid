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
import com.example.apophis_android.data.entity.OurUserChat
import com.example.apophis_android.data.entity.UserInputChat
import com.example.apophis_android.ui.ChipFactory
import com.google.android.material.chip.ChipGroup
import java.lang.IllegalArgumentException

/**
 * Created By kimdahyee
 * on 01월 07일, 2020
 */
 
class UserChatAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val userChatList: MutableList<OurUserChat> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return when (userChatList[position].tag) {
            0 -> R.layout.item_chat_user
            1 -> R.layout.item_chip_choice
            2 -> R.layout.item_chat_short_answer
            else -> R.layout.item_chat_long_answer //3
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_chat_user -> {
                val view = layoutInflater.inflate(R.layout.item_chat_user, parent, false)
                UserViewHolder(view)
            }
            R.layout.item_chip_choice -> {
                val view = layoutInflater.inflate(R.layout.item_chip_choice, parent, false)
                ChoiceChatViewHolder(view, layoutInflater)
            }
            R.layout.item_chat_short_answer -> {
                val view = layoutInflater.inflate(R.layout.item_chat_short_answer, parent, false)
                UserInputViewHolder(view)
            }
            else ->
                throw IllegalArgumentException("ViewType [$viewType] is unexpected")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserViewHolder) {
            holder.bind(userChatList[position].content)
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }

        if (holder is ChoiceChatViewHolder) {
            holder.bind(userChatList[position].content)
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }

        if (holder is UserInputViewHolder) {
            holder.bind()
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val content = itemView.findViewById<TextView>(R.id.user_chat_content)
        fun bind(chatDataList: MutableList<String>) {
            chatDataList.forEach {
                content.text = it
            }
        }
    }

    inner class ChoiceChatViewHolder(itemView: View, inflater: LayoutInflater) : RecyclerView.ViewHolder(itemView) {
        private var chipGroup: ChipGroup = itemView.findViewById(R.id.chipgroup_choice)
        private val chipTextList: MutableList<String> = mutableListOf()
        private val inflater: LayoutInflater = inflater

        fun bind(chipItem: MutableList<String>) {
            chipGroup.removeAllViews()
            chipItem.forEach {
                val chip = ChipFactory.newInstance(inflater)
                chip.text = it
                chip.isClickable = true
                chipGroup.addView(chip)
                chipTextList.add(it)

                chip.setOnClickListener {
                    itemClickListener.onItemClick(chipTextList)
                }
            }
        }
    }

    inner class UserInputViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var input: TextView = itemView.findViewById(R.id.tv_user_input)
        private var input1: TextView = itemView.findViewById(R.id.et_user_input_1)
        private var input2: TextView = itemView.findViewById(R.id.et_user_input_2)
        private var input3: TextView = itemView.findViewById(R.id.et_user_input_3)
        private var btnComplete: TextView = itemView.findViewById(R.id.tv_user_input_complete)
        private val inputTextList: MutableList<String> = mutableListOf()

        fun bind() {
            btnComplete.setOnClickListener {
                removeChat()
                inputTextList.add(input.text.toString())
                inputTextList.add(input1.text.toString())
                inputTextList.add(input2.text.toString())
                inputTextList.add(input3.text.toString())

                for (i in inputTextList.indices) {
                    val chatRight = OurUserChat(mutableListOf(inputTextList[i]), 0)
                    addChat(chatRight)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return userChatList.size
    }

    fun addChat(ourUserChatItem: OurUserChat) {
        userChatList.add(ourUserChatItem)
        notifyItemInserted(userChatList.size)
    }

    fun removeChat() {
        userChatList.removeAt(userChatList.size-1)
        notifyItemRemoved(userChatList.size)
    }

    /* chip click listener */
    interface OnItemClickListener {
        fun onItemClick(dataList: MutableList<String>)
    }

    private lateinit var itemClickListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

}