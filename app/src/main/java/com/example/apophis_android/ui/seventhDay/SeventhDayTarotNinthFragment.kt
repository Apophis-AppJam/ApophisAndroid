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

class SeventhDayTarotNinthFragment : Fragment() {

    var text9 = "텍스트를 입력하세요"
    var question9 = "냐핳"
    var TAROT9_REQUEST_CODE = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_seventh_day_tarot_ninth, container, false)

        val snakeTarot9 :  ImageView = view.findViewById(R.id.tarot9_viewpager_snake_iv)
        val lineTarot9 : ImageView = view.findViewById(R.id.tarot9_viewpager_line_iv)
        val moonTarot9 : ImageView = view.findViewById(R.id.tarot9_viewpager_moon_iv)
        val answerTarot9 : TextView = view.findViewById(R.id.tarot9_viewpager_answer_ed)
        answerTarot9.text = text9
        val questionTarot9 : TextView = view.findViewById(R.id.tarot9_viewpager_question_tv)
        questionTarot9.text = question9

        snakeTarot9.setOnClickListener {
            snakeTarot9.isInvisible = true
            lineTarot9.isInvisible = false
            moonTarot9.isInvisible = false
            answerTarot9.isInvisible = false
            questionTarot9.isInvisible = false
        }

        answerTarot9.setOnClickListener {
            val intent = Intent(activity,SeventhDayTarotAnswerActivity::class.java)
            startActivityForResult(intent, TAROT9_REQUEST_CODE)
        }
        return view
    }
}