package com.example.apophis_android.ui.seventhDay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apophis_android.R
import kotlinx.android.synthetic.main.activity_seventh_day_tarot.*

class SeventhDayTarotActivity : AppCompatActivity() {

    private lateinit var viewpagerAdapter : SeventhDayTarotAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seventh_day_tarot)

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
}