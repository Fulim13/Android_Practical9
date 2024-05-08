package com.example.demo.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update

// Entity ------------------------------------------------------------------------------------------

// TODO(1): Annotation --> @Entity, @PrimaryKey

data class Program(

    var id: String = "",
    var name: String = "",
) {
    // For spinner
    override fun toString() = name
}

// TODO(2): Annotation --> @Entity, @PrimaryKey

data class Student(

    var id: Int = 0,
    var name: String = "",
    var gender: String = "",
    var programId: String = "",
)

// DAO (Data Access Object) ------------------------------------------------------------------------

// TODO(3): Annotation --> @Dao, @Query, @Insert, @Update, @Delete

interface ProgramDao {

    fun getLiveData(): LiveData<List<Program>>


    suspend fun getAll(): List<Program>


    suspend fun get(id: String): Program?


    suspend fun insert(p: Program)


    suspend fun update(p: Program)


    suspend fun delete(p: Program)


    suspend fun deleteAll()
}

// TODO(4): Annotation --> @Dao, @Query, @Insert, @Update, @Delete

interface StudentDao {

    fun getLiveData(): LiveData<List<Student>>


    suspend fun getAll(): List<Student>


    suspend fun get(id: Int): Student?


    suspend fun insert(s: Student)


    suspend fun update(s: Student)


    suspend fun delete(s: Student)


    suspend fun deleteAll()
}

// Database ----------------------------------------------------------------------------------------

// TODO(5): Annotation --> @Database

abstract class DB : RoomDatabase() {
    // TODO(6): List of DAOs


    companion object {
        @Volatile
        private var db: DB? = null

        @Synchronized
        fun get(context: Context): DB {
            db = db ?: Room
                .databaseBuilder(context, DB::class.java, "database.db")
                .build()
            return db!!
        }
    }
}
