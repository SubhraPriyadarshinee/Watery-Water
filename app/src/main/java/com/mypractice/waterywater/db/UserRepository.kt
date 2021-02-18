package com.mypractice.waterywater.db

import androidx.lifecycle.LiveData

class UserRepository (private val userDao: UserDao) {

    val readAllData:LiveData<List<User>> = userDao.readAllData()
    val readDate: LiveData<List<String>> = userDao.readDate()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun getName(date : String) : String{
        return userDao.getName(date)
    }

    suspend fun getDateList() :List<String> {
        return userDao.getDateList()
    }

}