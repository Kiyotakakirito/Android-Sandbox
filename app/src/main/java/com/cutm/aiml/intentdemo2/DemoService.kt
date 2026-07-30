package com.cutm.aiml.intentdemo2

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings
import android.widget.Toast

class DemoService : Service() {
    
    private lateinit var player: MediaPlayer

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Initialize MediaPlayer with the default device ringtone
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)
        
        // Make it loop continuously
        player.isLooping = true
        
        // Start playing
        player.start()
        
        Toast.makeText(this, "Music Service Started!", Toast.LENGTH_SHORT).show()
        
        // START_STICKY means the OS will try to restart the service if it kills it
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop the music when the service is destroyed
        if (this::player.isInitialized) {
            player.stop()
            player.release()
        }
        Toast.makeText(this, "Music Service Stopped!", Toast.LENGTH_SHORT).show()
    }
}
