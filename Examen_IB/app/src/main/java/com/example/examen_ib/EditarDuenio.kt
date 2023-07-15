package com.example.examen_ib

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class EditarDuenio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("ciclo-vida", "onCreate")
        setContentView(R.layout.activity_editar_duenio)
    }

    override fun onStart() {
        Log.i("ciclo-vida", "onStart")
        super.onStart()

        val posicionDuenio = intent.getIntExtra("posicionEditar",1)

        var editNombre = findViewById<TextInputEditText>(R.id.txt_nombreDuenio_editar)
        var editFechaNacimiento = findViewById<TextInputEditText>(R.id.txt_fechaNacimiento_editar)
        var editEsActivo = findViewById<TextInputEditText>(R.id.txt_esActivo_editar)
        var editSalario = findViewById<TextInputEditText>(R.id.txt_salarioDuenio_editar)
        var editEdad = findViewById<TextInputEditText>(R.id.txt_edadDuenio_editar)

        DuenioBDD.TablaDuenio!!.listarDuenios().forEachIndexed{ indice: Int, duenio: Duenio ->
            if(indice == posicionDuenio){
                editNombre.setText(duenio.nombreDuenio.toString())
                editFechaNacimiento.setText(duenio.fechaNacimiento.toString())
                editEsActivo.setText(duenio.activo.toString())
                editSalario.setText(duenio.salarioDuenio.toString())
                editEdad.setText(duenio.edadDuenio.toString())
            }
        }

        val btnGuardarCambios = findViewById<Button>(R.id.btn_editarDuenio)
        btnGuardarCambios.setOnClickListener {
            var nombreDuenio = editNombre.text.toString()
            var fechaNacimiento = editFechaNacimiento.text.toString()
            var esActivo = editEsActivo.text.toString()
            var salarioDuenio = editSalario.text.toString().toDouble()
            var edadDuenio = editEdad.text.toString().toInt()

            DuenioBDD.TablaDuenio!!.actualizarDuenio(posicionDuenio, nombreDuenio,
                fechaNacimiento, esActivo, salarioDuenio.toString(), edadDuenio.toString())

            val intentEditSucces = Intent(this, HomeDuenios::class.java)
            startActivity(intentEditSucces)
        }

        val btnCancelarEditar = findViewById<Button>(R.id.btn_cancelar_editar)
        btnCancelarEditar.setOnClickListener{
            val intentEditCancel = Intent(this, HomeDuenios::class.java)
            startActivity(intentEditCancel)
        }

    }
}