package com.example.studentmanagementapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class StudentInformation : AppCompatActivity() {

    private var fullNameEditText: EditText? = null
    private var dobEditText: EditText? = null
    private var classIdEditText: EditText? = null
    private var genderRadioGr: RadioGroup? = null
    private var saveBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_information)

        fullNameEditText = findViewById(R.id.fullNameEditText)
        dobEditText = findViewById(R.id.dobEditText)
        classIdEditText = findViewById(R.id.classIdEditText)
        genderRadioGr = findViewById(R.id.genderRadioGroup)
        saveBtn = findViewById(R.id.saveBtn)

        saveBtn!!.setOnClickListener {
            Log.i("test", "hehe")
        }
    }
}