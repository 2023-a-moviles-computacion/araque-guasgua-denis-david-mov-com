package com.example.examen_ib

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class CrearMascota : AppCompatActivity() {
    var nextIdMascota = 0
    var lastIdMascota = 0

    var nextIdDM = 0
    var lastIdDM = 0
    var idSelectedMascota = 0
    var duenioPos = 0
    var idSelectedDuenio = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("ciclo-vida","onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_mascota)
    }

    override fun onStart() {
        super.onStart()

        Log.i("ciclo-vida","onStart")
        duenioPos = intent.getIntExtra("posicion Dueño",-1)
        Log.i("posDueño","${duenioPos}")

        DuenioBDD.TablaDuenio!!.listarDuenios().forEachIndexed { indice: Int, duenio: Duenio ->
            if(indice==duenioPos){
                idSelectedDuenio = duenio.idDuenio
            }
        }

        var longListMascota = DuenioBDD.TablaDuenio!!.listarMascotas().lastIndex

        DuenioBDD.TablaDuenio!!.listarMascotas().forEachIndexed { indice: Int, mascota: Mascota ->
            Log.i("testExamen","${mascota.idMascota} -> ${mascota.nombreMascota}")
            if(indice == longListMascota){
                lastIdMascota = mascota.idMascota
            }
        }
        nextIdMascota = lastIdMascota+1

        var longPP = Registers.arregloDueniosMascotas.lastIndex
        Registers.arregloDueniosMascotas.forEachIndexed { indice: Int, dueniosMascotas: DueniosMascotas ->
            if(indice==longPP)
                lastIdDM = dueniosMascotas.idDuenioMascota
        }
        nextIdDM = lastIdDM+1


        // ------------ o ------------

        var txtNombre = findViewById<TextInputEditText>(R.id.txt_nombreMascota)
        var txtRaza = findViewById<TextInputEditText>(R.id.txt_raza)
        var txtSexo = findViewById<TextInputEditText>(R.id.txt_sexoMascota)
        var txtPeso = findViewById<TextInputEditText>(R.id.txt_pesoMascota)
        var txtEdad = findViewById<TextInputEditText>(R.id.txt_edadMascota)

        var btnAddMascota = findViewById<Button>(R.id.btn_crearMascota)
        btnAddMascota.setOnClickListener {
            var nombreMascota = txtNombre.text.toString()
            var razaMascota = txtRaza.text.toString()
            var sexoMascota = txtSexo.text.toString()
            val pesoMascota = txtPeso.text.toString()
            var edadMascota = txtEdad.text.toString()

            Registers.arregloDueniosMascotas.add(DueniosMascotas(nextIdDM, idSelectedDuenio, nextIdMascota))

            DuenioBDD.TablaDuenio!!.crearMascota(nextIdMascota, nombreMascota,razaMascota,sexoMascota,pesoMascota,edadMascota)

            answer()
        }

        // ------------ o ------------

        var btnCancelarCrearMascota = findViewById<Button>(R.id.btn_cancelar_crear_mascota)
        btnCancelarCrearMascota.setOnClickListener {
            answer()
        }


    }

    fun answer(){
        val intentReturnParameters = Intent()
        intentReturnParameters.putExtra("posicion Dueño",duenioPos)
        setResult(
            RESULT_OK,
            intentReturnParameters
        )
        finish()
    }


}