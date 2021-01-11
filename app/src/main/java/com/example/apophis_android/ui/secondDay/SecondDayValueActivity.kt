package com.example.apophis_android.ui.secondDay

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.apophis_android.R
import kotlinx.android.synthetic.main.activity_second_day_value.*
import kotlin.math.max
import kotlin.arrayOf as arrayOf

class SecondDayValueActivity : AppCompatActivity() {

    private var loveNum = 0
    private var relationNum = 0
    private var achievementNum = 0
    private var satisfactionNum = 0
    private var joyNum = 0
    private var stabilityNum = 0
    private var totalNum = 10
    private var maxNum = 0

    private var loveString = ""
    private var relationString = ""
    private var achievementString = ""
    private var satisfactionString = ""
    private var joyString = ""
    private var stabilityString = ""
    private  var selectedValue = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_day_value)

        value_btn_next.isEnabled = false

        value_btn_back.setOnClickListener { onBackPressed() }

        value_btn_next.setOnClickListener {
            maxNum = maxOf(loveNum, relationNum, achievementNum, satisfactionNum, joyNum, stabilityNum)
            if (maxNum == loveNum) { loveString = "사랑\n" }
            if (maxNum == relationNum) { relationString = "관계\n" }
            if (maxNum == achievementNum) { achievementString = "성취\n" }
            if (maxNum == satisfactionNum) { satisfactionString = "만족\n" }
            if (maxNum == joyNum) { joyString = "즐거움\n" }
            if (maxNum == stabilityNum) { stabilityString = "안정" }
            selectedValue = "$loveString$relationString$achievementString$satisfactionString$joyString$stabilityString"
            val intent = Intent(this, SecondDayValueResultActivity::class.java)
            intent.putExtra("printingValue",selectedValue)
            startActivity(intent)
        }

        value_iv_love.setOnClickListener {
            if (totalNum > 0) {
                choiceLove()
                if (totalNum == 0) {
                    activeNextBtn()
                }
            } else {
                value_iv_love.isEnabled = false
            }
        }

        value_iv_relation.setOnClickListener {
            if (totalNum > 0) {
                choiceRelation()
                if (totalNum == 0) {
                    activeNextBtn()
                }
            } else {
                value_iv_relation.isEnabled = false
            }
        }

        value_iv_achievement.setOnClickListener {
            if (totalNum > 0) {
                choiceAchievement()
                if (totalNum == 0) {
                    activeNextBtn()
                }
            } else {
                value_iv_achievement.isEnabled = false
            }
        }

        value_iv_satisfaction.setOnClickListener {
            if (totalNum > 0) {
                choiceSatisfaction()
                if (totalNum == 0) {
                    activeNextBtn()
                }
            } else {
                value_iv_satisfaction.isEnabled = false
            }
        }

        value_iv_joy.setOnClickListener {
            if (totalNum > 0) {
                choiceJoy()
                if (totalNum == 0) {
                    activeNextBtn()
                }
            } else {
                value_iv_joy.isEnabled = false
            }
        }

        value_iv_stability.setOnClickListener {
            if (totalNum > 0) {
                choiceStability()
                if (totalNum == 0) {
                    activeNextBtn()
                }
            } else {
                value_iv_stability.isEnabled = false
            }
        }

        value_btn_reset.setOnClickListener {
            totalNum = 10
            value_tv_number.text = "$totalNum"
            activeImage()
            value_btn_next.isEnabled = false
            value_btn_next.setBackgroundResource(R.drawable.value_next_btn_yet)
            value_tv_next.setTextColor(Color.parseColor("#5A4F63"))
        }

    }

    private fun choiceLove(): Int {
        loveNum++
        value_tv_love_number.text = "${loveNum}회"
        when (loveNum) {
            1 -> value_iv_love.setBackgroundResource(R.drawable.img_value_1)
            2 -> value_iv_love.setBackgroundResource(R.drawable.img_value_2)
            3 -> value_iv_love.setBackgroundResource(R.drawable.img_value_3)
            4 -> value_iv_love.setBackgroundResource(R.drawable.img_value_4)
            5 -> value_iv_love.setBackgroundResource(R.drawable.img_value_5)
            6 -> value_iv_love.setBackgroundResource(R.drawable.img_value_6)
            7 -> value_iv_love.setBackgroundResource(R.drawable.img_value_7)
            8 -> value_iv_love.setBackgroundResource(R.drawable.img_value_8)
            9 -> value_iv_love.setBackgroundResource(R.drawable.img_value_9)
            10 -> value_iv_love.setBackgroundResource(R.drawable.img_value_10)
        }
        totalNum--
        value_tv_number.text = "$totalNum"
        return totalNum
    }

    private fun choiceRelation(): Int {
        relationNum++
        value_tv_relation_number.text = "${relationNum}회"
        when (relationNum) {
            1 -> value_iv_relation.setBackgroundResource(R.drawable.img_value_1)
            2 -> value_iv_relation.setBackgroundResource(R.drawable.img_value_2)
            3 -> value_iv_relation.setBackgroundResource(R.drawable.img_value_3)
            4 -> value_iv_relation.setBackgroundResource(R.drawable.img_value_4)
            5 -> value_iv_relation.setBackgroundResource(R.drawable.img_value_5)
            6 -> value_iv_relation.setBackgroundResource(R.drawable.img_value_6)
            7 -> value_iv_relation.setBackgroundResource(R.drawable.img_value_7)
            8 -> value_iv_relation.setBackgroundResource(R.drawable.img_value_8)
            9 -> value_iv_relation.setBackgroundResource(R.drawable.img_value_9)
            10 -> value_iv_relation.setBackgroundResource(R.drawable.img_value_10)
        }
        totalNum--
        value_tv_number.text = "$totalNum"
        return totalNum
    }

    private fun choiceAchievement(): Int {
        achievementNum++
        value_tv_achievement_number.text = "${achievementNum}회"
        when (achievementNum) {
            1 -> value_iv_achievement.setBackgroundResource(R.drawable.img_value_1)
            2 -> value_iv_achievement.setBackgroundResource(R.drawable.img_value_2)
            3 -> value_iv_achievement.setBackgroundResource(R.drawable.img_value_3)
            4 -> value_iv_achievement.setBackgroundResource(R.drawable.img_value_4)
            5 -> value_iv_achievement.setBackgroundResource(R.drawable.img_value_5)
            6 -> value_iv_achievement.setBackgroundResource(R.drawable.img_value_6)
            7 -> value_iv_achievement.setBackgroundResource(R.drawable.img_value_7)
            8 -> value_iv_achievement.setBackgroundResource(R.drawable.img_value_8)
            9 -> value_iv_achievement.setBackgroundResource(R.drawable.img_value_9)
            10 -> value_iv_achievement.setBackgroundResource(R.drawable.img_value_10)
        }
        totalNum--
        value_tv_number.text = "$totalNum"
        return totalNum
    }

    private fun choiceSatisfaction(): Int {
        satisfactionNum++
        value_tv_satisfaction_number.text = "${satisfactionNum}회"
        when (satisfactionNum) {
            1 -> value_iv_satisfaction.setBackgroundResource(R.drawable.img_value_1)
            2 -> value_iv_satisfaction.setBackgroundResource(R.drawable.img_value_2)
            3 -> value_iv_satisfaction.setBackgroundResource(R.drawable.img_value_3)
            4 -> value_iv_satisfaction.setBackgroundResource(R.drawable.img_value_4)
            5 -> value_iv_satisfaction.setBackgroundResource(R.drawable.img_value_5)
            6 -> value_iv_satisfaction.setBackgroundResource(R.drawable.img_value_6)
            7 -> value_iv_satisfaction.setBackgroundResource(R.drawable.img_value_7)
            8 -> value_iv_satisfaction.setBackgroundResource(R.drawable.img_value_8)
            9 -> value_iv_satisfaction.setBackgroundResource(R.drawable.img_value_9)
            10 -> value_iv_satisfaction.setBackgroundResource(R.drawable.img_value_10)
        }
        totalNum--
        value_tv_number.text = "$totalNum"
        return totalNum
    }

    private fun choiceJoy(): Int {
        joyNum++
        value_tv_joy_number.text = "${joyNum}회"
        when (joyNum) {
            1 -> value_iv_joy.setBackgroundResource(R.drawable.img_value_1)
            2 -> value_iv_joy.setBackgroundResource(R.drawable.img_value_2)
            3 -> value_iv_joy.setBackgroundResource(R.drawable.img_value_3)
            4 -> value_iv_joy.setBackgroundResource(R.drawable.img_value_4)
            5 -> value_iv_joy.setBackgroundResource(R.drawable.img_value_5)
            6 -> value_iv_joy.setBackgroundResource(R.drawable.img_value_6)
            7 -> value_iv_joy.setBackgroundResource(R.drawable.img_value_7)
            8 -> value_iv_joy.setBackgroundResource(R.drawable.img_value_8)
            9 -> value_iv_joy.setBackgroundResource(R.drawable.img_value_9)
            10 -> value_iv_joy.setBackgroundResource(R.drawable.img_value_10)
        }
        totalNum--
        value_tv_number.text = "$totalNum"
        return totalNum
    }

    private fun choiceStability(): Int {
        stabilityNum++
        value_tv_stability_number.text = "${stabilityNum}회"
        when (stabilityNum) {
            1 -> value_iv_stability.setBackgroundResource(R.drawable.img_value_1)
            2 -> value_iv_stability.setBackgroundResource(R.drawable.img_value_2)
            3 -> value_iv_stability.setBackgroundResource(R.drawable.img_value_3)
            4 -> value_iv_stability.setBackgroundResource(R.drawable.img_value_4)
            5 -> value_iv_stability.setBackgroundResource(R.drawable.img_value_5)
            6 -> value_iv_stability.setBackgroundResource(R.drawable.img_value_6)
            7 -> value_iv_stability.setBackgroundResource(R.drawable.img_value_7)
            8 -> value_iv_stability.setBackgroundResource(R.drawable.img_value_8)
            9 -> value_iv_stability.setBackgroundResource(R.drawable.img_value_9)
            10 -> value_iv_stability.setBackgroundResource(R.drawable.img_value_10)
        }
        totalNum--
        value_tv_number.text = "$totalNum"
        return totalNum
    }

    private fun activeImage() {
        value_iv_love.isEnabled = true
        value_iv_relation.isEnabled = true
        value_iv_achievement.isEnabled = true
        value_iv_satisfaction.isEnabled = true
        value_iv_joy.isEnabled = true
        value_iv_stability.isEnabled = true

        value_tv_love_number.text = "0회"
        value_tv_relation_number.text = "0회"
        value_tv_achievement_number.text = "0회"
        value_tv_satisfaction_number.text = "0회"
        value_tv_joy_number.text = "0회"
        value_tv_stability_number.text = "0회"

        loveNum = 0
        relationNum = 0
        achievementNum = 0
        satisfactionNum = 0
        joyNum = 0
        stabilityNum = 0

        loveString = ""
        relationString = ""
        achievementString = ""
        satisfactionString = ""
        joyString = ""
        stabilityString = ""

        value_iv_love.setBackgroundResource(0)
        value_iv_relation.setBackgroundResource(0)
        value_iv_achievement.setBackgroundResource(0)
        value_iv_satisfaction.setBackgroundResource(0)
        value_iv_joy.setBackgroundResource(0)
        value_iv_stability.setBackgroundResource(0)
    }

    private fun activeNextBtn() {
        value_btn_next.isEnabled = true
        value_btn_next.setBackgroundResource(R.drawable.value_next_btn_done)
        value_tv_next.setTextColor(Color.parseColor("#AB70F5"))
    }
}