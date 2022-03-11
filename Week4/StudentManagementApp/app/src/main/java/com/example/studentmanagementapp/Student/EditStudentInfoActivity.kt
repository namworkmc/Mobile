package com.example.studentmanagementapp.Student

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanagementapp.R

class EditStudentInfoActivity : AppCompatActivity() {
    private var fullNameEditText: EditText? = null
    private var dobEditText: EditText? = null
    private var classIdEditText: EditText? = null
    private var genderRadioGroup: RadioGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        fullNameEditText = findViewById(R.id.fullNameEditText)
        dobEditText = findViewById(R.id.dobEditText)
        classIdEditText = findViewById(R.id.classIdEditText)
        genderRadioGroup = findViewById(R.id.genderRadioGroup)

        val reply = intent.getStringExtra("StudentListActivity")
        Log.i("hehe", reply!!)
        val info = reply.split(" - ")

        fullNameEditText!!.setText(info[1])
        dobEditText!!.setText(info[2])
        when (info[3]) {
            "Male" -> findViewById<RadioButton>(R.id.maleRadioButton).isChecked = true
            "Female" -> findViewById<RadioButton>(R.id.femaleRadioButton).isChecked = true
            else -> findViewById<RadioButton>(R.id.otherRadioButton).isChecked = true
        }
        classIdEditText!!.setText(info[4])
    }
}