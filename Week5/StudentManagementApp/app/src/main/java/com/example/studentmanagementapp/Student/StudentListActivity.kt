package com.example.studentmanagementapp.Student

import android.annotation.SuppressLint
import android.app.Activity
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

    private val baseStudentAL = ArrayList<Student>()
    private val studentAL = ArrayList<Student>()
    private val studentNameAL = ArrayList<String>()

    private var recyclerView: RecyclerView? = null
    private var addStudentBtn: Button? = null
    private var searchAutoCompleteTV: AutoCompleteTextView? = null

    private var studentsNameAdapter: ArrayAdapter<String>? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)

        loadStudentList()

        recyclerView = findViewById(R.id.recyclerView)
        val studentAdapter = StudentAdapter(studentAL)
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
        studentsNameAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, studentNameAL)
        searchAutoCompleteTV!!.setAdapter(studentsNameAdapter)
        searchAutoCompleteTV!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            @SuppressLint("NotifyDataSetChanged")
            override fun onTextChanged(searchStr: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (searchStr!!.isEmpty()) {
                    studentAL.clear()
                    studentAL.addAll(baseStudentAL)
                    recyclerView!!.adapter!!.notifyDataSetChanged()
                } else {
                    studentAL.clear()
                    studentAL.addAll(baseStudentAL.filter { it.fullName.lowercase().startsWith(searchStr.toString().lowercase()) })
                    recyclerView!!.adapter!!.notifyDataSetChanged()
                }
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

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1111) {
            Activity.ACCESSIBILITY_SERVICE
            when (resultCode) {
                StudentActivity.DELETE -> {
                    val reply = data!!.getStringExtra("DeleteStudentInfo")
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

                        // Remove info
                        baseStudentAL.remove(student)
                        studentAL.remove(student)

                        recyclerView!!.adapter!!.notifyItemRemoved(position)

                        // Remove name
                        studentNameAL.removeAt(position)
                        studentsNameAdapter!!.notifyDataSetChanged()
                    }
                }

                StudentActivity.EDIT -> {
                    val reply = data!!.getStringExtra("EditStudentInfo")
                    if (reply != null) {
                        val info = reply.split(" - ")
                        val position = info[0].toInt()

                        // Edit info
                        val studentBeforeModify = studentAL[position]
                        val modifiedStudent = Student(
                            info[1],
                            info[2],
                            info[3],
                            info[4],
                            R.drawable.ic_baseline_school_24
                        )

                        // Info
                        val positionBeforeModify = baseStudentAL.indexOf(studentBeforeModify)

                        baseStudentAL[positionBeforeModify] = modifiedStudent
                        studentAL[position] = modifiedStudent
                        recyclerView!!.adapter!!.notifyItemChanged(position)

                        // Name
                        studentNameAL[positionBeforeModify] = modifiedStudent.fullName
                        studentsNameAdapter!!.notifyDataSetChanged()
                    }
                }

                StudentActivity.ADD -> {
                    // TODO("add new student")
                    val newStudentInfoStr = data!!.getStringExtra("StudentInfoActivity")
                    if (newStudentInfoStr != null) {
                        val newStudentInfo = newStudentInfoStr.split(" - ")

                        // Info
                        val newStudent = Student(
                            newStudentInfo[0],
                            newStudentInfo[1],
                            newStudentInfo[2],
                            newStudentInfo[3],
                            R.drawable.ic_baseline_school_24
                        )
                        baseStudentAL.add(newStudent)
                        studentAL.add(newStudent)

                        recyclerView!!.adapter!!.notifyItemInserted(studentAL.size - 1)

                        // Name
                        studentNameAL.add(newStudentInfo[0])
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
                        baseStudentAL.add(
                            Student(
                                info[0],
                                info[1],
                                info[2],
                                info[3],
                                R.drawable.ic_baseline_school_24
                            )
                        )
                        // Push name
                        studentNameAL.add(info[0])

                        line = reader.readLine()
                    }
                    inputStream.close()
                }
            }

            studentAL.addAll(baseStudentAL)
        } catch (e: FileNotFoundException) {
            Toast.makeText(this, "Exception: $e", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveStudentList() {
        try {
            //File will be in "/data/data/$packageName/files/"
            val out = OutputStreamWriter(openFileOutput(fileName, 0))
            for (elt in studentAL) {
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