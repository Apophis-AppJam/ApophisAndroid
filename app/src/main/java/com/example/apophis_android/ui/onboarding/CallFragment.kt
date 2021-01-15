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
    val callMessage: MutableList<String> = mutableListOf("안녕", "쟈기야", "ㅎㅇㅎㅇ", "머해")
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

    // call message start each time
    var index = -1
    private lateinit var second: TimerTask
    private val handler = Handler()
    val timer = Timer()
    fun callStart() {

        second = object: TimerTask() {
            override fun run() {
                Update()
            }
        }

        timer.schedule(second, 0, 1500)
    }
    protected fun Update() {
        val updater = object:Runnable {
            override fun run() {
                index++
                if(index == callMessage.size){
                    timer.cancel()
                    (activity as CallActivity).changeCallText()
                    activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.remove(this@CallFragment)
                        ?.commit()
                    Log.i("end","end")
                }else {
                    tv_call_message.setText(callMessage[index])
                    tv_call_message.animation =
                        AnimationUtils.loadAnimation(context, R.anim.fade_in)
                    tv_call_message.animation =
                        AnimationUtils.loadAnimation(context, R.anim.fade_out)
                }
            }
        }
        handler.post(updater)
    }
}
