package com.example.studentmanagementapp.Student

import java.time.LocalDate

data class Student(val fullName: String, val dob: LocalDate, val gender: String, val classId: String, val icon: Int) {
    override fun toString(): String {
        return "$fullName - $dob - $gender - $classId"
    }
}
