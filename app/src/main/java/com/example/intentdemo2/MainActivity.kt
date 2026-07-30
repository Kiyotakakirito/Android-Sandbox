package com.example.intentdemo2

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var i_f: IntentFilter
    private val receiver = Myreceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
       
        val mainView = findViewById<android.view.View>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
        
        val unameField: EditText = findViewById(R.id.etusername)
        val pwdField: EditText = findViewById(R.id.pwd)
        val loginBtn: Button = findViewById(R.id.loginBtn)
        val clearBtn: Button = findViewById(R.id.clearBtn)
        
        clearBtn.setOnClickListener {
            unameField.text.clear()
            pwdField.text.clear()
            unameField.error = null
            pwdField.error = null
        }
        i_f = IntentFilter()
        i_f.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        i_f.addAction(Intent.ACTION_BATTERY_CHANGED)

        loginBtn.setOnClickListener {
            val uname = unameField.text.toString().trim()
            val pwd = pwdField.text.toString().trim()
            
            if (uname.isEmpty() || pwd.isEmpty()) {
                if (uname.isEmpty()) unameField.error = "Username is required"
                if (pwd.isEmpty()) pwdField.error = "Password is required"
            } else {
                val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                val savedPwd = sharedPref.getString(uname, null)
                
                if (savedPwd == null) {
                    // New user: Automatically register and login
                    sharedPref.edit().putString(uname, pwd).apply()
                    Toast.makeText(this, "User registered and logged in", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity2::class.java)
                    intent.putExtra("uname", uname)
                    startActivity(intent)
                } else if (savedPwd == pwd) {
                    // Existing user: Password matches
                    val intent = Intent(this, MainActivity2::class.java)
                    intent.putExtra("uname", uname)
                    startActivity(intent)
                } else {
                    // Existing user: Password mismatch
                    Toast.makeText(this, "Login Failed: Incorrect password", Toast.LENGTH_SHORT).show()
                    pwdField.error = "Incorrect password"
                }
            }    
        }
    }
    
    override fun onStart() {
        super.onStart()
        registerReceiver(receiver, i_f)
        Log.d("BroadcastReceiver", "Started Broadcast Receiver")
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}