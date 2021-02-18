package com.mypractice.waterywater.grid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.fragment_zero_time.view.*

private lateinit var adapter: GridAdapter

class MyAlarmReceiver(contxt: Context,startTim: Int,endTim: Int,viw: View) : BroadcastReceiver() {

        val context =contxt
        val startTime = startTim
        val endTime = endTim
        val view = viw
//          var adapter = adaptr

    override fun onReceive(p0: Context?, p1: Intent?) {
        val requestCode = p1!!.getIntExtra("REQUEST_CODE",-1)
        Log.d("MyAlarmReceiver","Alarm fired with requestCode $requestCode")
        adapterEnable(context,startTime,endTime,view)
        adapter.showToast()
    }

    fun adapterEnable(context: Context,startTime:Int,endTime:Int,view: View) {
        // Get an instance of base adapter
        val adapter = GridAdapter(context, startTime, endTime)

        // Set the grid view adapter
        view.gridView.adapter = adapter

        Log.d("MyAlarmReceiver","${adapter.showToast()}")

    }


}