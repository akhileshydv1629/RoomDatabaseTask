package com.newcompany.roomdatabasetask.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO{

    @Insert
    suspend fun insertUser(user: User):Long

    @Query("Select * from user_table")
    fun getUser():LiveData<List<User>>

    @Update
    suspend fun updateUser(user: User):Int

    @Delete
    suspend fun deleteUser(user: User):Int

    @Query("DELETE FROM user_table")
    suspend fun deleteAll() : Int



}