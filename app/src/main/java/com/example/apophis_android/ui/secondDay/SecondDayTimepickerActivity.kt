package com.example.apophis_android.ui.secondDay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import com.example.apophis_android.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_second_day_timepicker.*
import kotlinx.android.synthetic.main.dialog_timepicker.*

class SecondDayTimepickerActivity : AppCompatActivity() {

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var timePicker: TimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_day_timepicker)

        btn_time_setting.setOnClickListener {
            bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(R.layout.dialog_timepicker)
            bottomSheetDialog.show()
        }
    }
}