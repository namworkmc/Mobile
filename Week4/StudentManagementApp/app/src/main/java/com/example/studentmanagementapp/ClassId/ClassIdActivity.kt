package com.example.studentmanagementapp.ClassId

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanagementapp.R
import com.example.studentmanagementapp.Student.StudentInformationActivity

class ClassIdActivity : AppCompatActivity() {
    private val list = ArrayList<ClassId>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_class)

        val listView: ListView = findViewById(R.id.listView)


        list.add(ClassId("19KTPM1", R.drawable.ic_baseline_school_24))
        list.add(ClassId("19KTPM2", R.drawable.ic_baseline_school_24))
        list.add(ClassId("19KTPM3", R.drawable.ic_baseline_school_24))

        listView.adapter = ClassAdapter(this, R.layout.class_list_item, list)

        listView.setOnItemClickListener { parent: AdapterView<*>, view: View, position: Int, id: Long->
            when (position) {
                0 -> {
                    val replyIntent = Intent()
                    replyIntent.putExtra("ClassIdActivity", "19KTPM1")
                    setResult(Activity.RESULT_OK, replyIntent)
                    finish()
                }
                1 -> {
                    val replyIntent = Intent()
                    replyIntent.putExtra("ClassIdActivity", "19KTPM2")
                    setResult(Activity.RESULT_OK, replyIntent)
                    finish()
                }
                2 -> {
                    val replyIntent = Intent()
                    replyIntent.putExtra("ClassIdActivity", "19KTPM3")
                    setResult(Activity.RESULT_OK, replyIntent)
                    finish()
                }
            }
        }
    }
}