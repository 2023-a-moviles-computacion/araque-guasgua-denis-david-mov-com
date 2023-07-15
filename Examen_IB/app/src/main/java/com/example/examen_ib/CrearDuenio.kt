package com.example.examen_ib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText

class CrearDuenio : AppCompatActivity() {

    var id1 = 0
    var id2 = 0

    var nombreDuenio = ""
    var fechaNacimiento = ""
    var activo = ""
    var salario = ""
    var edad = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_duenio)
    }

    override fun onStart() {
        super.onStart()
        Log.i("cicloVida", "onStart")
        var longListDuenio = DuenioBDD.TablaDuenio!!.listarDuenios().lastIndex
        DuenioBDD.TablaDuenio!!.listarDuenios().forEachIndexed { indice: Int, duenio: Duenio ->
            Log.i("testExamen","${duenio.idDuenio} -> ${duenio.nombreDuenio}")
            if( indice == longListDuenio){
                id2 = duenio.idDuenio
            }
        }

        id1 = id2+1

        var txtNombre = findViewById<TextInputEditText>(R.id.txt_nombreDuenio)
        var txtFechaNacimiento = findViewById<TextInputEditText>(R.id.txt_fechaNacimiento)
        var txtActivo = findViewById<TextInputEditText>(R.id.txt_esActivoDuenio)
        var txtSalario = findViewById<TextInputEditText>(R.id.txt_salarioDuenio)
        var txtEdad = findViewById<TextInputEditText>(R.id.txt_edadDuenio)

        var btnCrearDuenio = findViewById<Button>(R.id.btn_crearDuenio)

        btnCrearDuenio.setOnClickListener {
            nombreDuenio = txtNombre.text.toString()
            fechaNacimiento = txtFechaNacimiento.text.toString()
            activo = txtActivo.text.toString()
            salario = txtSalario.text.toString()
            edad = txtEdad.text.toString()

            DuenioBDD.TablaDuenio!!.crearDuenio(id1,nombreDuenio,fechaNacimiento,
                activo, salario, edad)

            val intentAddSucces = Intent(this, HomeDuenios::class.java)
            startActivity(intentAddSucces)

        }

        var btnCancelarCrearDuenio = findViewById<Button>(R.id.btn_cancelar_crear)
        btnCancelarCrearDuenio.setOnClickListener {
            val intentAddCancel = Intent(this, HomeDuenios::class.java)
            startActivity(intentAddCancel)
        }

    }
}