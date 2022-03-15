package com.example.studentmanagementapp.ClassId

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.studentmanagementapp.R

class ClassAdapter(private val context: Activity, private val resource: Int, private val items: ArrayList<ClassId>) :
    ArrayAdapter<ClassId>(context, resource, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.class_list_item, null)

        val imageView: ImageView = view.findViewById(R.id.imageView)
        val classId: TextView = view.findViewById(R.id.classIdTextView)

        imageView.setImageResource(items[position].icon)
        classId.text = items[position].id

        return view
    }
}