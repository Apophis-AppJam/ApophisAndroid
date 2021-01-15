package com.example.apophis_android.ui.seventhDay.tarot

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
import com.example.apophis_android.ui.seventhDay.tarot.SeventhDayTarotActivity


class SeventhDayTarotSecondFragment : Fragment() {
    var text2 = "텍스트를 입력하세요"
    var question2 = "해보고 싶었는데 안해봐서 억울한 게 있어?"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_seventh_day_tarot_second, container, false)

        val snakeTarot2 : ImageView = view.findViewById(R.id.tarot2_viewpager_snake_iv)
        val lineTarot2 : ImageView = view.findViewById(R.id.tarot2_viewpager_line_iv)
        val moonTarot2 : ImageView = view.findViewById(R.id.tarot2_viewpager_moon_iv)
        val answerTarot2 : TextView = view.findViewById(R.id.tarot2_viewpager_answer_ed)
        answerTarot2.text = text2
        val questionTarot2 : TextView = view.findViewById(R.id.tarot2_viewpager_question_tv)
        questionTarot2.text = question2

        snakeTarot2.setOnClickListener {
            snakeTarot2.isInvisible = true
            lineTarot2.isInvisible = false
            moonTarot2.isInvisible = false
            answerTarot2.isInvisible = false
            questionTarot2.isInvisible = false
        }

        answerTarot2.setOnClickListener {
            (activity as SeventhDayTarotActivity).moveToTarotAnswer(132)
        }
        return view
    }
}