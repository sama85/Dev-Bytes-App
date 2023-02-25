package com.example.android.devbyteviewer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideoDao{

    @Query("select * from databasevideo")
    fun getAllVideos() : List<DatabaseVideo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //WHY VARARG?
    fun insertAll(vararg videos : DatabaseVideo)

}