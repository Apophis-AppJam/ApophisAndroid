package com.example.apophis_android.ui.seventhDay

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import com.example.apophis_android.R

class SeventhDayTarotFourthFragment : Fragment() {

    var text4 = "텍스트를 입력하세요"
    var question4 = "냐핳"
    var TAROT4_REQUEST_CODE = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_seventh_day_tarot_fourth, container, false)

        val snakeTarot4 : ImageView = view.findViewById(R.id.tarot4_viewpager_snake_iv)
        val lineTarot4 : ImageView = view.findViewById(R.id.tarot4_viewpager_line_iv)
        val moonTarot4 : ImageView = view.findViewById(R.id.tarot4_viewpager_moon_iv)
        val answerTarot4 : TextView = view.findViewById(R.id.tarot4_viewpager_answer_ed)
        answerTarot4.text = text4
        val questionTarot4 : TextView = view.findViewById(R.id.tarot4_viewpager_question_tv)
        questionTarot4.text = question4

        snakeTarot4.setOnClickListener {
            snakeTarot4.isInvisible = true
            lineTarot4.isInvisible = false
            moonTarot4.isInvisible = false
            answerTarot4.isInvisible = false
            questionTarot4.isInvisible = false
        }

        answerTarot4.setOnClickListener {
            val intent = Intent(activity,SeventhDayTarotAnswerActivity::class.java)
            startActivityForResult(intent, TAROT4_REQUEST_CODE)
        }
        return view
    }
}