package com.example.studentmanagementapp.Student

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@Entity(tableName = "student")
data class Student(
    var fullName: String,
    var dob: String,
    var gender: String,
    var classId: String,
    val icon: Int
) {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()

    constructor(
        id: String,
        fullName: String,
        dob: String,
        gender: String,
        classId: String,
        icon: Int
    ) : this(fullName, dob, gender, classId, icon) {
        this.id = id
    }

    override fun toString(): String {
        return "$id - $fullName - $dob - $gender - $classId"
    }
}