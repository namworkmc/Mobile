package com.example.studentmanagementapp.Student.Activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanagementapp.R
import com.example.studentmanagementapp.Student.Student
import com.example.studentmanagementapp.Student.StudentDatabase

class EditStudentInfoActivity : AppCompatActivity() {
    private var db: StudentDatabase? = null

    private var fullNameEditText: EditText? = null
    private var dobEditText: EditText? = null
    private var classIdSpinner: Spinner? = null
    private var genderRadioGroup: RadioGroup? = null

    private var saveBtn: Button? = null
    private var deleteBtn: Button? = null

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveBtnHandler(position: Int, student: Student) {
        val genderId = genderRadioGroup!!.checkedRadioButtonId
        val classId = classIdSpinner!!.selectedItem.toString()

        val isEmpty =
            TextUtils.isEmpty(fullNameEditText!!.text) || TextUtils.isEmpty(dobEditText!!.text) ||
                    TextUtils.isEmpty(classId) || genderId == -1

        if (isEmpty) {
            Toast.makeText(this, "All fields to be required", Toast.LENGTH_SHORT).show()
        } else {
            val selectedValue: RadioButton? = findViewById(genderId)
            val gender = selectedValue!!.text as String
            val fullName = fullNameEditText!!.text.toString()
            val dob = dobEditText!!.text.toString()

            student.fullName = fullName
            student.dob = dob
            student.gender = gender
            student.classId = classId

            // Update student info in database
            db!!.studentDAO().updateStudent(student)

            // Đổi activity
            val intent = Intent()
            intent.putExtra("EditStudentInfo", "$position - $student")
            setResult(StudentActivity.EDIT, intent)
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun deleteBtnHandler(position: Int, student: Student) {
        val genderId = genderRadioGroup!!.checkedRadioButtonId
        val classId = classIdSpinner!!.selectedItem.toString()

        val isEmpty =
            TextUtils.isEmpty(fullNameEditText!!.text) || TextUtils.isEmpty(dobEditText!!.text) ||
                    TextUtils.isEmpty(classId) || genderId == -1

        if (isEmpty) {
            Toast.makeText(this, "All fields to be required", Toast.LENGTH_SHORT).show()
        } else {
            // Đổi activity
            val intent = Intent()
            intent.putExtra("DeleteStudentInfo", "$position - ${student.id}")
            setResult(StudentActivity.DELETE, intent)

            // Delete student in database
            db!!.studentDAO().deleteStudent(student)
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        db = StudentDatabase.getInstance(this)

        // Gán dữ liệu
        fullNameEditText = findViewById(R.id.fullNameEditText)
        dobEditText = findViewById(R.id.dobEditText)
        classIdSpinner = findViewById(R.id.classIdSpinner)
        genderRadioGroup = findViewById(R.id.genderRadioGroup)

        // Load dữ liệu lên spinner và xử lý event
        val options = arrayOf("19KTPM1", "19KTPM2", "19KTPM3")
        classIdSpinner!!.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, options)

        val reply = intent.getStringExtra("StudentListActivity")
        val info = reply!!.split(" - ")

        val position = info[0].toInt()
        val id = info[1]

        val student = db!!.studentDAO().getStudentById(id)

        fullNameEditText!!.setText(student.fullName)
        dobEditText!!.setText(student.dob)
        when (student.gender) {
            "Male" -> findViewById<RadioButton>(R.id.maleRadioButton).isChecked = true
            "Female" -> findViewById<RadioButton>(R.id.femaleRadioButton).isChecked = true
            else -> findViewById<RadioButton>(R.id.otherRadioButton).isChecked = true
        }
        classIdSpinner!!.setSelection(options.indexOf(student.classId))

        // Set event listener
        saveBtn = findViewById(R.id.saveBtn)
        deleteBtn = findViewById(R.id.deleteBtn)

        saveBtn!!.setOnClickListener {
            saveBtnHandler(position, student)
        }

        deleteBtn!!.setOnClickListener {
            deleteBtnHandler(position, student)
        }
    }
}