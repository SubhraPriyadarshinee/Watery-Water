package com.mypractice.waterywater.grid

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mypractice.waterywater.R
import com.mypractice.waterywater.db.User
import com.mypractice.waterywater.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_zero_time.view.*
import kotlinx.android.synthetic.main.griditem.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class ZeroTime : Fragment() {

    private var numberOfGrids = 0
    private lateinit var userViewModel: UserViewModel
    private val TAG = "ZeroTime"
    private lateinit var adapter: GridAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

         val view =  inflater.inflate(R.layout.fragment_zero_time, container, false)

        userViewModel= ViewModelProvider(this).get(UserViewModel::class.java)

        val args = ZeroTimeArgs.fromBundle(requireArguments())
        setHasOptionsMenu(true)

        numberOfGrids = args.endTime - args.startTime

        if (numberOfGrids == 0) {
            userViewModel.hideClockImage(view,requireContext())
        } else {
            userViewModel.makeGridAdapter(requireContext(),args.startTime,args.endTime,view)
        }

        insertDataToDatabase()

//        startTask()
//        alarm()


        return view
    }
    fun alarm() {
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
        val am = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 14)
            set(Calendar.MINUTE, 55)
            set(Calendar.SECOND, 10)
        }

        val textTimer = StringBuilder()
        val requestCode = Random().nextInt()
        val intent = Intent(requireContext(), MyAlarmReceiver::class.java)
        intent.putExtra("REQUEST_CODE", requestCode)
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
        val pi = PendingIntent.getBroadcast(requireContext(), requestCode, intent, 0)
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, 1000 * 60 , pi)
        

        textTimer.append(simpleDateFormat.format(calendar.timeInMillis)).append("\n")
        Log.d("MyAlarmReceiver","Alarm has been set--- $textTimer")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_clock, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId== R.id.clock)
            findNavController().navigate(R.id.action_zeroTime_to_timeSetting)

        if (item.itemId== R.id.themeChanger) {
            //TODO: when themeChanger is clicked, removed grids gets formed again
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Toast.makeText(requireContext(), "light", Toast.LENGTH_LONG).show()
//                    R.id.btn_default -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
//                    R.id.btn_dark -> AppCompatDelegate.MODE_NIGHT_YES
//                    else -> AppCompatDelegate.MODE_NIGHT_YES

        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDatabase() {
        val date = LocalDate.now()
        val name = convertNumberOfGridsToString(5)
        //TODO: change the number 5

        //create user object
        val user = User(date.toString(), name)

        //add data to database
        userViewModel.addUser(user)
        Toast.makeText(context, "Successfully added.", Toast.LENGTH_LONG).show()
    }

    private fun convertNumberOfGridsToString(n: Int) : String {
        var convertedString = ""
        for (i in 1..n){
            convertedString += "0"
        }
        return convertedString
    }

}