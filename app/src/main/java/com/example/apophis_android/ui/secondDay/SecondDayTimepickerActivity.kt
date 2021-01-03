package com.example.apophis_android.ui.secondDay
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apophis_android.R
import kotlinx.android.synthetic.main.activity_second_day_timepicker.*

class SecondDayTimepickerActivity : AppCompatActivity() {

    private lateinit var bottomSheetDialogFragment: SecondDayBottomSheetDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_day_timepicker)

        btn_time_setting.setOnClickListener {
            bottomSheetDialogFragment = SecondDayBottomSheetDialogFragment()
            bottomSheetDialogFragment.show(supportFragmentManager, "tag")
        }

        val intent = intent
        tv_clock_time.text = intent.getStringExtra("time")
    }

}