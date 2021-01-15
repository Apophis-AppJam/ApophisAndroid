package com.example.apophis_android.ui.seventhDay.tarot

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.apophis_android.R
import com.example.apophis_android.ui.seventhDay.adapter.SeventhDayTarotAdapter
import kotlinx.android.synthetic.main.activity_seventh_day_tarot.*


class SeventhDayTarotActivity : AppCompatActivity() {

    private lateinit var viewpagerAdapter : SeventhDayTarotAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seventh_day_tarot)

        tarot_btn_back.setOnClickListener { finish() }

        viewpagerAdapter = SeventhDayTarotAdapter(supportFragmentManager)
        viewpagerAdapter.fragments = listOf(
            SeventhDayTarotFirstFragment(),
            SeventhDayTarotSecondFragment(),
            SeventhDayTarotThirdFragment(),
            SeventhDayTarotFourthFragment(),
            SeventhDayTarotFifthFragment(),
            SeventhDayTarotSixthFragment(),
            SeventhDayTarotSeventhFragment(),
            SeventhDayTarotEighthFragment(),
            SeventhDayTarotNinthFragment()
        )

        tarot_viewpager.adapter = viewpagerAdapter
        tarot_viewpager.clipToPadding = false
        tarot_viewpager.setPadding(150, 0, 150, 0)
        tarot_viewpager.setPageMargin(50)
    }

    fun moveToTarotAnswer(idx: Int) {
        val intent = Intent(this, SeventhDayTarotAnswerActivity::class.java)
        intent.putExtra("Idx", idx)
        Log.d("성림",idx.toString())
        startActivity(intent)
    }
}