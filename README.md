

#  ApophisAndroid : *Never-Die-Zombieroid* 🌠🧛‍♀️

<br>



<img src="https://user-images.githubusercontent.com/63586451/104689431-d8683500-5745-11eb-8a57-5532602f7260.jpg" alt="프로필"/>

> SOPT 27기 17th APP-JAM : Apophis 🌠



<br>



### 📌 Part-meeting

------

> 매일 저녁 7시 30분

https://www.notion.so/b562e3c34a9e4641b5025506546260a0



<br> 

### 🎨 Kanban-board

------



https://github.com/Apophis-AppJam/ApophisAndroid/projects/1



<br>

### 🔧 Tools

------

- Android Studio 
- Zeplin
- Figma



<br>



### ✌ Communication tools

------

- Notion
- Slack
- Gather
- Zoom 


<br>



### 🧩 Branch naming

------

- feature : 기능 개발하는 브랜치

  **feature/[이슈번호] _ [기능] _ [layout/inflate]**



<br>

### 💬 Commit message

------



```
ex) [Add] 홈 화면 Layout 작성 완료

    1. 설명1
    2. 설명 2
```

<br>


[Add]         기능추가

[Delete]     삭제

[Update]   기능수정

[Fix]           버그수정

[Docs]       문서정리

[Chore]      잡일



<br>



### 🔗 Dependency

------

```
/* retrofit */
implementation 'com.squareup.retrofit2:retrofit:2.7.2'
implementation 'com.squareup.retrofit2:converter-gson:2.7.1'
implementation 'com.squareup.okhttp3:logging-interceptor:4.2.1'

/* gson */
implementation 'com.google.code.gson:gson:2.8.6'

/* glide */
implementation "com.github.bumptech.glide:glide:4.10.0"
kapt "com.github.bumptech.glide:compiler:4.10.0"

//lottie
implementation 'com.airbnb.android:lottie:3.4.0'

/* recyclerview */
implementation "androidx.recyclerview:recyclerview:1.2.0-alpha02"

/* kakao */
implementation 'com.kakao.sdk:usermgmt:1.28.0'

implementation platform('com.google.firebase:firebase-bom:26.2.0')
implementation 'com.google.firebase:firebase-analytics-ktx'
implementation 'com.google.firebase:firebase-auth:19.1.0'
implementation 'com.google.android.gms:play-services-auth:17.0.0'
compileOnly 'com.google.android.wearable:wearable:2.8.1'

/* camera */
def camerax_version = "1.0.0-beta07"
implementation "androidx.camera:camera-camera2:$camerax_version"
implementation "androidx.camera:camera-lifecycle:$camerax_version"
implementation "androidx.camera:camera-view:1.0.0-alpha14"
```




<br>




### 🧱 Project structure

------

```

```



<br>



### 🎉 Core Function

------

1. 일차별 채팅

<img src="https://user-images.githubusercontent.com/63586451/104733166-a4ab0080-5781-11eb-815d-0ab2df1ee146.jpg" width="320"/> &nbsp;<img src="https://user-images.githubusercontent.com/63586451/104733170-a5dc2d80-5781-11eb-95a9-1b9d60b75f74.jpg" width="320"/>



<br>

```kotlin
package com.example.apophis_android.ui.secondDay

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.apophis_android.R
import com.example.apophis_android.data.entity.OurUserChat
import com.example.apophis_android.data.remote.ApophisService
import com.example.apophis_android.data.remote.request.ReplyFourRequest
import com.example.apophis_android.data.remote.request.ReplyOneRequest
import com.example.apophis_android.data.remote.response.AponymousChatResponse
import com.example.apophis_android.data.remote.response.BaseResponse
import com.example.apophis_android.data.remote.response.ChoiceChatResponse
import com.example.apophis_android.ui.secondDay.adapter.SecondDayChatAdapter
import kotlinx.android.synthetic.main.activity_second_day_chat.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecondDayChatActivity : AppCompatActivity() {

    private lateinit var chatAdapter: SecondDayChatAdapter

    private val apophisService = ApophisService
    private val jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWR4Ijo2LCJpYXQiOjE2MTAxNjM5NjIsImV4cCI6MTYxMDc2ODc2MiwiaXNzIjoiYXBvcGhpcyJ9.gM5avYDIhGybMsXqlvaWwqJCsTfkAjo1lYD2tvxZAdw"
    private var chatDetailsIdx = 23 // 2일차 시작 인덱스 23

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_day_chat)

        initRcv()

        getAponymousChatFromServer(jwt, chatDetailsIdx)

        btn_second_back.setOnClickListener { onBackPressed() }

        constraintLayout_second.setOnClickListener { hideKeyboard() }

        et_second_chat_message.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_chat_send.setImageResource(R.drawable.btn_send_act)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        /* chip click listener 재정의 */
        chatAdapter.setOnItemClickListener(object : SecondDayChatAdapter.OnItemClickListener {
            override fun onItemClick(data: String) {
                // override fun onItemClick(data: MutableList<String>) {

                et_second_chat_message.setText(data)
                et_second_chat_message.setTextColor(Color.parseColor("#FFFFFF"))
                btn_chat_send.setImageResource(R.drawable.btn_send_act)

                /*for (i in dataList.indices) {
                    et_second_chat_message.setText(dataList[i])
                    et_second_chat_message.setTextColor(Color.parseColor("#FFFFFF"))
                    btn_chat_send.setImageResource(R.drawable.btn_send_act)
                }*/
            }
        })

    }

    private fun initRcv() {
        chatAdapter = SecondDayChatAdapter(this)
        rcv_second_chat.adapter = chatAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SecondDayChatAdapter.TIMER_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val text = data?.getStringExtra("text")
                et_second_chat_message.setText(text)
            }
        }
    }

    private fun getAponymousChatFromServer(jwt: String, chatDetailsIdx: Int) {
        apophisService.getInstance()
            .requestAponymousChat(
                jwt = jwt,
                chatDetailsIdx = chatDetailsIdx
            ).enqueue(object : Callback<BaseResponse<AponymousChatResponse>> {
                override fun onFailure(
                    call: Call<BaseResponse<AponymousChatResponse>>,
                    t: Throwable
                ) { //통신 실패
                    Log.d("fail", t.message)
                }

                override fun onResponse(
                    call: Call<BaseResponse<AponymousChatResponse>>,
                    response: Response<BaseResponse<AponymousChatResponse>>
                ) {
                    //통신 성공
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            var tag: Int
                            for (i in response.body()!!.data.chat.indices) {
                                tag = 0

                                val nextAction = response.body()!!.data.chat[i].nextAction
                                if (nextAction == "채팅 이미지") {
                                    tag = 1
                                } else if (nextAction == "백그라운드 이미지 - 눈길 뷰"){
                                    snowBackground()
                                } else if(nextAction == "음성송출1"){
                                    tag = 2
                                }

                                val aponymousChatData = OurUserChat(mutableListOf(response.body()!!.data.chat[i].text), tag)
                                chatAdapter.addChat(aponymousChatData)
                            }

                            val replyType = response.body()!!.data.postInfo.replyType
                            tag = if (replyType == "단일 보기 선택" || replyType == "다중 보기 선택" || replyType == "카테고리 선택") { //chip_choice
                                4
                            } else if (replyType == "단답형 텍스트 입력") { //short answer
                                5
                            } else if (replyType == "기능 액션 버튼 - 시간대 설정") { //timer
                                6
                            } else if (replyType == "기능 액션 버튼 - 두개의 나 ") { //find_me
                                7
                            } else if (replyType == "기능 액션 버튼 - 가치 선택") { //value
                                8
                            } else if(replyType == "장문형 텍스트 입력") { // long answer
                                9
                            } else if (replyType == "일차 종료 (reply 없음)") { //end
                                10
                            } else {
                                3 //chat_user
                            }

                            when (tag) {
                                0, 1, 3, 4 -> {
                                    getChoiceChatFromServer(jwt, chatDetailsIdx, tag)
                                    btn_chat_send.setOnClickListener {
                                        chatAdapter.removeChat()
                                        val userChoice = et_second_chat_message.text.toString()
                                        val chat = OurUserChat(mutableListOf(userChoice), 3)
                                        chatAdapter.addChat(chat)
                                        et_second_chat_message.setText("")
                                        postReplyOneToServer(jwt, chatDetailsIdx, 1, userChoice)
                                    }
                                }
                                5 -> { //short answer
                                    getChoiceChatFromServer(jwt, chatDetailsIdx, tag)
                                    btn_chat_send.setOnClickListener(null)
                                    chatAdapter.setCallbackListener(object : SecondDayChatAdapter.CallbackListener{
                                        override fun callBack(inputTextList: MutableList<String>) {
                                            postReplyFourToServer(jwt, chatDetailsIdx, 4, inputTextList)
                                        }
                                    })
                                }
                                9 -> { //long answer
                                    btn_chat_send.setOnClickListener {
                                        val userChoice = et_second_chat_message.text.toString()
                                        val chat = OurUserChat(mutableListOf(userChoice), 3)
                                        chatAdapter.addChat(chat)
                                        et_second_chat_message.setText("")
                                        postReplyOneToServer(jwt, chatDetailsIdx, 1, userChoice)
                                    }
                                }
                                10 -> {
                                    val user = ""
                                    val chat = OurUserChat(mutableListOf(user), 10)
                                    chatAdapter.addChat(chat)
                                    btn_chat_send.setOnClickListener(null)
                                }
                                else -> {
                                    getChoiceChatFromServer(jwt, chatDetailsIdx, tag)
                                    btn_chat_send.setOnClickListener {
                                        chatAdapter.removeChat()
                                        val userChoice = et_second_chat_message.text.toString()
                                        val chat = OurUserChat(mutableListOf(userChoice), 3)
                                        chatAdapter.addChat(chat)
                                        et_second_chat_message.setText("")
                                        postReplyOneToServer(jwt, chatDetailsIdx, 1, userChoice)
                                    }
                                }
                            }
                        }
                    }
                }
            })
    }

    private fun getChoiceChatFromServer(jwt: String, chatDetailsIdx: Int, tag: Int) {
        apophisService.getInstance()
            .requestChoiceChat(
                jwt = jwt,
                chatDetailsIdx = chatDetailsIdx
            ).enqueue(object : Callback<BaseResponse<ChoiceChatResponse>> {
                override fun onFailure(
                    call: Call<BaseResponse<ChoiceChatResponse>>,
                    t: Throwable
                ) { //통신 실패
                    Log.d("fail", t.message)
                }

                override fun onResponse(
                    call: Call<BaseResponse<ChoiceChatResponse>>,
                    response: Response<BaseResponse<ChoiceChatResponse>>
                ) {
                    //통신 성공
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            val replyNum = response.body()!!.data.replyNum
                            val list = mutableListOf<String>()
                            for (i in response.body()!!.data.choiceWords.indices) {
                                list.add(response.body()!!.data.choiceWords[i].choiceWords)
                            }
                            val choiceChatData = OurUserChat(list, tag)
                            chatAdapter.addChat(choiceChatData)
                        }

                    }
                }
            })
    }

    private fun postReplyOneToServer(jwt: String, chatDetailsIdx: Int, replyNum: Int, replyString: String) {
        apophisService.getInstance()
            .requestOneReply(
                jwt = jwt,
                chatDetailsIdx = chatDetailsIdx,
                replyNum = replyNum,
                body = ReplyOneRequest(replyString)
            ).enqueue(object : Callback<BaseResponse<Unit>> {
                override fun onFailure(
                    call: Call<BaseResponse<Unit>>,
                    t: Throwable
                ) { //통신 실패
                    Log.d("fail", t.message)
                }

                override fun onResponse(
                    call: Call<BaseResponse<Unit>>,
                    response: Response<BaseResponse<Unit>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            if (chatDetailsIdx < 41) {
                                getAponymousChatFromServer(jwt, chatDetailsIdx + 1)
                                btn_chat_send.setImageResource(R.drawable.btn_send_unact)
                            }
                        }
                    }
                }
            })
    }

    private fun postReplyFourToServer(jwt: String, chatDetailsIdx: Int, replyNum: Int, reply: MutableList<String>) {
        apophisService.getInstance()
            .requestFourReply(
                jwt = jwt,
                chatDetailsIdx = chatDetailsIdx,
                replyNum = replyNum,
                body = ReplyFourRequest(reply[0], reply[1], reply[2], reply[3])
            ) .enqueue(object : Callback<BaseResponse<Unit>> {
                override fun onFailure(
                    call: Call<BaseResponse<Unit>>,
                    t: Throwable
                ) { //통신 실패
                    Log.d("fail", t.message)
                }

                override fun onResponse(
                    call: Call<BaseResponse<Unit>>,
                    response: Response<BaseResponse<Unit>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            if (chatDetailsIdx < 41) {
                                getAponymousChatFromServer(jwt, chatDetailsIdx + 1)
                                btn_chat_send.setImageResource(R.drawable.btn_send_unact)
                            }
                        }
                    }
                }
            })
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(et_second_chat_message.windowToken, 0)
    }

    private fun snowBackground() {
        rcv_second_chat.setBackgroundResource(R.color.transparency00FF)
        cl_second_chat_bottom.setBackgroundResource(R.color.transparency00FF)
        cl_second_chat_header.setBackgroundResource(R.color.transparency00FF)
        constraintLayout_second.setBackgroundResource(R.drawable.bg_snowroadxx)
        Handler().postDelayed({
            rcv_second_chat.setBackgroundResource(R.color.black262627)
            cl_second_chat_bottom.setBackgroundResource(R.color.black2C2C2D)
            cl_second_chat_header.setBackgroundResource(R.color.black2C2C2D)
            constraintLayout_second.setBackgroundResource(R.color.black262627)
        }, 40000)
    }
}


```



```kotlin
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
import com.example.apophis_android.ui.secondDay.SecondDayChatActivity
import com.example.apophis_android.ui.secondDay.SecondDayChatEndingActivity
import com.example.apophis_android.ui.secondDay.findMe.SecondDayFindLightMeActivity
import com.example.apophis_android.ui.secondDay.time.SecondDayTimepickerActivity
import com.example.apophis_android.ui.secondDay.value.SecondDayValueActivity
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
            10 -> R.layout.item_chat_ending
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
            R.layout.item_chat_ending -> {
                val view = layoutInflater.inflate(R.layout.item_chat_ending, parent, false)
                EndingViewHolder(view)
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

        if (holder is EndingViewHolder) {
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

    inner class EndingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val btnAction = itemView.findViewById<View>(R.id.btn_ending_okay)
        fun bind() {
            btnAction.setOnClickListener {
                val intent = Intent(context, SecondDayChatEndingActivity::class.java)
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
```





<br>





### 🎵 Android developer & roles

------



|           👩 [김다혜](https://github.com/kimdahyee)           |           👨 [한재현](https://github.com/wogus0333)           |          👩 [조성림](https://github.com/CHOSUNGRIM)           |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="https://user-images.githubusercontent.com/63586451/104689277-98a14d80-5745-11eb-9f92-77bb4dc2a470.jpg" alt="프로필" width="300px" /> | <img src="https://user-images.githubusercontent.com/63586451/104689261-90e1a900-5745-11eb-8bce-b32a6b463cd6.jpg" alt="프로필" width="300px" /> | <img src="https://user-images.githubusercontent.com/63586451/104689254-8f17e580-5745-11eb-93d1-654435cd19a2.jpg" alt="프로필" width="300px" /> |
|     스플래쉬<br/>음악 재생 <br/>메인<br/>편지 받기/쓰기      |                            온보딩                            |            아포피스 뷰 <br/>타이머<br />음성 송출            |
| 채팅 전체 로직 구현<br/>채팅 엔딩 뷰<br />2일차 - 채팅 구현<br />2일차 - 시간 설정<br />2일차 - Find me | 1일차 - 채팅 구현<br />1일차 - 나침반<br />1일차 - 카메라 촬영<br />1일차 / 2일차 - 백그라운드 이미지 전환 | 2일차 - 가치관<br />7일차 - 채팅 구현<br />7일차 - 유서 쓰기 |

<br>
