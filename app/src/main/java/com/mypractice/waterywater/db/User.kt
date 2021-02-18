package com.mypractice.waterywater.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User (
    @PrimaryKey(autoGenerate = false)
    val date: String,
    val name: String="0"
)