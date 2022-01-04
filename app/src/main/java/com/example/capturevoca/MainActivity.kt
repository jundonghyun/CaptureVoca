package com.example.capturevoca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.capturevoca.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var mbinding: ActivityMainBinding? = null
    private val binding get() = mbinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addword.setOnClickListener {
            startActivity(Intent(this, FileListActivity::class.java))
        }
    }
}