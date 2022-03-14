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
import com.example.studentmanagementapp.R

class EditStudentInfoActivity : AppCompatActivity() {
    private var fullNameEditText: EditText? = null
    private var dobEditText: EditText? = null
    private var classIdEditText: EditText? = null
    private var genderRadioGroup: RadioGroup? = null

    private var saveBtn: Button? = null
    private var deleteBtn: Button? = null

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveBtnHandler(position: Int) {
        val genderId = genderRadioGroup!!.checkedRadioButtonId

        val isEmpty =
            TextUtils.isEmpty(fullNameEditText!!.text) || TextUtils.isEmpty(dobEditText!!.text) || TextUtils.isEmpty(
                classIdEditText!!.text
            ) || genderId == -1

        if (isEmpty) {
            Toast.makeText(this, "All fields to be required", Toast.LENGTH_SHORT).show()
        } else {
            // Lấy data
            val selectedValue: RadioButton? = findViewById(genderId)
            val gender = selectedValue!!.text as String
            val fullName = fullNameEditText!!.text.toString()
            val dob = dobEditText!!.text.toString()
            val classId = classIdEditText!!.text.toString()

            val student = Student(fullName, dob, gender, classId, R.drawable.ic_baseline_school_24)

            // Trả result activity
            val replyIntent = Intent()
            replyIntent.putExtra("EditStudentInfo", "$position - $student")
            setResult(1, replyIntent)
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        // Gán dữ liệu
        fullNameEditText = findViewById(R.id.fullNameEditText)
        dobEditText = findViewById(R.id.dobEditText)
        classIdEditText = findViewById(R.id.classIdEditText)
        genderRadioGroup = findViewById(R.id.genderRadioGroup)

        val reply = intent.getStringExtra("StudentListActivity")
        val info = reply!!.split(" - ")

        val position = info[0].toInt()
        fullNameEditText!!.setText(info[1])
        dobEditText!!.setText(info[2])
        when (info[3]) {
            "Male" -> findViewById<RadioButton>(R.id.maleRadioButton).isChecked = true
            "Female" -> findViewById<RadioButton>(R.id.femaleRadioButton).isChecked = true
            else -> findViewById<RadioButton>(R.id.otherRadioButton).isChecked = true
        }
        classIdEditText!!.setText(info[4])

        // Set event listener
        saveBtn = findViewById(R.id.saveBtn)
        deleteBtn = findViewById(R.id.deleteBtn)

        saveBtn!!.setOnClickListener {
            saveBtnHandler(position)
        }

        deleteBtn!!.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra("DeleteStudentInfo", "$position")
            setResult(0, replyIntent)
            finish()
        }
    }
}