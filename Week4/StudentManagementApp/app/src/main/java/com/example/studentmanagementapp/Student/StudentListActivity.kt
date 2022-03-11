package com.example.studentmanagementapp.Student

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.studentmanagementapp.ClassId.ClassId
import com.example.studentmanagementapp.R
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate

class StudentListActivity : AppCompatActivity() {
    private val fileName = "students_info.txt"

    private val arrayList = ArrayList<Student>()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadStudentList() {
        try {
            val inputStream: InputStream? = openFileInput(fileName)
            if (inputStream != null) {
                val reader = BufferedReader(InputStreamReader(inputStream))
                var line: String? = reader.readLine()
                while (line != null) {
                    val info = line.split(" - ")
                    arrayList.add(
                        Student(
                            info[0],
                            LocalDate.parse(info[1]),
                            info[2],
                            info[3],
                            R.drawable.ic_baseline_school_24
                        )
                    )

                    line = reader.readLine()
                }
                inputStream.close()
            }
        } catch (e: FileNotFoundException) {
            Toast.makeText(this, "Exception: $e", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)
        loadStudentList()

        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = StudentAdapter(this, R.layout.student_list_item, arrayList)

        listView.setOnItemClickListener { _: AdapterView<*>, _: View, position: Int, _: Long ->
            val replyIntent = Intent()
            replyIntent.putExtra("StudentListActivity", arrayList[position].toString())
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
    }
}