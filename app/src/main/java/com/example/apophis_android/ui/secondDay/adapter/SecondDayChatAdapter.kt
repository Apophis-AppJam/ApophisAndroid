package com.example.apophis_android.ui.secondDay.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import com.example.apophis_android.ui.secondDay.SecondDayFindLightMeActivity
import com.example.apophis_android.ui.secondDay.SecondDayTimepickerActivity
import com.example.apophis_android.ui.secondDay.SecondDayValueActivity
import com.google.android.material.chip.ChipGroup
import java.lang.IllegalArgumentException

/**
 * Created By kimdahyee
 * on 01월 07일, 2020
 */

class SecondDayChatAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val userChatList: MutableList<OurUserChat> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return when (userChatList[position].tag) {
            0 -> R.layout.item_chat_aponymous
            1 -> R.layout.item_chat_aponymous_image
            2 -> R.layout.item_aponymous_sound
            3, 9 -> R.layout.item_chat_user //9 장문형
            4 -> R.layout.item_chip_choice
            5 -> R.layout.item_chat_short_answer
            6 -> R.layout.item_chat_time
            7 -> R.layout.item_chat_find_me
            else -> R.layout.item_chat_value
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
            R.layout.item_aponymous_sound -> {
                val view = layoutInflater.inflate(R.layout.item_aponymous_sound, parent, false)
                AponymousSoundViewHolder(view)
            }
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
                UserShortAnswerViewHolder(view, callbackListener)
            }
            R.layout.item_chat_time -> {
                val view = layoutInflater.inflate(R.layout.item_chat_time, parent, false)
                TimerActionViewHolder(view)
            }
            R.layout.item_chat_find_me -> {
                val view = layoutInflater.inflate(R.layout.item_chat_find_me, parent, false)
                FindMeActionViewHolder(view)
            }
            R.layout.item_chat_value -> {
                val view = layoutInflater.inflate(R.layout.item_chat_value, parent, false)
                ValueActionViewHolder(view)
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

        if (holder is AponymousSoundViewHolder) {
            holder.bind(userChatList[position].content)
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }

        if (holder is UserViewHolder) {
            holder.bind(userChatList[position].content)
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }

        if (holder is ChoiceChatViewHolder) {
            holder.bind(userChatList[position].content)
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }

        if (holder is UserShortAnswerViewHolder) {
            holder.bind(callbackListener)
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }

        if (holder is TimerActionViewHolder) {
            holder.bind()
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }

        if (holder is FindMeActionViewHolder) {
            holder.bind()
            holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.translate_up)
        }

        if (holder is ValueActionViewHolder) {
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
        fun bind(imageUrl: MutableList<String>) {
            imageUrl.forEach {
                Glide.with(itemView).load(it).into(image)
            }
            image.background = context.getDrawable(R.drawable.round_rectangle_black_23dp)
            image.clipToOutline = true
        }
    }

    inner class AponymousSoundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(text: MutableList<String>) {

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

    inner class UserShortAnswerViewHolder(itemView: View, callbackListener: CallbackListener) : RecyclerView.ViewHolder(itemView) {
        private var num1: TextView = itemView.findViewById(R.id.tv_user_input_1)
        private var num2: TextView = itemView.findViewById(R.id.tv_user_input_2)
        private var num3: TextView = itemView.findViewById(R.id.tv_user_input_3)

        private var input: TextView = itemView.findViewById(R.id.tv_user_input)
        private var input1: TextView = itemView.findViewById(R.id.et_user_input_1)
        private var input2: TextView = itemView.findViewById(R.id.et_user_input_2)
        private var input3: TextView = itemView.findViewById(R.id.et_user_input_3)

        private var btnComplete: TextView = itemView.findViewById(R.id.btn_user_input_complete)
        private val inputTextList: MutableList<String> = mutableListOf()

        fun bind(callbackListener: CallbackListener) {
            input1.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    num1.setTextColor(Color.parseColor("#AB70F5"))
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            input2.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    num2.setTextColor(Color.parseColor("#AB70F5"))
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            input3.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    num3.setTextColor(Color.parseColor("#AB70F5"))
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            btnComplete.setOnClickListener {
                removeChat()
                inputTextList.add(input.text.toString())
                inputTextList.add("첫 번째는 " + input1.text.toString())
                inputTextList.add("두 번째는 " + input2.text.toString())
                inputTextList.add("세 번째는 " + input3.text.toString())

                for (i in inputTextList.indices) {
                    val chatRight = OurUserChat(mutableListOf(inputTextList[i]), 3)
                    addChat(chatRight)
                }
                callbackListener.callBack(inputTextList)
            }
        }
    }

    inner class TimerActionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val btnAction = itemView.findViewById<ImageView>(R.id.btn_timer)
        fun bind() {
            btnAction.setOnClickListener {
                val intent = Intent(context, SecondDayTimepickerActivity::class.java)
                (context as Activity).startActivityForResult(intent, TIMER_ACTIVITY_REQUEST_CODE
                )
            }
        }
    }

    inner class FindMeActionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val btnAction = itemView.findViewById<ImageView>(R.id.btn_find_me)
        fun bind() {
            btnAction.setOnClickListener {
                itemClickListener.onItemClick("이런 모습들이 있는 것 같아.")
                val intent = Intent(context, SecondDayFindLightMeActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    inner class ValueActionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val btnAction = itemView.findViewById<ImageView>(R.id.btn_value)
        fun bind() {
            btnAction.setOnClickListener {
                itemClickListener.onItemClick("했어.")
                val intent = Intent(context, SecondDayValueActivity::class.java)
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

    /* callback listener */
    interface CallbackListener {
        fun callBack(inputTextList: MutableList<String>)
    }

    private lateinit var callbackListener: CallbackListener

    fun setCallbackListener(callbackListener: CallbackListener) {
        this.callbackListener = callbackListener
    }

    companion object {
        const val TIMER_ACTIVITY_REQUEST_CODE = 26
        // 26 = 2일차 viewType 6
        const val FIND_ME_ACTIVITY_REQUEST_CODE = 27
        const val VALUE_ACTIVITY_REQUEST_CODE = 28
    }

}