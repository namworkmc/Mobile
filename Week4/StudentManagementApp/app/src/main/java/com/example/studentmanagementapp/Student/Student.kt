package com.example.studentmanagementapp.Student

data class Student(val fullName: String, val dob: String, val gender: String, val classId: String, val icon: Int) {
    override fun toString(): String {
        return "$fullName - $dob - $gender - $classId"
    }
}
