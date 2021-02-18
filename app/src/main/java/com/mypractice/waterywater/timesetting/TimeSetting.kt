package com.mypractice.waterywater.timesetting

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mypractice.waterywater.R
import kotlinx.android.synthetic.main.fragment_time_setting.*
import kotlinx.android.synthetic.main.fragment_time_setting.view.*
import java.text.SimpleDateFormat
import java.util.*

class TimeSetting : Fragment() {

    private var startTime = "0"
    private var endTime = "0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_time_setting, container, false)


        view.start_time_button.setOnClickListener {
            lateinit var timeText: String

            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, _ ->
                cal.set(Calendar.HOUR_OF_DAY, hour)

                timeText = SimpleDateFormat("HH", Locale.ENGLISH).format(cal.time)
                start_time_button.text = timeText
                startTime=timeText
            }
            TimePickerDialog(context,timeSetListener,cal.get(Calendar.HOUR_OF_DAY),0,true).show()

        }
        view.end_time_button.setOnClickListener {
            lateinit var timeText: String

            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, _ ->
                cal.set(Calendar.HOUR_OF_DAY, hour)

                timeText = SimpleDateFormat("HH", Locale.ENGLISH).format(cal.time)
                end_time_button.text = timeText
                endTime=timeText
            }
            TimePickerDialog(context,timeSetListener,cal.get(Calendar.HOUR_OF_DAY),0,true).show()
        }


        view.save_button.setOnClickListener {
            findNavController().navigate(TimeSettingDirections.actionTimeSettingToZeroTime(
                    startTime.toInt(), endTime.toInt()))

        }

        return view
    }


}