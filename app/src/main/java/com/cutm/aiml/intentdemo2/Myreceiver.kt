package com.cutm.aiml.intentdemo2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.widget.Toast

class Myreceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        if (action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            Toast.makeText(context, "Airplane mode activated", Toast.LENGTH_SHORT).show()
        }
    }
}
