package com.example.apophis_android.ui.secondDay.adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.apophis_android.R
import com.example.apophis_android.data.entity.ChatData
import com.example.apophis_android.ui.ChipFactory
import com.google.android.material.chip.ChipGroup

/**
 * Created By kimdahyee
 * on 01월 07일, 2020
 */
 
class SecondDayChoiceAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val chatDataList: MutableList<ChatData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_chat_choice, parent, false)
        return SecondDayChoiceViewHolder(view, layoutInflater)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SecondDayChoiceViewHolder) {
            holder.bind(chatDataList[position].content)
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }
    }

    override fun getItemCount(): Int {
        return chatDataList.size
    }

    inner class SecondDayChoiceViewHolder(itemView: View, inflater: LayoutInflater) : RecyclerView.ViewHolder(itemView) {

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

    fun addChat(chatDataItem: ChatData) {
        chatDataList.add(chatDataItem)
        notifyItemInserted(chatDataList.size)
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