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

class SeventhDayTarotThirdFragment : Fragment() {
    var text3 = "텍스트를 입력하세요"
    var question3 = "한 번 쯤 하고 싶었는데 못해본 말이 있을까?"
    var TAROT3_REQUEST_CODE = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_seventh_day_tarot_third, container, false)

        val snakeTarot3 : ImageView = view.findViewById(R.id.tarot3_viewpager_snake_iv)
        val lineTarot3 : ImageView = view.findViewById(R.id.tarot3_viewpager_line_iv)
        val moonTarot3 : ImageView = view.findViewById(R.id.tarot3_viewpager_moon_iv)
        val answerTarot3 : TextView = view.findViewById(R.id.tarot3_viewpager_answer_ed)
        answerTarot3.text = text3
        val questionTarot3 : TextView = view.findViewById(R.id.tarot3_viewpager_question_tv)
        questionTarot3.text = question3

        snakeTarot3.setOnClickListener {
            snakeTarot3.isInvisible = true
            lineTarot3.isInvisible = false
            moonTarot3.isInvisible = false
            answerTarot3.isInvisible = false
            questionTarot3.isInvisible = false
        }

        answerTarot3.setOnClickListener {
            Handler().postDelayed({
                snakeTarot3.isInvisible = false
                lineTarot3.isInvisible = true
                moonTarot3.isInvisible = true
                answerTarot3.isInvisible = true
                questionTarot3.isInvisible = true
            },2000)
            (activity as SeventhDayTarotActivity).moveToTarotAnswer(133)
        }

        return view
    }
}