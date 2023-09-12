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

class EditarMascota : AppCompatActivity() {
    var duenioSeleccionada = Duenio("","","","",0.0,0)
    var mascotaSeleccionado = Mascota("","","","","",0.0,0)
    val db = Firebase.firestore
    val duenios = db.collection("Duenios")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_mascota)
    }

    override fun onStart() {
        Log.i("ciclo-vida", "onStart")
        super.onStart()

        duenioSeleccionada = intent.getParcelableExtra<Duenio>("posicionDuenioEditar")!!
        mascotaSeleccionado = intent.getParcelableExtra<Mascota>("Mascota")!!

        // ------------------ o ------------------

        val editNombreMascota = findViewById<TextInputEditText>(R.id.txtIn_nombreMascota_editar)
        val editGeneroMascota = findViewById<TextInputEditText>(R.id.txtIn_generoMascota_editar)
        val editesCachorro = findViewById<TextInputEditText>(R.id.txtIn_esCachorro_editar)
        val editPesoMascota = findViewById<TextInputEditText>(R.id.txtIn_pesoMascota_editar)
        val editEdadMascota = findViewById<TextInputEditText>(R.id.txtIn_edadMascota_editar)

        editNombreMascota.setText(mascotaSeleccionado.nombreMascota.toString())
        editGeneroMascota.setText(mascotaSeleccionado.generoMascota.toString())
        editesCachorro.setText(mascotaSeleccionado.esCachorro.toString())
        editPesoMascota.setText(mascotaSeleccionado.pesoMascota.toString())
        editEdadMascota.setText(mascotaSeleccionado.edadMascota.toString())
        // ------------------ o ------------------

        val btnEditMascota = findViewById<Button>(R.id.btn_editarMascota)
        btnEditMascota.setOnClickListener {
            duenios.document("${duenioSeleccionada.idDuenio}")
                .collection("Mascotas")
                .document("${mascotaSeleccionado.idMascota}")
                .update(
                    "nombre", editNombreMascota.text.toString(),
                    "genero", editGeneroMascota.text.toString(),
                    "esCachorro", editesCachorro.text.toString(),
                    "pesoMascota", editPesoMascota.text.toString().toDouble(),
                    "edadMascota", editEdadMascota.text.toString().toInt()
                )
                .addOnSuccessListener {
                    Toast.makeText(this, "Mascota actualizada con éxito", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "${duenioSeleccionada.idDuenio}", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "${mascotaSeleccionado.idMascota}", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "Error al actualizar el mascota: ${e.message}", Toast.LENGTH_SHORT).show()
                }

            answer()
        }


        val btnCancelMascota = findViewById<Button>(R.id.btn_cancelar_editar_mascota)
        btnCancelMascota.setOnClickListener {
            answer()
        }


    }

    fun answer(){
        val intentReturnParameters = Intent()
        intentReturnParameters.putExtra("posicionDuenioEditar",duenioSeleccionada)
        setResult(
            RESULT_OK,
            intentReturnParameters
        )
        finish()
    }
}
