package com.example.apophis_android.ui.secondDay

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import com.example.apophis_android.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_second_day_timepicker.*
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timePicker = view.findViewById(R.id.timepicker_second_day)

        tv_timepicker_complete.setOnClickListener {

            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            cal.set(Calendar.MINUTE, timePicker.minute)

            val hour = SimpleDateFormat("kk").format(cal.time).toInt()
            val text: String = makeText(hour)

            val intent = Intent(requireContext(), SecondDayTimepickerActivity::class.java)
            intent.putExtra("time", SimpleDateFormat("kk시 mm분").format(cal.time))
            intent.putExtra("text", text)
            intent.putExtra("tag", 1000)
            startActivity(intent)

            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.remove(this)
                ?.commit()

        }
    }

    private fun makeText(hour: Int): String {
        Log.d("dahye", hour.toString())
        var text = ""
        if (hour in 1..3) {
            text = "차분하고 적막한 새벽에 떠나고 싶어."
        } else if (hour in 4..5) {
            text = "동 트기 전 가장 추운 새벽에 떠나고 싶어."
        } else if (hour == 6) {
            text = "아직 아무도 일어나지 않은 아침에 떠나고 싶어."
        } else if (hour in 7..9) {
            text = "활기찬 아침에 떠나고 싶어."
        } else if (hour in 10..11) {
            text = "포근한 햇살이 쬐는 낮에 떠나고 싶어."
        } else if (hour in 12..16) {
            text = "여유로운 낮에 떠나고 싶어."
        } else if (hour in 17..18) {
            text = "노을을 볼 수 있는 저녁에 떠나고 싶어."
        } else if (hour in 19..21) {
            text = "아직 열기가 식지 않은 저녁에 떠나고 싶어."
        } else if (hour in 22..23) {
            text = "고요한 어둠이 깔린 밤에 떠나고 싶어."
        } else {
            text = "고요한 어둠이 깔린 밤에 떠나고 싶어."
        }
        return text
    }

}