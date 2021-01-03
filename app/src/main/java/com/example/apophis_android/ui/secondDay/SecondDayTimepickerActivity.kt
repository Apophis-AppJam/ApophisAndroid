package com.example.apophis_android.ui.secondDay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apophis_android.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_second_day_timepicker.*

class SecondDayTimepickerActivity : AppCompatActivity() {

    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_day_timepicker)

        btn_time_setting.setOnClickListener {
            bottomSheetDialog = BottomSheetDialog(applicationContext)
            bottomSheetDialog.setContentView(R.layout.dialog_timepicker)
            bottomSheetDialog.show()
        }
    }
}