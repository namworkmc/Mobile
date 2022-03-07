package com.example.studentmanagementapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class StudentInformation : AppCompatActivity() {
    private var simpleListView: ListView? = null
    private var items = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)

        simpleListView = findViewById(R.id.listView)
        //create simple data
        for (i in 0..200) {
            items.add("Data $i")
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)

        simpleListView!!.adapter = adapter
    }
}