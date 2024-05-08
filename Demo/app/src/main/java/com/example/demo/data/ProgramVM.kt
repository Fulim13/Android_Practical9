package com.example.demo.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProgramVM (val app: Application) : AndroidViewModel(app) {
    // TODO(7): Access to database
    private val db = DB.get(app)

    // 1 to 1 mapping to DAO
    fun getLiveData() = db.programDao.getLiveData()

    suspend fun getAll() = db.programDao.getAll()
    suspend fun get(id: String) = db.programDao.get(id)

    //Why this do not have suspend, because the insert,update , delete and deleteAll, not need return the value, so it can directly run on separate thread
    fun insert(p: Program) = viewModelScope.launch { db.programDao.insert(p) }
    fun update(p: Program) =  viewModelScope.launch { db.programDao.update(p) }
    fun delete(p: Program) = viewModelScope.launch { db.programDao.delete(p) }
    fun deleteAll()        = viewModelScope.launch { db.programDao.deleteAll() }

    // ---------------------------------------------------------------------------------------------

    fun restore() {
        insert(Program("RIS", "Information Security"))
        insert(Program("RIT", "Internet Technology"))
        insert(Program("RSD", "Software Systems Development"))
        insert(Program("RSW", "Software Engineering"))
    }

}