package com.example.apophis_android.ui.seventhDay

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import com.example.apophis_android.R


class SeventhDayTarotFirstFragment : Fragment() {

    var text1 = "텍스트를 입력하세요"
    var question1 = "냐핳"
    var TAROT1_REQUEST_CODE = 100

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_seventh_day_tarot_first, container, false)

        val snakeTarot1 : ImageView = view.findViewById(R.id.tarot1_viewpager_snake_iv)
        val lineTarot1 : ImageView = view.findViewById(R.id.tarot1_viewpager_line_iv)
        val moonTarot1 : ImageView = view.findViewById(R.id.tarot1_viewpager_moon_iv)
        val answerTarot1 : TextView = view.findViewById(R.id.tarot1_viewpager_answer_ed)
        answerTarot1.text = text1
        val questionTarot1 : TextView = view.findViewById(R.id.tarot1_viewpager_question_tv)
        questionTarot1.text = question1

        snakeTarot1.setOnClickListener {
            snakeTarot1.isInvisible = true
            lineTarot1.isInvisible = false
            moonTarot1.isInvisible = false
            answerTarot1.isInvisible = false
            questionTarot1.isInvisible = false
        }

        answerTarot1.setOnClickListener {
            val intent = Intent(activity,SeventhDayTarotAnswerActivity::class.java)
            startActivityForResult(intent, TAROT1_REQUEST_CODE)
        }
        return view
    }
}