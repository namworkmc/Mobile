package com.example.studentmanagementapp

import android.app.Activity
import android.widget.ArrayAdapter

class MyAdapter(private val context: Activity, private val classes: ArrayList<ClassId>) :
    ArrayAdapter<ClassId>(context, R.layout.class_list_item) {
}