package com.example.apophis_android.ui

import android.view.LayoutInflater
import com.example.apophis_android.R
import com.google.android.material.chip.Chip

/**
 * Created By kimdahyee
 * on 01월 03일, 2020
 */

class ChipFactory {
    companion object {
        fun newInstance(layoutInflater: LayoutInflater): Chip {
            return layoutInflater.inflate(R.layout.chip_choice, null, false) as Chip
        }
    }
}
