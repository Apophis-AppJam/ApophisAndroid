package com.example.apophis_android.ui.seventhDay.tarot

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import com.example.apophis_android.R
import com.example.apophis_android.ui.seventhDay.tarot.SeventhDayTarotActivity


class SeventhDayTarotEighthFragment : Fragment() {

    var text8 = "텍스트를 입력하세요"
    var question8 = "죽은 너에게 해주고 싶은 말은 뭐야?"
    var TAROT8_REQUEST_CODE = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_seventh_day_tarot_eighth, container, false)

        val snakeTarot8 : ImageView = view.findViewById(R.id.tarot8_viewpager_snake_iv)
        val lineTarot8 : ImageView = view.findViewById(R.id.tarot8_viewpager_line_iv)
        val moonTarot8 : ImageView = view.findViewById(R.id.tarot8_viewpager_moon_iv)
        val answerTarot8 : TextView = view.findViewById(R.id.tarot8_viewpager_answer_ed)
        answerTarot8.text = text8
        val questionTarot8 : TextView = view.findViewById(R.id.tarot8_viewpager_question_tv)
        questionTarot8.text = question8

        snakeTarot8.setOnClickListener {
            snakeTarot8.isInvisible = true
            lineTarot8.isInvisible = false
            moonTarot8.isInvisible = false
            answerTarot8.isInvisible = false
            questionTarot8.isInvisible = false
        }

        answerTarot8.setOnClickListener {
            snakeTarot8.isInvisible = false
            lineTarot8.isInvisible = true
            moonTarot8.isInvisible = true
            answerTarot8.isInvisible = true
            questionTarot8.isInvisible = true
            (activity as SeventhDayTarotActivity).moveToTarotAnswer(138)
        }

        return view
    }
}