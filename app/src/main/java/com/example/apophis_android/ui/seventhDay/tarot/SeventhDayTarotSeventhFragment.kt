package com.example.apophis_android.ui.seventhDay.tarot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import com.example.apophis_android.R
import com.example.apophis_android.ui.seventhDay.tarot.SeventhDayTarotActivity

class SeventhDayTarotSeventhFragment : Fragment() {

    var text7 = "텍스트를 입력하세요"
    var question7 = "소리내서 그걸 자기 스스로에게 말해줘."
    var TAROT7_REQUEST_CODE = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_seventh_day_tarot_seventh, container, false)

        val snakeTarot7 : ImageView = view.findViewById(R.id.tarot7_viewpager_snake_iv)
        val lineTarot7 : ImageView = view.findViewById(R.id.tarot7_viewpager_line_iv)
        val moonTarot7 : ImageView = view.findViewById(R.id.tarot7_viewpager_moon_iv)
        val answerTarot7 : TextView = view.findViewById(R.id.tarot7_viewpager_answer_ed)
        answerTarot7.text = text7
        val questionTarot7 : TextView = view.findViewById(R.id.tarot7_viewpager_question_tv)
        questionTarot7.text = question7

        snakeTarot7.setOnClickListener {
            snakeTarot7.isInvisible = true
            lineTarot7.isInvisible = false
            moonTarot7.isInvisible = false
            answerTarot7.isInvisible = false
            questionTarot7.isInvisible = false
        }

        answerTarot7.setOnClickListener {
            (activity as SeventhDayTarotActivity).moveToTarotAnswer(137)
        }
        return view
    }
}