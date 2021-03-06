package com.example.studentmanagementapp.Student.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagementapp.R
import com.example.studentmanagementapp.Student.Student
import com.example.studentmanagementapp.Student.StudentDatabase
import java.io.FileNotFoundException


class StudentListActivity : AppCompatActivity() {
    private val fileName = "students_info.json"
    private var isToggle = false

    private var baseStudentAL = ArrayList<Student>()
    private val studentAL = ArrayList<Student>()
    private val studentNameAL = ArrayList<String>()
    private var studentsNameAdapter: ArrayAdapter<String>? = null
    private var db: StudentDatabase? = null

    private var recyclerView: RecyclerView? = null
    private var addStudentBtn: Button? = null
    private var searchAutoCompleteTV: AutoCompleteTextView? = null
    private var toggleButton: ImageButton? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)

        db = StudentDatabase.getInstance(this)
        loadStudentList()

        // Recycler View
        recyclerView = findViewById(R.id.recyclerView)
        val studentAdapter = StudentAdapter(studentAL)
        recyclerView!!.adapter = studentAdapter
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.setHasFixedSize(true)
        studentAdapter.onItemClick = { position, student ->
            val intent = Intent(this, EditStudentInfoActivity::class.java)
            intent.putExtra("StudentListActivity", "$position - ${student.id}")
            startActivityForResult(intent, 1111)
        }

        // Add button
        addStudentBtn = findViewById(R.id.addStudentBtn)
        addStudentBtn!!.setOnClickListener {
            val intent = Intent(this, AddStudentInfoActivity::class.java)
            startActivityForResult(intent, 1111)
        }

        // Search autocomplete text view
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
                    studentAL.addAll(baseStudentAL.filter {
                        it.fullName.lowercase().startsWith(searchStr.toString().lowercase())
                    })
                    recyclerView!!.adapter!!.notifyDataSetChanged()
                }
            }
        })

        // Toggle image button
        toggleButton = findViewById(R.id.toggleButton)
        toggleButton!!.setOnClickListener {
            isToggle = !isToggle
            if (isToggle) {
                recyclerView!!.layoutManager =
                    GridLayoutManager(this, 2)
                toggleButton!!.setImageResource(R.drawable.ic_baseline_grid_off_24)
            } else {
                recyclerView!!.layoutManager =
                    LinearLayoutManager(this)
                toggleButton!!.setImageResource(R.drawable.ic_baseline_grid_on_24)
            }
        }
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
                        val id = info[1]

                        // Remove info
                        val position = studentAL.indexOfFirst { it.id == id }
                        val removedStudent = studentAL.removeAt(position)

                        baseStudentAL.removeIf { it.id == id }
                        studentAL.removeIf { it.id == id }

                        recyclerView!!.adapter!!.notifyItemRemoved(position)

                        // Remove name
                        studentNameAL.remove(removedStudent.fullName)
                        studentsNameAdapter!!.notifyDataSetChanged()
                    }
                }

                StudentActivity.EDIT -> {
                    val reply = data!!.getStringExtra("EditStudentInfo")
                    if (reply != null) {
                        val info = reply.split(" - ")
                        val position = info[0].toInt()
                        val id = info[1]

                        // Edit info
                        val studentBeforeModify = studentAL[position]

                        val modifiedStudent = db!!.studentDAO().getStudentById(id)

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
                    val id = data!!.getStringExtra("StudentInfoActivity")
                    if (id != null) {
                        // Info
                        val newStudent = db!!.studentDAO().getStudentById(id)
                        baseStudentAL.add(newStudent)
                        studentAL.add(newStudent)

                        recyclerView!!.adapter!!.notifyItemInserted(studentAL.size - 1)

                        // Name
                        studentNameAL.add(newStudent.fullName)
                        studentsNameAdapter!!.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadStudentList() {
        try {
            baseStudentAL.addAll(db!!.studentDAO().getAllStudents())
            studentAL.addAll(baseStudentAL)

            studentNameAL.addAll(baseStudentAL.map { it.fullName })
        } catch (e: FileNotFoundException) {
            Toast.makeText(this, "Exception: $e", Toast.LENGTH_SHORT).show()
        }
    }
}