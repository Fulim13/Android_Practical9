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
@Entity
data class Program(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
) {
    // For spinner
    override fun toString() = name
}

// TODO(2): Annotation --> @Entity, @PrimaryKey
@Entity
data class Student(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    var gender: String = "",
    var programId: String = "",
)

// DAO (Data Access Object) ------------------------------------------------------------------------

// TODO(3): Annotation --> @Dao, @Query, @Insert, @Update, @Delete
@Dao
interface ProgramDao {
    //Live Data No need suspend, not live data need suspend, (create separate thread background to do DB operation)
    @Query("SELECT * FROM program")
    fun getLiveData(): LiveData<List<Program>>

    @Query("SELECT * FROM program")
    suspend fun getAll(): List<Program>

    @Query("SELECT * FROM program WHERE id = :id")
    suspend fun get(id: String): Program?

    // if insert programm, got conflict id , then replace the new one from old one
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(p: Program)

    @Update
    suspend fun update(p: Program)

    @Delete
    suspend fun delete(p: Program)

    @Query("DELETE FROM program")
    suspend fun deleteAll()
}

// TODO(4): Annotation --> @Dao, @Query, @Insert, @Update, @Delete
@Dao
interface StudentDao {
    @Query("SELECT * FROM student")
    fun getLiveData(): LiveData<List<Student>>

    @Query("SELECT * FROM student")
    suspend fun getAll(): List<Student>

    @Query("SELECT * FROM student WHERE id = :id")
    suspend fun get(id: Int): Student?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(s: Student)

    @Update
    suspend fun update(s: Student)

    @Delete
    suspend fun delete(s: Student)

    @Query("DELETE FROM student")
    suspend fun deleteAll()
}

// Database ----------------------------------------------------------------------------------------

// TODO(5): Annotation --> @Database
// if modify the database , add another table in source code, when compile it will have error, so it advice you to change to version 2
// solution: delete whole app with the db, then no problem
@Database(
    entities = [Program::class , Student::class],
    version = 2,
    exportSchema = true
)
abstract class DB : RoomDatabase() {
    // TODO(6): List of DAOs
    abstract val programDao: ProgramDao
    abstract val studentDao: StudentDao

    // Singleton - if got db, used back, if dont got create one
    // companion (static), belong to class
    companion object {
        @Volatile
        private var db: DB? = null

        @Synchronized
        fun get(context: Context): DB {
            db = db ?: Room
                .databaseBuilder(context, DB::class.java, "database.db")
                .fallbackToDestructiveMigration() // add this line
                .build()
            return db!!
        }
    }
}
