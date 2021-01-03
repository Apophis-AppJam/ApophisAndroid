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

            val intent = Intent(requireContext(), SecondDayTimepickerActivity::class.java)
            intent.putExtra("time", SimpleDateFormat("kk시 mm분").format(cal.time))
            startActivity(intent)

            returnTransition

        }
    }

}