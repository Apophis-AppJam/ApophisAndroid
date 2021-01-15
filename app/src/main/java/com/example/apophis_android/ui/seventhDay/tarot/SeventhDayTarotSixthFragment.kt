package com.example.apophis_android.ui.seventhDay.tarot

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import com.example.apophis_android.R
import com.example.apophis_android.ui.seventhDay.tarot.SeventhDayTarotActivity

class SeventhDayTarotSixthFragment : Fragment() {
    var text6 = "텍스트를 입력하세요"
    var question6 = "곧 마지막인 지금 이 순간 너에게 꼭 해주고 싶은 말이 있어?"
    var TAROT6_REQUEST_CODE = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_seventh_day_tarot_sixth, container, false)

        val snakeTarot6 : ImageView = view.findViewById(R.id.tarot6_viewpager_snake_iv)
        val lineTarot6 : ImageView = view.findViewById(R.id.tarot6_viewpager_line_iv)
        val moonTarot6 : ImageView = view.findViewById(R.id.tarot6_viewpager_moon_iv)
        val answerTarot6 : TextView = view.findViewById(R.id.tarot6_viewpager_answer_ed)
        answerTarot6.text = text6
        val questionTarot6 : TextView = view.findViewById(R.id.tarot6_viewpager_question_tv)
        questionTarot6.text = question6

        snakeTarot6.setOnClickListener {
            snakeTarot6.isInvisible = true
            lineTarot6.isInvisible = false
            moonTarot6.isInvisible = false
            answerTarot6.isInvisible = false
            questionTarot6.isInvisible = false
        }

        answerTarot6.setOnClickListener {
            Handler().postDelayed({
                snakeTarot6.isInvisible = false
                lineTarot6.isInvisible = true
                moonTarot6.isInvisible = true
                answerTarot6.isInvisible = true
                questionTarot6.isInvisible = true
            },2000)
            (activity as SeventhDayTarotActivity).moveToTarotAnswer(136)
        }

        return view
    }
}