package com.mypractice.waterywater.viewmodel

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mypractice.waterywater.MainActivity
import com.mypractice.waterywater.db.User
import com.mypractice.waterywater.db.UserDatabase
import com.mypractice.waterywater.db.UserRepository
import com.mypractice.waterywater.grid.GridAdapter
import com.mypractice.waterywater.grid.MyAlarmReceiver
import kotlinx.android.synthetic.main.fragment_zero_time.view.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class UserViewModel(application: Application): AndroidViewModel(application) {

     val readAllData:LiveData<List<User>>
    val readDate:LiveData<List<String>>
    private val repository: UserRepository
    private lateinit var adapter: GridAdapter



    init {
        val userDao=UserDatabase.getDatabase(application).userDao()

        repository= UserRepository(userDao)
        readAllData=repository.readAllData
        readDate = repository.readDate

    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    suspend fun getName(date:String) : String{
        var name = "not assigned"
        val result = viewModelScope.async(Dispatchers.IO) {
            name= repository.getName(date)
            Log.d("UserViewModel","within async  $name")
            return@async name
        }
        result.await()
        Log.d("UserViewModel", name)
        return name
    }


    suspend fun getDateList() : List<String> {

        var dateList:List<String> = emptyList()
        val result = viewModelScope.async(Dispatchers.IO) {
            dateList= repository.getDateList()
            return@async dateList
        }
        result.await()
        return dateList
    }

    fun makeGridAdapter(context: Context,startTime :Int, endTime: Int, view: View) {

        // Get an instance of base adapter
        adapter = GridAdapter(context, startTime, endTime)

        // Set the grid view adapter
        view.gridView.adapter = adapter

        Log.d("ZeroTime", "total number of 1 are ${adapter.makeString()}")

    }

    fun hideClockImage(view: View,context: Context) {
        view.clock_image.visibility=View.VISIBLE
        Toast.makeText(context,"Default time is 0", Toast.LENGTH_LONG).show()

    }

//    fun alarmFromViewModel() {
//        fun alarm() {
//            val simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
//            val am = MainActivity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//
//            val calendar: Calendar = Calendar.getInstance().apply {
//                timeInMillis = System.currentTimeMillis()
//                set(Calendar.HOUR_OF_DAY, 14)
//                set(Calendar.MINUTE, 55)
//                set(Calendar.SECOND, 10)
//            }
//
//            val textTimer = StringBuilder()
//            val requestCode = Random().nextInt()
//            val intent = Intent(requireContext(), MyAlarmReceiver::class.java)
//            intent.putExtra("REQUEST_CODE", requestCode)
//            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
//            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
//            val pi = PendingIntent.getBroadcast(requireContext(), requestCode, intent, 0)
//            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, 1000 * 60 , pi)
//
//
//            textTimer.append(simpleDateFormat.format(calendar.timeInMillis)).append("\n")
//            Log.d("MyAlarmReceiver","Alarm has been set--- $textTimer")
//        }
//
//    }

}