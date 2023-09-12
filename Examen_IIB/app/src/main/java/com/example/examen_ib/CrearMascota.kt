package com.example.examen_ib

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearMascota : AppCompatActivity() {
    var duenioSeleccionado = Duenio("","","","",0.0,0)
    val db = Firebase.firestore
    val duenios = db.collection("Duenios")


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("ciclo-vida","onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_mascota)
    }

    override fun onStart() {
        super.onStart()
        Log.i("ciclo-vida","onStart")


        duenioSeleccionado = intent.getParcelableExtra<Duenio>("posicionDuenio")!!
        val panaderiaSubColeccion = duenios.document("${duenioSeleccionado.idDuenio}")
            .collection("Mascotas")


        var txtInNombre = findViewById<TextInputEditText>(R.id.txtIn_nombreMascota_crear)
        var txtInGenero = findViewById<TextInputEditText>(R.id.txtIn_generoMascota_crear)
        var txtInEsCachorro = findViewById<TextInputEditText>(R.id.txtIn_esCachorro_crear)
        var txtInPeso = findViewById<TextInputEditText>(R.id.txtIn_pesoMascota_crear)
        var txtInEdad = findViewById<TextInputEditText>(R.id.txtIn_edadMascita_crear)

        Log.i("posMascota", "${duenioSeleccionado.idDuenio}")

        var btnAddMascota = findViewById<Button>(R.id.btn_crearMascota)
        btnAddMascota.setOnClickListener {
            var mascota = hashMapOf(
                "nombreMascota" to txtInNombre.text.toString(),
                "genero" to txtInGenero.text.toString(),
                "esCachorro" to txtInEsCachorro.text.toString(),
                "peso" to txtInPeso.text.toString(),
                "edad" to txtInEdad.text.toString(),
                "idDuenio_Mascota" to duenioSeleccionado.idDuenio,
            )
            panaderiaSubColeccion.add(mascota).addOnSuccessListener {
                Toast.makeText(this, "Mascota registrado con exito", Toast.LENGTH_SHORT).show();
                Log.i("Crear-Mascota", "Success")
            }.addOnFailureListener {
                Log.i("Crear-Mascota", "Failed")
            }

            answer()

        }


        var btnCancelarCrearMascota = findViewById<Button>(R.id.btn_cancelar_crear_mascota)
        btnCancelarCrearMascota.setOnClickListener {
            answer()
        }


    }

    fun answer(){
        val intentReturnParameters = Intent()
        intentReturnParameters.putExtra("posicionDuenio",duenioSeleccionado)
        setResult(
            RESULT_OK,
            intentReturnParameters
        )
        finish()
    }


}