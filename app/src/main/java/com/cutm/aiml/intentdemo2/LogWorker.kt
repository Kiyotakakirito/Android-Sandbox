package com.cutm.aiml.intentdemo2

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.net.HttpURLConnection
import java.net.URL

class LogWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
        private const val TAG = "LogWorker"
        const val KEY_FILE_URL = "FileUrl"
    }

    override fun doWork(): Result {
        val fileUrl = inputData.getString(KEY_FILE_URL) ?: return Result.failure()

        return try {
            Log.d(TAG, "Background worker started. Target URL: $fileUrl")
            
            val url = URL(fileUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.apply {
                requestMethod = "GET"
                connectTimeout = 10000
                readTimeout = 10000
            }
            
            try {
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d(TAG, "File successfully accessed (HTTP 200).")
                } else {
                    Log.w(TAG, "File access returned unexpected code: $responseCode")
                }
            } finally {
                connection.disconnect()
            }
            
            Log.d(TAG, "Background worker completed successfully.")
            Result.success()
            
        } catch (e: Exception) {
            Log.e(TAG, "Exception during background file access.", e)
            Result.retry()
        }
    }
}
