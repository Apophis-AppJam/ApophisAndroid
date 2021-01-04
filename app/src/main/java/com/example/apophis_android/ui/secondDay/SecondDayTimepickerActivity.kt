package com.example.apophis_android.ui.secondDay
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.marginTop
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
        val tag = intent.getIntExtra("tag", 0)

        if (tag == 1000) {
            tv_clock_time.text = intent.getStringExtra("time")
            tv_timepiker_final_ment.visibility = View.VISIBLE
            btn_time_setting_complete.setImageResource(R.drawable.btn_complete_act)
            tv_time_setting_complete.setTextColor(Color.parseColor("#FFFFFF"))

            /* 원 그리기
            var canvas = Canvas()
            val startAngle = 0F
            val sweepAngle = 90F
            var paint = Paint()
            paint.isAntiAlias = true
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 30F
            paint.color = Color.parseColor("#AB70F5")

            var oval = RectF(100F, 200F, 300F, 400F)
            canvas?.drawArc(oval, startAngle, sweepAngle, true, paint) */
        }
    }

}
