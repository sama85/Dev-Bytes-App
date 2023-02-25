package com.example.android.devbyteviewer.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideoDao{

    @Query("select * from databasevideo")
    fun getAllVideos() : LiveData<List<DatabaseVideo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //HOW LIVE DATA MAKES ROOM QUERY FROM UI THREAD EXECUTE IN BG?
    fun insertAll(videos : List<DatabaseVideo>)

}