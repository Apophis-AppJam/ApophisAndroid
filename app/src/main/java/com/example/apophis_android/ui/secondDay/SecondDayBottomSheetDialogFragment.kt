package com.example.apophis_android.ui.secondDay

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import com.example.apophis_android.MainActivity
import com.example.apophis_android.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_second_day_bottom_sheet_dialog.*
import java.text.SimpleDateFormat
import java.util.*


class SecondDayBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var timePicker: TimePicker


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second_day_bottom_sheet_dialog, container, false)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timePicker = view.findViewById(R.id.timepicker_second_day)

        tv_timepicker_complete.setOnClickListener {

            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            cal.set(Calendar.MINUTE, timePicker.minute)

            val hour = SimpleDateFormat("kk").format(cal.time).toInt()
            val time = SimpleDateFormat("kk시 mm분").format(cal.time)
            val text: String = makeText(hour)

            tv_timepicker_complete.setOnClickListener {
                (activity as SecondDayTimepickerActivity).getFragmentData(time, text)
                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.remove(this)
                    ?.commit()
            }
        }
    }

    private fun makeText(hour: Int): String {
        var text = ""
        when (hour) {
            in 1..3 -> {
                text = "차분하고 적막한 새벽에 떠나고 싶어."
            }
            in 4..5 -> {
                text = "동 트기 전 가장 추운 새벽에 떠나고 싶어."
            }
            6 -> {
                text = "아직 아무도 일어나지 않은 아침에 떠나고 싶어."
            }
            in 7..9 -> {
                text = "활기찬 아침에 떠나고 싶어."
            }
            in 10..11 -> {
                text = "포근한 햇살이 쬐는 낮에 떠나고 싶어."
            }
            in 12..16 -> {
                text = "여유로운 낮에 떠나고 싶어."
            }
            in 17..18 -> {
                text = "노을을 볼 수 있는 저녁에 떠나고 싶어."
            }
            in 19..21 -> {
                text = "아직 열기가 식지 않은 저녁에 떠나고 싶어."
            }
            in 22..23 -> {
                text = "고요한 어둠이 깔린 밤에 떠나고 싶어."
            }
            else -> {
                text = "고요한 어둠이 깔린 밤에 떠나고 싶어."
            }
        }
        return text
    }
}