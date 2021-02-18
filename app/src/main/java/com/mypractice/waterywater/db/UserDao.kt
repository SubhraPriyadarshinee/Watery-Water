package com.mypractice.waterywater.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user:User)

    @Query("SELECT * FROM user_table")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT name FROM user_table WHERE date = :date")
    suspend fun getName(date : String): String

    @Query("SELECT date FROM user_table")
    suspend fun getDateList() : List<String>

    @Query("SELECT date FROM user_table")
    fun readDate(): LiveData<List<String>>

}