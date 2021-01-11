package com.example.apophis_android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import com.example.apophis_android.R
import kotlinx.android.synthetic.main.activity_apophis.*
import kotlinx.android.synthetic.main.activity_second_day_value.*
import java.text.NumberFormat
import java.util.*

class ApophisActivity : AppCompatActivity() {

    private lateinit var timeCountDownTimer: CountDownTimer
    private lateinit var distanceCountDownTimer: CountDownTimer
    private lateinit var hour: String
    private lateinit var min: String
    private lateinit var sec: String
    var DATE_CODE = 1
    var YESTERDAY_CHAT_CODE = 1
    var apoDistance = 129201213


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apophis)

        apo_btn_back.setOnClickListener { onBackPressed() }

        when(DATE_CODE){
            1 -> {apo_tv_day_number.text = "6"
                apo_iv_distance.setImageResource(R.drawable.img_apodistance_07)}
            2 -> {apo_tv_day_number.text = "5"
                apo_iv_distance.setImageResource(R.drawable.img_apodistance_06)}
            3 -> {apo_tv_day_number.text = "4"
                apo_iv_distance.setImageResource(R.drawable.img_apodistance_05)}
            4 -> {apo_tv_day_number.text = "3"
                apo_iv_distance.setImageResource(R.drawable.img_apodistance_04)}
            5 -> {apo_tv_day_number.text = "2"
                apo_iv_distance.setImageResource(R.drawable.img_apodistance_03)}
            6 -> {apo_tv_day_number.text = "1"
                apo_iv_distance.setImageResource(R.drawable.img_apodistance_02)}
            7 -> {apo_tv_day_number.text = "0"
                apo_iv_distance.setImageResource(R.drawable.img_apodistance_01)}
        }

        if(YESTERDAY_CHAT_CODE == 1) {
            timeCountDownTimer = object: CountDownTimer(86400 * 1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    getTime()
                    apo_tv_hour_number.text = hour
                    apo_tv_min_number.text = min
                    apo_tv_sec_number.text = sec
                }

                override fun onFinish() {
                    finish()
                }
            }
            timeCountDownTimer.start()

            distanceCountDownTimer = object: CountDownTimer(86400 * 7000, 100) {
                override fun onTick(p0: Long) {
                    decreaseApo()
                    apo_tv_distance_number.text = NumberFormat.getInstance(Locale.getDefault()).format(apoDistance)
                }

                override fun onFinish() {
                    finish()
                }
            }
            distanceCountDownTimer.start()
        } else {
            apo_tv_hour_number.text = "00"
            apo_tv_min_number.text = "00"
            apo_tv_sec_number.text = "00"
        }
    }

    fun getTime() {
        val calendar: Calendar = GregorianCalendar()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        val c_hour = calendar[Calendar.HOUR_OF_DAY]
        val c_min = calendar[Calendar.MINUTE]
        val c_sec = calendar[Calendar.SECOND]

        val baseCal: Calendar = GregorianCalendar(year, month, day, c_hour, c_min, c_sec)
        if(c_hour < 20) {
            var targetCal: Calendar = GregorianCalendar(year, month, day, 20, 0, 0) //비교대상날짜
            val diffSec = (targetCal.timeInMillis - baseCal.timeInMillis) / 1000
            val diffDays = diffSec / (24 * 60 * 60)
            targetCal.add(Calendar.DAY_OF_MONTH, (-diffDays).toInt())
            val hourTime = Math.floor((diffSec / 3600).toDouble()).toInt()
            val minTime = Math.floor(((diffSec - 3600 * hourTime) / 60).toDouble()).toInt()
            val secTime = Math.floor((diffSec - 3600 * hourTime - 60 * minTime).toDouble()).toInt()
            hour = String.format("%02d", hourTime)
            min = String.format("%02d", minTime)
            sec = String.format("%02d", secTime)
        } else {
            lateinit var targetCal : Calendar
            when(setOf(month, day)) {
                setOf(12,31) -> targetCal = GregorianCalendar(year + 1, 1, 1, 20, 0, 0)
                setOf(1,31) -> targetCal = GregorianCalendar(year, month + 1, 1, 20, 0, 0)
                setOf(3,31) -> targetCal = GregorianCalendar(year, month + 1, 1, 20, 0, 0)
                setOf(5,31) -> targetCal = GregorianCalendar(year, month + 1, 1, 20, 0, 0)
                setOf(7,31) -> targetCal = GregorianCalendar(year, month + 1, 1, 20, 0, 0)
                setOf(8,31) -> targetCal = GregorianCalendar(year, month + 1, 1, 20, 0, 0)
                setOf(10,31) -> targetCal = GregorianCalendar(year, month + 1, 1, 20, 0, 0)
                setOf(2,28) -> targetCal = GregorianCalendar(year, month + 1, 1, 20, 0, 0)
                setOf(2,29) -> targetCal = GregorianCalendar(year, month + 1, 1, 20, 0, 0)
                setOf(4,30) -> targetCal = GregorianCalendar(year, month + 1, 1, 20, 0, 0)
                setOf(6,30) -> targetCal = GregorianCalendar(year, month + 1, 1, 20, 0, 0)
                setOf(9,30) -> targetCal = GregorianCalendar(year, month + 1, 1, 20, 0, 0)
                setOf(11,30) -> targetCal = GregorianCalendar(year, month + 1, 1, 20, 0, 0)
                else -> targetCal = GregorianCalendar(year, month, day + 1, 20, 0, 0)
            }
            val diffSec = (targetCal.timeInMillis - baseCal.timeInMillis) / 1000
            val diffDays = diffSec / (24 * 60 * 60)
            targetCal.add(Calendar.DAY_OF_MONTH, (-diffDays).toInt())
            val hourTime = Math.floor((diffSec / 3600).toDouble()).toInt()
            val minTime = Math.floor(((diffSec - 3600 * hourTime) / 60).toDouble()).toInt()
            val secTime = Math.floor((diffSec - 3600 * hourTime - 60 * minTime).toDouble()).toInt()
            hour = String.format("%02d", hourTime)
            min = String.format("%02d", minTime)
            sec = String.format("%02d", secTime)
        }
    }

    fun decreaseApo (): Float {
        apoDistance -= 1
        return apoDistance.toFloat()
    }
}