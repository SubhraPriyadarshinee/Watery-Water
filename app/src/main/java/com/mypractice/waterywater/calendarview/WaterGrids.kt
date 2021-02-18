package com.mypractice.waterywater.calendarview

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.mypractice.waterywater.R
import com.mypractice.waterywater.databinding.Example2CalendarDayBinding
import com.mypractice.waterywater.databinding.Example2CalendarHeaderBinding
import com.mypractice.waterywater.databinding.FragmentWaterGridsBinding
import com.mypractice.waterywater.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.util.*
import kotlin.collections.ArrayList

class WaterGrids : Fragment() {
    private lateinit var binding : FragmentWaterGridsBinding

    private lateinit var userViewModel: UserViewModel

    private var listOfDates = listOf<String>()
    private var listOfNames = ArrayList<String>()

    private var selectedDate: LocalDate? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_water_grids, container, false)

        val daysOfWeek = daysOfWeekFromLocale()

        binding.exTwoCalendar.setup(YearMonth.of(2021,1), YearMonth.of(2024,12), daysOfWeek.first())

        userViewModel= ViewModelProvider(this).get(UserViewModel::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            textColor()
            addDates()
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            // Will be set when this container is bound. See the dayBinder.
            lateinit var day: CalendarDay
            val textView = Example2CalendarDayBinding.bind(view).exTwoDayText

        }

        binding.exTwoCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.textView
                textView.text = day.date.dayOfMonth.toString()
                textView.setTextColor(Color.WHITE)
                textView.setBackgroundResource(R.drawable.blue_default)

                for (datem in listOfDates) {
                    Log.d("WaterGrids","Inside bind $datem")
                    GlobalScope.launch(Dispatchers.IO){
                        val name = userViewModel.getName(datem)
                        Log.d("WaterGrids","Inside band--- $name")

                        if (datem==day.date.toString()) {

                            if (name.length > 1) {
                                textView.setBackgroundResource(R.drawable.blue_one)
                            }
                            if (name.length > 2) {
                                textView.setBackgroundResource(R.drawable.blue_two)
                            }
                            if (name.length > 3) {
                                textView.setBackgroundResource(R.drawable.blue_three)
                            }
                            if (name.length > 4) {
                                textView.setBackgroundResource(R.drawable.blue_four)
                            }

                            if (name.length > 5) {
                                textView.setBackgroundResource(R.drawable.blue_five)
                            }
                        }

                    }
                }


                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.makeVisible()
                } else
                    textView.makeInVisible()

            }
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val textView = Example2CalendarHeaderBinding.bind(view).exTwoHeaderText
        }
        binding.exTwoCalendar.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                @SuppressLint("SetTextI18n") // Concatenation warning for `setText` call.
                container.textView.text = "${month.yearMonth.month.name.toLowerCase(Locale.ROOT).capitalize(
                    Locale.ROOT
                )
                } ${month.year}"
            }
        }
        return binding.root
    }

    private suspend fun textColor() {
        listOfDates= userViewModel.getDateList()
    }

    private suspend fun addDates() {
        for (date in listOfDates){
            val yo = userViewModel.getName(date)
            listOfNames.add(yo)
        }
    }

}