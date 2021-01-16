package com.example.apophis_android.ui.onboarding

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.apophis_android.R
import kotlinx.android.synthetic.main.fragment_call.*
import java.util.*

/**
 * Created By hanjaehyeon
 */

class CallFragment : Fragment() {
    val callMessage: MutableList<String> = mutableListOf("어, 드디어 받았다.", "안녕. 전화 잠깐 끊지 말아주라.",
            "방금 7일 후에 지구멸망한다는\n뉴스 속보 봤어?", "넌 남은 일주일을 어떻게 보낼 생각이야?",
            "내가 누구냐고?", "음.. 그냥 아무 번호나 찍어서\n전화했어.", "죽기 전에 이런거 한 번쯤\n해보고 싶었거든.",
            "사실 당황스럽겠지만, 내가 연락할 사람이\n한 명도 없어서 그러는데.", "남은 일주일 동안 나랑\n연락하면서 지내주면 안될까?",
            "만약 괜찮다면... 계속 연락하고 싶어.")
    var index = 0
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_call, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callStart()
    }


//    // call message start each time

    private lateinit var second: TimerTask
    private val handler = Handler()
    val timer = Timer()
    fun callStart() {

        second = object : TimerTask() {
            override fun run() {
                Update()
            }
        }
        timer.schedule(second, 0, 1900)
    }

    protected fun Update() {
        val updater = object : Runnable {
            override fun run() {
                Log.i("index", index.toString())
                if (index == callMessage.size) {
                    timer.cancel()
                    Log.i("index", index.toString())
                    (activity as CallActivity).changeCallText()
                    activity?.supportFragmentManager
                            ?.beginTransaction()
                            ?.remove(this@CallFragment)
                            ?.commit()
                } else {
                    tv_call_message.setText(callMessage[index++])
                    tv_call_message.animation =
                            AnimationUtils.loadAnimation(context, R.anim.fade_in_onboarding)
                    tv_call_message.animation =
                            AnimationUtils.loadAnimation(context, R.anim.fade_out_onboarding)
                }
            }
        }
        handler.post(updater)
    }
}

