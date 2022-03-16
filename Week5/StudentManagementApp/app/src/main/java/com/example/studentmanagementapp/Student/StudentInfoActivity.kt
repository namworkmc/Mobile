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

class StudentInfoActivity : AppCompatActivity() {

    private val REQUEST_CODE = 1111

    private var fullNameEditText: EditText? = null
    private var dobEditText: EditText? = null
    private var classIdSpinner: Spinner? = null
    private var genderRadioGroup: RadioGroup? = null
    private var saveBtn: Button? = null

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveBtnHandler() {
        val genderId = genderRadioGroup!!.checkedRadioButtonId
        val classId = classIdSpinner!!.selectedItem.toString()

        val isEmpty =
            TextUtils.isEmpty(fullNameEditText!!.text) || TextUtils.isEmpty(dobEditText!!.text) ||
                    TextUtils.isEmpty(classId) || genderId == -1

        if (isEmpty) {
            Log.i("hehe", "Empty field")
            Toast.makeText(this, "All fields to be required", Toast.LENGTH_SHORT).show()
        } else {
            val selectedValue: RadioButton? = findViewById(genderId)
            val gender = selectedValue!!.text as String
            val fullName = fullNameEditText!!.text.toString()
            val dob = dobEditText!!.text.toString()

            val student = Student(fullName, dob, gender, classId, R.drawable.ic_baseline_school_24)

            // Đổi activity
            val intent = Intent(this, StudentListActivity::class.java)
            intent.putExtra("StudentInfoActivity", student.toString())
            setResult(2, intent)
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_information)

        // Lấy component
        fullNameEditText = findViewById(R.id.fullNameEditText)
        dobEditText = findViewById(R.id.dobEditText)
        classIdSpinner = findViewById(R.id.classIdSpinner)
        genderRadioGroup = findViewById(R.id.genderRadioGroup)
        saveBtn = findViewById(R.id.saveBtn)

        // Load dữ liệu lên spinner và xử lý event
        val options = arrayOf("19KTPM1", "19KTPM2", "19KTPM3")
        classIdSpinner!!.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, options)

        // Event listener
        saveBtn!!.setOnClickListener {
            saveBtnHandler()
        }

//        classIdSpinner!!.setOnClickListener {
//            val intent = Intent(this, ClassIdListActivity::class.java)
//            startActivityForResult(intent, 1111)
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val reply = data!!.getStringExtra("ClassIdActivity")
                if (reply != null) {
                    Log.i("hehe", reply)
                }
//                classIdEditText!!.setText(reply)
            }
        }
    }
}