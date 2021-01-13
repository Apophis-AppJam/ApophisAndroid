package com.example.apophis_android.ui.secondDay

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.apophis_android.R
import kotlinx.android.synthetic.main.activity_second_day_timepicker.*

class SecondDayTimepickerActivity : AppCompatActivity() {

    private lateinit var bottomSheetDialogFragment: SecondDayBottomSheetDialogFragment
    private lateinit var time: String
    private lateinit var text: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_day_timepicker)

        btn_timepicker_back.setOnClickListener { onBackPressed() }

        btn_time_setting.setOnClickListener {
            bottomSheetDialogFragment = SecondDayBottomSheetDialogFragment()
            bottomSheetDialogFragment.show(supportFragmentManager, "tag")
        }

        btn_time_setting_complete.setOnClickListener { finish() }


        val intent = intent
        val tag = intent.getIntExtra("tag", 0)

//        if (tag == 1000) {
//            tv_clock_time.text = intent.getStringExtra("time")
//            view_clock_act.visibility = View.VISIBLE
//            tv_timepiker_final_ment.visibility = View.VISIBLE
//            tv_timepiker_final_ment.text = intent.getStringExtra("text")
//            btn_time_setting_complete.setImageResource(R.drawable.btn_complete_act)
//            tv_time_setting_complete.setTextColor(Color.parseColor("#FFFFFF"))
//        }
    }

    fun getFragmentData(time: String, text: String) {
        this.time = time
        this.text = text
        tv_clock_time.text = time
        view_clock_act.visibility = View.VISIBLE
        tv_timepiker_final_ment.visibility = View.VISIBLE
        tv_timepiker_final_ment.text = text
        btn_time_setting_complete.setImageResource(R.drawable.btn_complete_act)
        tv_time_setting_complete.setTextColor(Color.parseColor("#FFFFFF"))
    }
}
