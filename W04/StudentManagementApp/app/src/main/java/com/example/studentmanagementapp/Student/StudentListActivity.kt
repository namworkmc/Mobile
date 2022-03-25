package com.example.studentmanagementapp.Student

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanagementapp.R
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths

class StudentListActivity : AppCompatActivity() {
    private val fileName = "students_info.txt"

    private val arrayList = ArrayList<Student>()

    private var listView: ListView? = null

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadStudentList() {
        try {
            if (Files.exists(Paths.get("$filesDir/$fileName"))) {
                val inputStream: InputStream? = openFileInput(fileName)
                if (inputStream != null) {
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    var line: String? = reader.readLine()
                    while (line != null) {
                        val info = line.split(" - ")
                        arrayList.add(
                            Student(
                                info[0],
                                info[1],
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

    private fun saveStudentList() {
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
                    newStudentInfo[1],
                    newStudentInfo[2],
                    newStudentInfo[3],
                    R.drawable.ic_baseline_school_24
                )
            )
        }

        listView = findViewById(R.id.listView)
        listView!!.adapter = StudentAdapter(this, R.layout.student_list_item, arrayList)

        listView!!.setOnItemClickListener { _: AdapterView<*>, _: View, position: Int, _: Long ->
            val intent = Intent(this, EditStudentInfoActivity::class.java)
            intent.putExtra("StudentListActivity", "$position - ${arrayList[position]}")
            startActivityForResult(intent, 1111)
        }
    }

    override fun onStop() {
        super.onStop()
        saveStudentList()
    }

    override fun onPause() {
        super.onPause()
        saveStudentList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1111) {
            when (resultCode) {
                0 -> {
                    val reply = data!!.getStringExtra("DeleteStudentInfo")
                    if (reply != null) {
                        val position = reply.toInt()
                        arrayList.removeAt(position)
                        (listView!!.adapter as BaseAdapter).notifyDataSetChanged()
                    }
                }

                1 -> {
                    val reply = data!!.getStringExtra("EditStudentInfo")
                    if (reply != null) {
                        val info = reply.split(" - ")
                        val position = info[0].toInt()
                        val student = Student(
                            info[1],
                            info[2],
                            info[3],
                            info[4],
                            R.drawable.ic_baseline_school_24
                        )

                        arrayList.removeAt(position)
                        arrayList.add(position, student)
                        (listView!!.adapter as BaseAdapter).notifyDataSetChanged()
                    }
                }
            }
        }
    }
}