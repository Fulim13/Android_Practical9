package com.example.demo.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class StudentVM (val app: Application) : AndroidViewModel(app) {
    // TODO(8): Access to database
    private val db = 0

    fun getLiveData() = MutableLiveData<List<Student>>()

    suspend fun getAll() = 0
    suspend fun get(id: Int) = 0

    fun insert(s: Student) = 0
    fun update(s: Student) = 0
    fun delete(s: Student) = 0
    fun deleteAll()        = 0

    // ---------------------------------------------------------------------------------------------

    fun restore() {
        insert(Student(1, "Alice", "F", "RIS"))
        insert(Student(2, "Benny", "M", "RIS"))
        insert(Student(3, "Candy", "F", "RIT"))
        insert(Student(4, "Danny", "M", "RIT"))
        insert(Student(5, "Emily", "F", "RSD"))
        insert(Student(6, "Felix", "M", "RSD"))
        insert(Student(7, "Garry", "F", "RSW"))
        insert(Student(8, "Henry", "M", "RSW"))
    }

    // ---------------------------------------------------------------------------------------------

    fun validate(s: Student): String {
        var e = ""

        if (s.name      == "") e += "- Name required.\n"
        if (s.gender    == "") e += "- Gender required.\n"
        if (s.programId == "") e += "- Program required.\n"

        return e
    }

}