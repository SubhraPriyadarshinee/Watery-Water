package com.mypractice.waterywater.grid

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.mypractice.waterywater.R
import kotlinx.android.synthetic.main.griditem.view.*

class GridAdapter(val context: Context, private val startTime:Int, private val endTime:Int) : BaseAdapter() {

    private var list = addTimeStamps()
    var str = ""
    var countOne = 0

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = parent?.context?.
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.griditem,null)

        val txt = view.bucket
        txt.setImageResource(R.drawable.bucket_water)
        val bxt = view.time
        bxt.text = list[position]

        // checking how to make grids checkable only once


        txt.setOnClickListener {
            showToast()
//            makeViewToast(view)

            txt.isClickable=false

            txt.visibility=View.GONE
            bxt.visibility=View.GONE

            list.removeAt(position)

            str += "1"

            notifyDataSetInvalidated()
        }
        return view
    }

     fun showToast() :  Int {
      countOne++
//        Toast.makeText(context,"view clicked", Toast.LENGTH_LONG).show()
        Log.d("MyAlarmReceiver","countOne --- $countOne")

        return countOne
    }

    private fun addTimeStamps(): ArrayList<String>{
        val list = ArrayList<String>()

        for (i in startTime until endTime){
            list.add("$i - ${i+1}")
        }

        return list
    }

    fun makeString():String {
        Log.d("ZeroTime",str)
        return str
    }

    fun makeViewToast(view:View) {
        view.bucket.setOnClickListener {
            Log.d("ZeroTime","Inside MakeViewToast()")
            Toast.makeText(context,"bucket clicked",Toast.LENGTH_SHORT).show()
        }

    }


}