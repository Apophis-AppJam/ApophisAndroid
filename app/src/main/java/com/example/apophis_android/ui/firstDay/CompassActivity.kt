package com.example.apophis_android.ui.firstDay

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Toast
import com.example.apophis_android.R
import kotlinx.android.synthetic.main.activity_compass.*
import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.timer

class CompassActivity : AppCompatActivity(), SensorEventListener {
    var manager: SensorManager? = null
    var currentDegree: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass)
        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
        manager?.registerListener(
            this, manager?.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
        )

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        val degree: Int = p0?.values?.get(0)?.toInt()!!
        var rotationAnim = RotateAnimation(
            currentDegree.toFloat(), (-degree).toFloat(), Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotationAnim.duration = 210
        rotationAnim.fillAfter = true
        currentDegree = -degree
        iv_compass.startAnimation(rotationAnim)
        if (degree > 80 && degree < 100) {
            Timer().schedule(2000) {
                runOnUiThread {
                    iv_compass_arrow.setImageResource(R.drawable.img_compass_arrow_bold)
                    manager?.unregisterListener(this@CompassActivity)
                    rotationAnim = RotateAnimation(
                        -90.toFloat(), (-90).toFloat(), Animation.RELATIVE_TO_SELF,
                        0.5f, Animation.RELATIVE_TO_SELF, 0.5f
                    )
                    rotationAnim.duration = 210
                    rotationAnim.fillAfter = true
                    currentDegree = -degree
                    iv_compass.startAnimation(rotationAnim)
                    Thread.currentThread().interrupt()
                    Timer().schedule(2000){
                        finish()
                    }
                }
            }
        } else {
            iv_compass.setImageResource(R.drawable.img_compass)
        }

    }
}