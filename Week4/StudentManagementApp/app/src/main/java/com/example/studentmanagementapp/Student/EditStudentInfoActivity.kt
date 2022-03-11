package com.example.studentmanagementapp.Student

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("hehe", "item clicked")
        if (requestCode == 1111) {
            if (resultCode == Activity.RESULT_OK) {
                val reply = data!!.getStringExtra("StudentListActivity")
                Log.i("hehe", reply.toString())
                val info = reply!!.split(" - ")
//
//                fullNameEditText!!.setText(info[0])
//                dobEditText!!.setText(info[1])
//                classIdEditText!!.setText(info[3])
            }
        }
    }
}