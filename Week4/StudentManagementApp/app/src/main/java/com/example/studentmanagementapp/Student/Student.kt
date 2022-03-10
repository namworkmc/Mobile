package com.example.studentmanagementapp.Student

import java.time.LocalDate

data class Student(private val fullName: String, private val dob: LocalDate, private val classId: String) {
    override fun toString(): String {
        return "$fullName - $dob - $classId"
    }
}
