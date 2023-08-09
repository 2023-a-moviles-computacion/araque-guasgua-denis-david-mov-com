package com.example.tiktok
import android.content.Intent
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Agregar un Handler que ejecutará el cambio de actividad después de 3 segundos (3000 ms)
        Handler().postDelayed({
            // Iniciar la ActivityB
            val intent = Intent(this, homeTikTok::class.java)
            startActivity(intent)

            // Finalizar esta Activity si deseas que al volver a ActivityA desde ActivityB se cierre la app
            finish()
        }, 3000) // 3000 ms = 3 segundos

    }
}