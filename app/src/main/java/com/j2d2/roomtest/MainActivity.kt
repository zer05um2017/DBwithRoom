package com.j2d2.roomtest

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var appDatabase: AppDatabase? = null
        appDatabase = AppDatabase.getInstance(this)

        btnInsert.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                appDatabase?.userDao()?.insert(User(0, "엥", "휴"))
            }
        }

        btnList.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val user = appDatabase?.userDao()?.getAll()
                for (u:User in user!!) {
                    println("${u.uid} => First Name : ${u.firstName.toString()} Last Name : ${u.lastName.toString()}")
                }
            }
        }

        btnDeleteAll.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                appDatabase?.userDao()?.deleteAll()
            }
        }

//        editTextDate.setOnFocusChangeListener { view, hasFocus ->
//            if(hasFocus)
//                Toast.makeText(this@MainActivity, "focused", Toast.LENGTH_SHORT).show()
//            else
//                Toast.makeText(this@MainActivity, "focuse lose", Toast.LENGTH_SHORT).show()
//
//        }

        editTextDate.setOnTouchListener { _: View, event:MotionEvent ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
//                    Toast.makeText(this@MainActivity, "focused", Toast.LENGTH_SHORT).show()
                    val cal = Calendar.getInstance()
                    val y = cal.get(Calendar.YEAR)
                    val m = cal.get(Calendar.MONTH)
                    val d = cal.get(Calendar.DAY_OF_MONTH)


                    val datepickerdialog:DatePickerDialog = DatePickerDialog(this@MainActivity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//                    Toast.makeText(this@MainActivity, "$dayOfMonth $monthOfYear, $year", Toast.LENGTH_SHORT).show()
                        editTextDate.setText("$dayOfMonth $monthOfYear, $year")
                        // Display Selected date in textbox
//                        lblDate.setText("" + dayOfMonth + " " + MONTHS[monthOfYear] + ", " + year)
                    }, y, m, d)

                    datepickerdialog.show()
                    false
                }
            }

            true
        }

        editTextTime.setOnTouchListener { _: View, event:MotionEvent ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
//                    Toast.makeText(this@MainActivity, "focused", Toast.LENGTH_SHORT).show()
                    val c:Calendar= Calendar.getInstance()
                    val hh=c.get(Calendar.HOUR_OF_DAY)
                    val mm=c.get(Calendar.MINUTE)
                    val timePickerDialog:TimePickerDialog=TimePickerDialog(this@MainActivity,
                        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                            editTextTime.setText( ""+hourOfDay + ":" + minute);
                    },hh,mm,true)
                    timePickerDialog.show()
                    false
                }
            }

            true
        }


    }

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        super.onDestroy()
    }
}