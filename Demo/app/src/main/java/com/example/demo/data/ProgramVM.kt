package com.example.demo.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProgramVM (val app: Application) : AndroidViewModel(app) {
    // TODO(7): Access to database
    private val db = 0

    fun getLiveData() = MutableLiveData<List<Program>>()

    suspend fun getAll() = 0
    suspend fun get(id: String) = 0

    fun insert(p: Program) = 0
    fun update(p: Program) = 0
    fun delete(p: Program) = 0
    fun deleteAll()        = 0

    // ---------------------------------------------------------------------------------------------

    fun restore() {
        insert(Program("RIS", "Information Security"))
        insert(Program("RIT", "Internet Technology"))
        insert(Program("RSD", "Software Systems Development"))
        insert(Program("RSW", "Software Engineering"))
    }

}