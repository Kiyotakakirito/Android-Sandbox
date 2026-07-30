package com.example.intentdemo2

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.BatteryManager
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.intentdemo2.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {

    private lateinit var binding: ActivityMain3Binding

    private val dashboardReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Intent.ACTION_BATTERY_CHANGED -> {
                    val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                    val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                    if (level != -1 && scale != -1) {
                        val batteryPct = level * 100 / scale
                        binding.tvBattery.text = "Battery: $batteryPct%"
                    }
                }
                Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                    val isAirplaneModeOn = intent.getBooleanExtra("state", false)
                    binding.tvAirplaneMode.text = "Airplane Mode: ${if (isAirplaneModeOn) "ON" else "OFF"}"
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize initial state for airplane mode
        val isAirplaneModeOn = Settings.Global.getInt(
            contentResolver,
            Settings.Global.AIRPLANE_MODE_ON, 0
        ) != 0
        binding.tvAirplaneMode.text = "Airplane Mode: ${if (isAirplaneModeOn) "ON" else "OFF"}"

        binding.btnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        binding.btnPhoneBook.setOnClickListener {
            try {
                // Setting the type specifically to Phone CONTENT_TYPE makes it much more reliable on physical devices
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Contacts app not found", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCamera.setOnClickListener {
            openCamera()
        }

        binding.btnDial.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:1234567890"))
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Dialer app not found", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnStartService.setOnClickListener {
            startService(Intent(this, DemoService::class.java))
            Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show()
        }

        binding.btnStopService.setOnClickListener {
            stopService(Intent(this, DemoService::class.java))
            Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openCamera() {
        try {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Camera app not found", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onStart() {
        super.onStart()
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_CHANGED)
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        }
        registerReceiver(dashboardReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(dashboardReceiver)
    }
}