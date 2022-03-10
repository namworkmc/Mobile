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
import com.example.studentmanagementapp.ClassId.ClassIdActivity
import com.example.studentmanagementapp.R
import java.io.OutputStreamWriter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class StudentInformationActivity : AppCompatActivity() {

    private val REQUEST_CODE = 1111
    private val fileName = "students_info.txt"

    private var fullNameEditText: EditText? = null
    private var dobEditText: EditText? = null
    private var classIdEditText: EditText? = null
    private var genderRadioGr: RadioGroup? = null
    private var saveBtn: Button? = null
    private var dropDownBtn: ImageView? = null

    private fun saveBtnHandler(str: String) {
        try {
            //File will be in "/data/data/$packageName/files/"
            val out = OutputStreamWriter(openFileOutput(fileName, 0))
            out.write(str + "\n")
            out.flush()
            out.close()
        } catch (t: Throwable) {
            Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
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

                // Đổi activity
                val intent = Intent(this, StudentListActivity::class.java)
                startActivityForResult(intent, 1111)

                val str = "$fullName - $dob - $classId"
                Log.i("hehe", str)
                saveBtnHandler(str)
            }
        }

        dropDownBtn!!.setOnClickListener {
            Log.i("hehe", "Clicked dropdown")
            val intent = Intent(this, ClassIdActivity::class.java)
            startActivityForResult(intent, 1111)
        }
    }

    override fun onStop() {
        super.onStop()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("hehe", "item clicked")
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val reply = data!!.getStringExtra("ClassIdActivity")
                if (reply != null) {
                    Log.i("hehe", reply)
                }
                classIdEditText!!.setText(reply)
            }
        }
    }
}