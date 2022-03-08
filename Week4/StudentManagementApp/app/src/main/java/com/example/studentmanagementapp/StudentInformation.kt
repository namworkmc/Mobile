package com.example.studentmanagementapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class StudentInformation : AppCompatActivity() {

    private var fullNameEditText: EditText? = null
    private var dobEditText: EditText? = null
    private var classIdEditText: EditText? = null
    private var genderRadioGr: RadioGroup? = null
    private var saveBtn: Button? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_information)

        fullNameEditText = findViewById(R.id.fullNameEditText)
        dobEditText = findViewById(R.id.dobEditText)
        classIdEditText = findViewById(R.id.classIdEditText)
        genderRadioGr = findViewById(R.id.genderRadioGroup)
        saveBtn = findViewById(R.id.saveBtn)

        saveBtn!!.setOnClickListener {
            val genderId = genderRadioGr!!.checkedRadioButtonId

            val isEmpty =
                TextUtils.isEmpty(fullNameEditText!!.text) || TextUtils.isEmpty(dobEditText!!.text) || TextUtils.isEmpty(
                    classIdEditText!!.text
                ) || genderId == -1

            if (isEmpty) {
                Log.i("hehe", "Empty field")
                Toast.makeText(this, "All fields to be required", Toast.LENGTH_SHORT).show()
            } else {
                val fullName = fullNameEditText!!.text.toString()
                val dob =
                    LocalDate.parse(dobEditText!!.text.toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                val classId = classIdEditText!!.text.toString()

                Log.i("hehe", "$fullName - $dob - $classId")
            }
        }
    }
}