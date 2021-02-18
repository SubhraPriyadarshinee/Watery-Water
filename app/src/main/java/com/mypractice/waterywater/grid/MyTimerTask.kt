package com.mypractice.waterywater.grid

import android.util.Log
import java.util.*

private const val TAG = "MyTimerTask"
class MyTimerTask : TimerTask(){


    fun getNextSecond () : Date {
        val date1 = Date()
        date1.hours =18
        date1.minutes=22
        return date1
    }

    override fun run() {
        val currentTime = System.currentTimeMillis()
        val stopTime = currentTime + 1000000*60*2 //provide the 2hrs time it should execute 1000*60*60*2
        while (stopTime != System.currentTimeMillis()) {
            Log.d(TAG, "start job= $stopTime & endJob = ${System.currentTimeMillis()}")

        }
    }

}