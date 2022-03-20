package com.example.studentmanagementapp.Student

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "student")
data class Student(val fullName: String, val dob: String, val gender: String, val classId: String, val icon: Int) {
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1

    override fun toString(): String {
        return "$fullName - $dob - $gender - $classId"
    }
}