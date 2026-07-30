package com.example.intentdemo2

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class LogWorker(context: Context, workerParams: WorkerParameters) : 
    Worker(context, workerParams) {

    override fun doWork(): Result {
        return try {
            // This runs completely in the background!
            Log.d("LogWorker", "--------------------------------------")
            Log.d("LogWorker", "⚙️ Background Worker Started!")
            
            // Simulating a long-running background task
            for (i in 1..5) {
                Log.d("LogWorker", "Working... Step $i of 5")
                Thread.sleep(1000) // Sleep for 1 second to simulate work
            }
            
            Log.d("LogWorker", "✅ Background Worker Finished Successfully!")
            Log.d("LogWorker", "--------------------------------------")
            
            // Tell Android the job was a success
            Result.success()
            
        } catch (e: Exception) {
            Log.e("LogWorker", "❌ Error in Background Worker", e)
            
            // Tell Android the job failed, but it should try again later
            Result.retry()
        }
    }
}
