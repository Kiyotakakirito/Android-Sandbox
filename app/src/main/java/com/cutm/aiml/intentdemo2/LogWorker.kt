package com.cutm.aiml.intentdemo2

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.net.HttpURLConnection
import java.net.URL

class LogWorker(context: Context, workerParams: WorkerParameters) : 
    Worker(context, workerParams) {

    override fun doWork(): Result {
        // Get the Google File URL passed from the Activity
        val fileUrl = inputData.getString("FileUrl") ?: return Result.failure()

        return try {
            Log.d("LogWorker", "--------------------------------------")
            Log.d("LogWorker", "⚙️ Background Worker Started!")
            Log.d("LogWorker", "Attempting to open file at: $fileUrl")
            
            // Open a background network connection to the Google File
            val url = URL(fileUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            
            // This actually reaches out to the internet to "open/read" the page
            val responseCode = connection.responseCode
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Log.d("LogWorker", "✅ File is opened!")
            } else {
                Log.d("LogWorker", "⚠️ File accessed but returned code: $responseCode")
            }
            
            connection.disconnect()
            
            Log.d("LogWorker", "Logger stopped")
            Log.d("LogWorker", "--------------------------------------")
            
            // Tell Android the job was a success
            Result.success()
            
        } catch (e: Exception) {
            Log.e("LogWorker", " Error in Background Worker", e)
            
            // Tell Android the job failed, but it should try again later
            Result.retry()
        }
    }
}
