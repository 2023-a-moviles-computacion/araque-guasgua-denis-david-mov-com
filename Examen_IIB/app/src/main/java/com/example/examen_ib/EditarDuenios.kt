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

class EditarDuenios : AppCompatActivity() {
    var duenioSeleccionada = Duenio("","","","",0.0,0)
    val db = Firebase.firestore
    val duenios = db.collection("Duenios")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("ciclo-vida", "onCreate")
        setContentView(R.layout.activity_editar_duenio)
    }

    override fun onStart() {
        Log.i("ciclo-vida", "onStart")
        super.onStart()

        duenioSeleccionada = intent.getParcelableExtra<Duenio>("PosDuenios")!!

        val editNombre = findViewById<TextInputEditText>(R.id.txtIn_nombreDuenio_editar)
        val editDireccion = findViewById<TextInputEditText>(R.id.txtIn_direccionDuenio_editar)
        val editGeneroDuenio = findViewById<TextInputEditText>(R.id.txtIn_generoDuenio_editar)
        val editSalarioDuenio = findViewById<TextInputEditText>(R.id.txtIn_salarioDuenio_editar)
        val editEdadDuenio = findViewById<TextInputEditText>(R.id.txtIn_edadDuenio_editar)

        editNombre.setText(duenioSeleccionada.nombreDuenio.toString())
        editDireccion.setText(duenioSeleccionada.direccionDuenio.toString())
        editGeneroDuenio.setText(duenioSeleccionada.generoDuenio.toString())
        editSalarioDuenio.setText(duenioSeleccionada.salarioDuenio.toString())
        editEdadDuenio.setText(duenioSeleccionada.edadDuenio.toString())

        val btnGuardarCambios = findViewById<Button>(R.id.btn_editarDuenio)
        btnGuardarCambios.setOnClickListener {
            duenios.document("${duenioSeleccionada.idDuenio}").update(
                "nombreDuenio", editNombre.text.toString(),
                "direccion", editDireccion.text.toString(),
                "generoDuenio", editGeneroDuenio.text.toString(),
                "salarioDuenio", editSalarioDuenio.text.toString().toDouble(),
                "edadDuenio", editEdadDuenio.text.toString().toInt()
            )

            Toast.makeText(this,"Duenio actualizada con exito", Toast.LENGTH_SHORT).show()

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