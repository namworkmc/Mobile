package com.example.studentmanagementapp.Student

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.studentmanagementapp.R

class StudentAdapter(private val context: Activity, private val resource: Int, private val items: ArrayList<Student>) :
    ArrayAdapter<Student>(context, resource, items) {
    @SuppressLint("ViewHolder", "InflateParams", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.student_list_item, null)

        val imageView: ImageView = view.findViewById(R.id.imageView)
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val classIdTextView: TextView = view.findViewById(R.id.classIdTextView)
        val infoTextView: TextView = view.findViewById(R.id.infoTextView)

        imageView.setImageResource(items[position].icon)
        nameTextView.text = items[position].fullName
        infoTextView.text = "${items[position].dob} - ${items[position].gender}"
        classIdTextView.text = items[position].classId

        return view
    }
}