package com.newcompany.roomdatabasetask.repository

import com.newcompany.roomdatabasetask.api.UserApi
import com.newcompany.roomdatabasetask.db.AppDatabase
import com.newcompany.roomdatabasetask.db.User
import javax.inject.Inject

class UserRepository {


    suspend fun getBreakingNews(countrycode : String, pageNumber : Int, api: UserApi) =
        api.getBreakingNews(countrycode, pageNumber)

    suspend fun searchNews(countrycode : String, pageNumber : Int, api:UserApi) =
        api.searchForNews(countrycode, pageNumber)

    suspend fun insertUser(user: User, appDatabase: AppDatabase)= appDatabase.getUserDAO().insertUser(user)
    fun getAllUsers(appDatabase: AppDatabase)= appDatabase.getUserDAO().getUser()
    suspend fun updateUser(user: User,appDatabase: AppDatabase) = appDatabase.getUserDAO().updateUser(user)
    suspend fun deleteUser(user: User, appDatabase: AppDatabase):Int = appDatabase.getUserDAO().deleteUser(user)
    suspend fun deleteAllUser(appDatabase: AppDatabase):Int = appDatabase.getUserDAO().deleteAll()
}