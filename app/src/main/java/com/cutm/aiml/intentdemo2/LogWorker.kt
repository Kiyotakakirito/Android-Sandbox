package com.cutm.aiml.intentdemo2

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class LogWorker(context: Context, workerParams: WorkerParameters) : 
    Worker(context, workerParams) {

    override fun doWork(): Result {
        return try {
            // Retrieve the custom message passed from the Activity
            val inpMessage = inputData.getString("LogWorkerMessage") ?: "Default Log Message"
            
            Log.d("LogWorker", "--------------------------------------")
            Log.d("LogWorker", "⚙️ Background Worker Started!")
            Log.d("LogWorker", "Message received: $inpMessage")
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
