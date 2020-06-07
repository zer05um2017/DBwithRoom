package com.j2d2.roomtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    }

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        super.onDestroy()
    }
}