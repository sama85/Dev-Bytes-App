/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.devbyteviewer.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class VideosDatabase : RoomDatabase() {
    //DAO interface is implemented by room
    abstract val videoDao: VideoDao

    companion object {
        var INSTANCE: VideosDatabase? = null
        fun getDatabase(context: Context): VideosDatabase {
            synchronized(this) {
                //HOW DID SMART CAST WORK FOR instance? IT'S MODIFIED BETWEEN CHECK AND USE
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, VideosDatabase::class.java,
                        "videos_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}