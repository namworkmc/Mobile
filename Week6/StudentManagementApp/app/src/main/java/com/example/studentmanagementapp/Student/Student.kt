package com.example.studentmanagementapp.Student

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@Entity(tableName = "student")
data class Student(val fullName: String, val dob: String, val gender: String, val classId: String, val icon: Int) {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()

    override fun toString(): String {
        return "$id - $fullName - $dob - $gender - $classId"
    }
}