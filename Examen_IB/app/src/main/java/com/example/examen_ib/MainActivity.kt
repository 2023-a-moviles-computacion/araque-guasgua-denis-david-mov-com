package com.example.examen_ib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DuenioBDD.TablaDuenio = ESqliteHelperDuenioMascota (this)
        Registers.arregloDueniosMascotas
        val btnStart = findViewById<Button>(R.id.btn_examStart)
        btnStart.setOnClickListener{
            val intent = Intent(this, HomeDuenios::class.java)
            startActivity(intent)
        }

    }
}