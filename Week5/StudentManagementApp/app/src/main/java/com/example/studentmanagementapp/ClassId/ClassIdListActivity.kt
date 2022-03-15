package com.example.studentmanagementapp.ClassId

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanagementapp.R

class ClassIdListActivity : AppCompatActivity() {
    private val arrayList = ArrayList<ClassId>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_class)

        val listView: ListView = findViewById(R.id.recyclerView)

        arrayList.add(ClassId("19KTPM1", R.drawable.ic_baseline_school_24))
        arrayList.add(ClassId("19KTPM2", R.drawable.ic_baseline_school_24))
        arrayList.add(ClassId("19KTPM3", R.drawable.ic_baseline_school_24))

        listView.adapter = ClassAdapter(this, R.layout.class_list_item, arrayList)

        listView.setOnItemClickListener { _: AdapterView<*>, _: View, position: Int, _: Long ->
            val replyIntent = Intent()
            replyIntent.putExtra("ClassIdActivity", arrayList[position].id)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
    }
}