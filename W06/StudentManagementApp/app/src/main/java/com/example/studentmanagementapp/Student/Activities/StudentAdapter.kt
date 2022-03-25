package com.example.studentmanagementapp.Student.Activities

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagementapp.R
import com.example.studentmanagementapp.Student.Student

class StudentAdapter(private val students: ArrayList<Student>) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    var onItemClick: ((Int, Student) -> Unit)? = null

    inner class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val classIdTextView: TextView = view.findViewById(R.id.classIdTextView)
        val infoTextView: TextView = view.findViewById(R.id.infoTextView)

        init {
            view.setOnClickListener {
                onItemClick?.invoke(adapterPosition, students[adapterPosition])
            }
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.student_list_item, null)
        return StudentViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.imageView.setImageResource(students[position].icon)
        holder.nameTextView.text = students[position].fullName
        holder.infoTextView.text = "${students[position].dob} - ${students[position].gender}"
        holder.classIdTextView.text = students[position].classId
    }

    override fun getItemCount(): Int {
        return students.size
    }
}