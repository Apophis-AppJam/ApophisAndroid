package com.example.apophis_android.ui.firstDay

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apophis_android.R
import com.example.apophis_android.data.entity.ChatData
import com.example.apophis_android.ui.ChipFactory
import com.google.android.material.chip.ChipGroup
import java.lang.IllegalArgumentException

class FirstDayChatAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val chatDataList: MutableList<ChatData> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return when (chatDataList[position].tag) {
            0 -> R.layout.item_chat_left
            1 -> R.layout.item_chat_right
            else -> R.layout.item_chat_choice
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
            R.layout.item_chat_choice -> {
                val view = layoutInflater.inflate(R.layout.item_chat_choice, parent, false)
                ChoiceViewHolder(view, layoutInflater)
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
        if (holder is ChoiceViewHolder) {
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

    inner class ChoiceViewHolder(itemView: View, private val inflater: LayoutInflater) : RecyclerView.ViewHolder(itemView) {

        private var chipGroup: ChipGroup = itemView.findViewById(R.id.chipgroup_choice)

        fun bind(chipItem: MutableList<String>) {
            chipGroup.removeAllViews()
            chipItem.forEach {
                val chip = ChipFactory.newInstance(inflater)
                chip.text = it
                chip.isClickable = true
                chipGroup.addView(chip)

                chip.setOnClickListener {
                    if (chip.text.toString() == "나침반") {
                        val intent = Intent(context, CompassActivity::class.java)
                        context.startActivity(intent)
                    } else if(chip.text.toString() == "카메라"){
                        val intent = Intent(context, CameraActivity::class.java)
                        context.startActivity(intent)
                    } else {
                        chipClickListener.onChipClick(chip.text.toString())
                    }
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return chatDataList.size
    }

    fun addChat(chatItem: ChatData) {
        chatDataList.add(chatItem)
        notifyItemInserted(chatDataList.size)
    }

    fun removeChat() {
        chatDataList.removeAt(chatDataList.size-2)
        notifyItemRemoved(chatDataList.size-1)
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