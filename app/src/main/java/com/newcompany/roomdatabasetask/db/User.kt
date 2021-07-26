package com.newcompany.roomdatabasetask.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
   data class User(
      @PrimaryKey(autoGenerate = true)
       @ColumnInfo(name = "user_id")
               var  user_id: Int,
       @ColumnInfo(name = "user_name")
               var user_name: String,
       @ColumnInfo(name ="user_mobile")
               var user_mobile: String,
       @ColumnInfo(name = "user_bookname")
               var user_book: String
   )