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
import com.example.apophis_android.data.entity.UserInputChat
import com.example.apophis_android.ui.ChipFactory
import com.google.android.material.chip.ChipGroup
import java.lang.IllegalArgumentException

/**
 * Created By kimdahyee
 * on 01월 07일, 2020
 */
 
class UserChatAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val choiceChatList: MutableList<ChatData> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return when (choiceChatList[position].tag) {
            0 -> R.layout.item_chip_choice
            else -> R.layout.item_second_day_user_input
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_chip_choice -> {
                val view = layoutInflater.inflate(R.layout.item_chip_choice, parent, false)
                ChoiceChatViewHolder(view, layoutInflater)
            }
            R.layout.item_second_day_user_input -> {
                val view = layoutInflater.inflate(R.layout.item_second_day_user_input, parent, false)
                UserInputViewHolder(view, layoutInflater)
            }
            else ->
                throw IllegalArgumentException("ViewType [$viewType] is unexpected")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ChoiceChatViewHolder) {
            holder.bind(choiceChatList[position].content)
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }

        if (holder is UserInputViewHolder) {

        }
    }

    inner class ChoiceChatViewHolder(itemView: View, inflater: LayoutInflater) : RecyclerView.ViewHolder(itemView) {
        private var chipGroup: ChipGroup = itemView.findViewById(R.id.chipgroup_choice)
        private val inflater: LayoutInflater = inflater

        fun bind(chipItem: MutableList<String>) {
            chipGroup.removeAllViews()
            chipItem.forEach {
                val chip = ChipFactory.newInstance(inflater)
                chip.text = it
                chip.isClickable = true
                chipGroup.addView(chip)

                chip.setOnClickListener {
                    chipClickListener.onChipClick(chip.text.toString())
                }
            }
        }
    }

    inner class UserInputViewHolder(itemView: View, inflater: LayoutInflater) : RecyclerView.ViewHolder(itemView) {
        private var userInput1: TextView = itemView.findViewById(R.id.et_user_input_1)
        private var userInput2: TextView = itemView.findViewById(R.id.et_user_input_2)
        private var userInput3: TextView = itemView.findViewById(R.id.et_user_input_3)

        fun bind(userInput: UserInputChat) {
            userInput1.text = userInput.input1
            userInput2.text = userInput.input2
            userInput3.text = userInput.input3
        }
    }

    override fun getItemCount(): Int {
        return choiceChatList.size
    }

    fun addChat(chatDataItem: ChatData) {
        choiceChatList.add(chatDataItem)
        notifyItemInserted(choiceChatList.size)
    }

    fun removeChat() {
        choiceChatList.removeAt(choiceChatList.size-1)
        notifyItemRemoved(choiceChatList.size)
    }

    /* chip click listener */
    interface OnChipClickListener {
        fun onChipClick(data: String)
    }

    private lateinit var chipClickListener: OnChipClickListener

    fun setOnChipItemClickListener(listener: OnChipClickListener) {
        this.chipClickListener = listener
    }

}