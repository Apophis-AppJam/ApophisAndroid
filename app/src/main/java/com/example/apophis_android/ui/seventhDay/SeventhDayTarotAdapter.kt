package com.example.apophis_android.ui.seventhDay

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class SeventhDayTarotAdapter (fm : FragmentManager)
    : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    var fragments = listOf<Fragment>()

    override fun getItem(position: Int): Fragment = when(position){
        0 -> SeventhDayTarotFirstFragment()
        1 -> SeventhDayTarotSecondFragment()
        2 -> SeventhDayTarotThirdFragment()
        3 -> SeventhDayTarotFourthFragment()
        4 -> SeventhDayTarotFifthFragment()
        5 -> SeventhDayTarotSixthFragment()
        6 -> SeventhDayTarotSeventhFragment()
        7 -> SeventhDayTarotEighthFragment()
        8 -> SeventhDayTarotNinthFragment()
        else -> throw IllegalStateException("Unexpected position $position")
    }

    override fun getCount(): Int = fragments.size
}