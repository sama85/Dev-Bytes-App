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

package com.example.android.devbyteviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.devbyteviewer.database.DatabaseVideo
import com.example.android.devbyteviewer.database.VideosDatabase
import com.example.android.devbyteviewer.database.asDomainModel
import com.example.android.devbyteviewer.domain.Video
import com.example.android.devbyteviewer.network.Network
import com.example.android.devbyteviewer.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


//repsoitiory should know about data sources -> database and network
//database instance is passed, while network can be globally accessed through network object
class VideosRepository(private val videosDatabase: VideosDatabase){

    //video playlist that's observed in app
    //HOW DOES LIVE DATA RUN QUERY ONLY WHEN THIS DATA IS OBSERVED?
    val videos : LiveData<List<Video>> = Transformations.map(videosDatabase.videoDao.getAllVideos()){
        it.asDomainModel()
    }

    /** 1. fetch data from network
        2. updte database with fetched data */
    suspend fun refreshDatabase(){
        //changes coroutine scope to a background coroutine scope (dispatchers.io) to not block ui scope
        withContext(Dispatchers.IO){
            //await is suspend function so it doesn't block coroutine scope
            //allowing other coroutines running in same scope to run
            val playlist = Network.devbytes.getPlaylist().await()
            videosDatabase.videoDao.insertAll(playlist.asDatabaseModel())
        }
    }
}