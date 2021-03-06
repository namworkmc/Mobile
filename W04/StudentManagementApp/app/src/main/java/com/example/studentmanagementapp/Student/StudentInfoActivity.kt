package com.example.studentmanagementapp.Student

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanagementapp.ClassId.ClassIdListActivity
import com.example.studentmanagementapp.R
import java.io.OutputStreamWriter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class StudentInfoActivity : AppCompatActivity() {

    private val REQUEST_CODE = 1111
    private val fileName = "students_info.txt"

    private var fullNameEditText: EditText? = null
    private var dobEditText: EditText? = null
    private var classIdEditText: EditText? = null
    private var genderRadioGr: RadioGroup? = null
    private var saveBtn: Button? = null
    private var dropDownBtn: ImageView? = null

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveBtnHandler() {
        val genderId = genderRadioGr!!.checkedRadioButtonId

        val isEmpty =
            TextUtils.isEmpty(fullNameEditText!!.text) || TextUtils.isEmpty(dobEditText!!.text) || TextUtils.isEmpty(
                classIdEditText!!.text
            ) || genderId == -1

        if (isEmpty) {
            Toast.makeText(this, "All fields to be required", Toast.LENGTH_SHORT).show()
        } else {
            val selectedValue: RadioButton? = findViewById(genderId)
            val gender = selectedValue!!.text as String
            val fullName = fullNameEditText!!.text.toString()
            val dob = dobEditText!!.text.toString()
            val classId = classIdEditText!!.text.toString()

            val student = Student(fullName, dob, gender, classId, R.drawable.ic_baseline_school_24)

            // Đổi activity
            val intent = Intent(this, StudentListActivity::class.java)
            intent.putExtra("StudentInfoActivity", student.toString())
            startActivityForResult(intent, 1111)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_information)

        fullNameEditText = findViewById(R.id.fullNameEditText)
        dobEditText = findViewById(R.id.dobEditText)
        classIdEditText = findViewById(R.id.classIdEditText)
        genderRadioGr = findViewById(R.id.genderRadioGroup)
        saveBtn = findViewById(R.id.saveBtn)
        dropDownBtn = findViewById(R.id.dropDownBtn)

        saveBtn!!.setOnClickListener {
            saveBtnHandler()
        }

        dropDownBtn!!.setOnClickListener {
            val intent = Intent(this, ClassIdListActivity::class.java)
            startActivityForResult(intent, 1111)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val reply = data!!.getStringExtra("ClassIdActivity")
                classIdEditText!!.setText(reply)
            }
        }
    }
}