package com.example.studentmanagementapp.Student

import androidx.room.*

@Dao
interface StudentDAO {
    @Query("SELECT * FROM student")
    fun getAllStudents(): List<Student>

    @Query("SELECT * FROM student WHERE fullName = :fullName")
    fun getStudentByFullName(fullName: String): Student

    @Query("SELECT * FROM student WHERE id = :id")
    fun getStudentById(id: String): Student

    @Insert
    fun insertStudent(student: Student)

    @Update
    fun updateStudent(student: Student)

    @Delete
    fun deleteStudent(student: Student)
}