package com.example.examen_ib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearDuenio : AppCompatActivity() {

    val db = Firebase.firestore
    val duenios = db.collection("Duenios")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_duenio)
    }

    override fun onStart() {
        super.onStart()

        var txtInNombre = findViewById<TextInputEditText>(R.id.txtIn_nombreDuenio_crear)
        var txtInDireccion = findViewById<TextInputEditText>(R.id.txtIn_direccionDuenio_crear)
        var txtInGeneroDuenio = findViewById<TextInputEditText>(R.id.txtIn_generoDuenio_crear)
        var txtInSalario = findViewById<TextInputEditText>(R.id.txtIn_salarioDuenio_crear)
        var txtInEdad = findViewById<TextInputEditText>(R.id.txtIn_edadDuenio_crear)

        var btnCrearPanaderia = findViewById<Button>(R.id.btn_crearPanaderia)

        btnCrearPanaderia.setOnClickListener {
            var panaderia = hashMapOf(
                "nombreDuenio" to txtInNombre.text.toString(),
                "direccionDuenio" to txtInDireccion.text.toString(),
                "generoDuenio" to txtInGeneroDuenio.text.toString(),
                "salarioDuenio" to txtInSalario.text.toString(),
                "edadDuenio" to txtInEdad.text.toString()
            )

            duenios.add(panaderia).addOnSuccessListener {
                txtInNombre.text!!.clear()
                txtInDireccion.text!!.clear()
                txtInGeneroDuenio.text!!.clear()
                txtInSalario.text!!.clear()
                txtInEdad.text!!.clear()

                Toast.makeText(this,"Duenio registrado con exito", Toast.LENGTH_SHORT).show();
                Log.i("Crear-Duenio","Success")
            }.addOnSuccessListener {
                Log.i("Crear-Duenio","Failed")
            }


            val intentAddSucces = Intent(this, HomeDuenios::class.java)
            startActivity(intentAddSucces)

        }

        var btnCancelarCrearPanaderia = findViewById<Button>(R.id.btn_cancelar_crear)
        btnCancelarCrearPanaderia.setOnClickListener {
            val intentAddCancel = Intent(this, HomeDuenios::class.java)
            startActivity(intentAddCancel)
        }

    }
}