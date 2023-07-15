package com.example.examen_ib

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
class EditarMascota : AppCompatActivity() {
    var duenioPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_mascota)
    }
    override fun onStart() {
        Log.i("ciclo-vida", "onStart")
        super.onStart()
        duenioPos = intent.getIntExtra("posicion-Dueño-Editar", 1)
        val editNombreMascota = findViewById<TextInputEditText>(R.id.txt_nombreMascota_editar)
        val editRazaMascota = findViewById<TextInputEditText>(R.id.txt_razaMascota_editar)
        val editSexoMascota = findViewById<TextInputEditText>(R.id.txt_sexoMascota_editar)
        val editPesoMascota = findViewById<TextInputEditText>(R.id.txt_pesoMascota_editar)
        val editEdadMascota = findViewById<TextInputEditText>(R.id.txt_edadMascota_editar)
        var idMascota = intent.getIntExtra("Mascota", 1)

        DuenioBDD.TablaDuenio!!.listarMascotas().forEachIndexed { indice: Int, mascota: Mascota ->
            if (mascota.idMascota == idMascota) {
                // Llenar los campos
                editNombreMascota.setText(mascota.nombreMascota)
                editRazaMascota.setText(mascota.razaMascota)
                editSexoMascota.setText(mascota.sexoMascota)
                editPesoMascota.setText(mascota.pesoMascota.toString())
                editEdadMascota.setText(mascota.edadMascota.toString())
            }
        }
        val btnEditMascota = findViewById<Button>(R.id.btn_editarMascota)
        btnEditMascota.setOnClickListener {
            var nombreMascota = editNombreMascota.text.toString()
            var razaMascota = editRazaMascota.text.toString()
            var sexoMascota = editSexoMascota.text.toString()
            var pesoMascota = editPesoMascota.text.toString().toDouble()
            var edadMascota = editEdadMascota.text.toString().toInt()

            DuenioBDD.TablaDuenio!!.actualizarMascota(
                idMascota, nombreMascota, razaMascota, sexoMascota,
                pesoMascota.toString(), edadMascota.toString()
            )
            answer()
        }
        val btnCancelMascota = findViewById<Button>(R.id.btn_cancelar_editar_mascota)
        btnCancelMascota.setOnClickListener {
            answer()
        }

    }

    fun answer(){
        val intentReturnParameters = Intent()
        intentReturnParameters.putExtra("posicion-Dueño-Editar",duenioPos)
        setResult(
            RESULT_OK,
            intentReturnParameters
        )
        finish()
    }
}