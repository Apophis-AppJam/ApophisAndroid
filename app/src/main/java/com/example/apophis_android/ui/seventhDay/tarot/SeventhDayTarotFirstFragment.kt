package com.example.apophis_android.ui.seventhDay.tarot

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import com.example.apophis_android.R
import com.example.apophis_android.ui.seventhDay.tarot.SeventhDayTarotActivity


class SeventhDayTarotFirstFragment : Fragment() {
    var text1 = "텍스트를 입력하세요"
    var question1 = "죽기 전 돌아봤을 때\n 넌 네가 되고 싶었던 네 자신이 있었어?"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_seventh_day_tarot_first, container, false)

        val snakeTarot1: ImageView = view.findViewById(R.id.tarot1_viewpager_snake_iv)
        val lineTarot1: ImageView = view.findViewById(R.id.tarot1_viewpager_line_iv)
        val moonTarot1: ImageView = view.findViewById(R.id.tarot1_viewpager_moon_iv)
        val answerTarot1: TextView = view.findViewById(R.id.tarot1_viewpager_answer_ed)
        answerTarot1.text = text1
        val questionTarot1: TextView = view.findViewById(R.id.tarot1_viewpager_question_tv)
        questionTarot1.text = question1

        snakeTarot1.setOnClickListener {
            snakeTarot1.isInvisible = true
            lineTarot1.isInvisible = false
            moonTarot1.isInvisible = false
            answerTarot1.isInvisible = false
            questionTarot1.isInvisible = false

        }

        answerTarot1.setOnClickListener {
            Handler().postDelayed({snakeTarot1.isInvisible = false
                lineTarot1.isInvisible = true
                moonTarot1.isInvisible = true
                answerTarot1.isInvisible = true
                questionTarot1.isInvisible = true
            },2000)
            (activity as SeventhDayTarotActivity).moveToTarotAnswer(131)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            if(resultCode == Activity.RESULT_OK) {
                val text = data?.getStringExtra("Answer")
                Log.d("성림 타로 다시 오면 값 있는지", text)
            }
        }
    }
}