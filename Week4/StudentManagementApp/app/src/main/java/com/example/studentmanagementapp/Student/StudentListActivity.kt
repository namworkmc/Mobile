package com.example.studentmanagementapp.Student

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanagementapp.R
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate

class StudentListActivity : AppCompatActivity() {
    private val fileName = "students_info.txt"

    private val arrayList = ArrayList<Student>()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadStudentList() {
        try {
            if (Files.exists(Paths.get(fileName))) {
                Files.createFile(Paths.get(fileName))
            } else {
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

        val newStudentInfoStr = intent.getStringExtra("StudentInfoActivity")
        if (newStudentInfoStr != null) {
            val newStudentInfo = newStudentInfoStr.split(" - ")
            arrayList.add(
                Student(
                    newStudentInfo[0],
                    LocalDate.parse(newStudentInfo[1]),
                    newStudentInfo[2],
                    newStudentInfo[3],
                    R.drawable.ic_baseline_school_24
                )
            )
        }

        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = StudentAdapter(this, R.layout.student_list_item, arrayList)

        listView.setOnItemClickListener { _: AdapterView<*>, _: View, position: Int, _: Long ->
            val intent = Intent(this, EditStudentInfoActivity::class.java)
            intent.putExtra("StudentListActivity", "$position - ${arrayList[position]}")
            startActivityForResult(intent, 1111)
        }
    }

    override fun onStop() {
        super.onStop()
        try {
            //File will be in "/data/data/$packageName/files/"
            val out = OutputStreamWriter(openFileOutput(fileName, 0))
            for (elt in arrayList) {
                out.write(elt.toString())
                out.write("\n")
            }
            out.flush()
            out.close()
        } catch (t: Throwable) {
            Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
        }
    }
}