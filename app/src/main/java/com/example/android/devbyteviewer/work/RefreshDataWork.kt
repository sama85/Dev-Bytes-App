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

package com.example.android.devbyteviewer.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.devbyteviewer.database.DatabaseVideo
import com.example.android.devbyteviewer.database.VideosDatabase
import com.example.android.devbyteviewer.repository.VideosRepository
import retrofit2.HttpException

//WHAT ARE CONSTRAINS FOR THIS WORKER? HOW OFTEN IT RUNS?
class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object{
        const val WORK_NAME = "RefreshDataWorker"
    }

    //runs in a coroutine in a background thread
    override suspend fun doWork(): Result {
        val videosDatabase = VideosDatabase.getDatabase(applicationContext)
        val repository = VideosRepository(videosDatabase)

        return try{
            //suspend fucntion can be only called from a coroutine or another suspend function
            repository.refreshDatabase()
            Result.success()
        } catch (exception: HttpException) {
            Result.retry()
        }
    }
}
