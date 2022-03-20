package com.example.studentmanagementapp.Student

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1)
abstract class StudentDatabase : RoomDatabase() {
    abstract fun studentDAO(): StudentDAO

    companion object {
        const val DB_NAME = "student_db"
        private var instance: StudentDatabase? = null
        fun getInstance(context: Context): StudentDatabase {
            return instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context, StudentDatabase::class.java,
                DB_NAME
            ).allowMainThreadQueries().build()
    }
}