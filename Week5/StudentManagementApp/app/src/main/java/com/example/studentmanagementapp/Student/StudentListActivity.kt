package com.example.studentmanagementapp.Student

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagementapp.R
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths


class StudentListActivity : AppCompatActivity() {
    private val fileName = "students_info.txt"

    private val students = ArrayList<Student>()
    private val studentsName = ArrayList<String>()

    private var recyclerView: RecyclerView? = null
    private var addStudentBtn: Button? = null
    private var searchAutoCompleteTV: AutoCompleteTextView? = null

    private val studentAdapter = StudentAdapter(students)
    private var studentsNameAdapter: ArrayAdapter<String>? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)

        loadStudentList()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView!!.adapter = studentAdapter
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.setHasFixedSize(true)
        studentAdapter.onItemClick = { position, student ->
            val intent = Intent(this, EditStudentInfoActivity::class.java)
            intent.putExtra("StudentListActivity", "$position - $student")
            startActivityForResult(intent, 1111)
        }

        addStudentBtn = findViewById(R.id.addStudentBtn)
        addStudentBtn!!.setOnClickListener {
            val intent = Intent(this, StudentInfoActivity::class.java)
            startActivityForResult(intent, 1111)
        }

        searchAutoCompleteTV = findViewById(R.id.searchAutoCompleteTV)
        studentsNameAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, studentsName)
        searchAutoCompleteTV!!.setAdapter(studentsNameAdapter)
        searchAutoCompleteTV!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
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

                        // Remove info
                        students.removeAt(position)
                        studentAdapter.notifyItemRemoved(position)

                        // Remove name
                        studentsName.removeAt(position)
                        studentsNameAdapter!!.notifyDataSetChanged()
                    }
                }

                1 -> {
                    val reply = data!!.getStringExtra("EditStudentInfo")
                    if (reply != null) {
                        val info = reply.split(" - ")
                        val position = info[0].toInt()

                        // Edit info
                        val student = Student(
                            info[1],
                            info[2],
                            info[3],
                            info[4],
                            R.drawable.ic_baseline_school_24
                        )

                        // Info
                        students[position] = student
                        studentAdapter.notifyItemChanged(position)
                        studentsNameAdapter!!.notifyDataSetChanged()

                        // Name
                        studentsName[position] = student.fullName
                        studentsNameAdapter!!.notifyDataSetChanged()
                    }
                }

                2 -> {
                    // TODO("add new student")
                    val newStudentInfoStr = intent.getStringExtra("StudentInfoActivity")
                    if (newStudentInfoStr != null) {
                        val newStudentInfo = newStudentInfoStr.split(" - ")

                        // Info
                        students.add(
                            Student(
                                newStudentInfo[0],
                                newStudentInfo[1],
                                newStudentInfo[2],
                                newStudentInfo[3],
                                R.drawable.ic_baseline_school_24
                            )
                        )
                        studentAdapter.notifyItemInserted(students.size - 1)


                        // Name
                        studentsName.add(newStudentInfo[0])
                        studentsNameAdapter!!.notifyDataSetChanged()
                    }
                }
            }
        }
    }

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

                        // Push info
                        students.add(
                            Student(
                                info[0],
                                info[1],
                                info[2],
                                info[3],
                                R.drawable.ic_baseline_school_24
                            )
                        )
                        // Push name
                        studentsName.add(info[0])

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
            for (elt in students) {
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