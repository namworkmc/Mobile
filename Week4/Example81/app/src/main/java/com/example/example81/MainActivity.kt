package com.example.example81

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var selectedTV: TextView? = null
    private var simpleListView: ListView? = null
    private var items = arrayListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        simpleListView = findViewById(R.id.simpleListView)
        selectedTV = findViewById(R.id.selectedTV)
        //create simple data
        for (i in 0..200) {
            items.add("Data $i")
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)

        simpleListView!!.adapter = adapter
        simpleListView!!.setOnItemClickListener { _, _, i, _ ->
            val text = "Position: " + i.toString() + "\nData: " + items[i]
            selectedTV!!.text = text
        }
    }
}