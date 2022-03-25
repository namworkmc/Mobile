package com.example.example2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.*

class MainActivity : AppCompatActivity() {
    private val fileName = "test01.txt"
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)

        val closeAndSaveBtn: Button = findViewById(R.id.closeAndSaveBtn)
        closeAndSaveBtn.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        try {
            val inputStream: InputStream? = openFileInput(fileName)
            if (inputStream != null) {
                val reader = BufferedReader(InputStreamReader(inputStream))
                val stringBuffer = StringBuffer()
                var line: String? = reader.readLine()
                while (line != null) {
                    stringBuffer.append(line + "\n")
                    line = reader.readLine()
                }
                inputStream.close()
                editText.setText(stringBuffer.toString())
            }
        } catch (e: FileNotFoundException) {
            Toast.makeText(this, "Exception: $e", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()

        try {
            //File will be in "/data/data/$packageName/files/"
            val out = OutputStreamWriter(openFileOutput(fileName, 0))
            out.write(editText.text.toString())
            out.close()
        } catch (t: Throwable) {
            editText.setText(t.message)
        }
    }
}