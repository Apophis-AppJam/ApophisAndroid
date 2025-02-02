package com.example.apophis_android.ui.seventhDay.tarot

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import com.example.apophis_android.R


class SeventhDayTarotFifthFragment : Fragment() {
    var text5 = "텍스트를 입력하세요"
    var question5 = "그 사람에게 꼭 전하고 싶었던 말이 뭐야?"
    var TAROT5_REQUEST_CODE = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_seventh_day_tarot_fifth, container, false)

        val snakeTarot5 : ImageView = view.findViewById(R.id.tarot5_viewpager_snake_iv)
        val lineTarot5 : ImageView = view.findViewById(R.id.tarot5_viewpager_line_iv)
        val moonTarot5 : ImageView = view.findViewById(R.id.tarot5_viewpager_moon_iv)
        val answerTarot5 : TextView = view.findViewById(R.id.tarot5_viewpager_answer_ed)
        answerTarot5.text = text5
        val questionTarot5 : TextView = view.findViewById(R.id.tarot5_viewpager_question_tv)
        questionTarot5.text = question5

        snakeTarot5.setOnClickListener {
            snakeTarot5.isInvisible = true
            lineTarot5.isInvisible = false
            moonTarot5.isInvisible = false
            answerTarot5.isInvisible = false
            questionTarot5.isInvisible = false
        }

        answerTarot5.setOnClickListener {
            Handler().postDelayed({
                snakeTarot5.isInvisible = false
                lineTarot5.isInvisible = true
                moonTarot5.isInvisible = true
                answerTarot5.isInvisible = true
                questionTarot5.isInvisible = true
            }, 2000)
            (activity as SeventhDayTarotActivity).moveToTarotAnswer(135)
        }
        return view
    }
}