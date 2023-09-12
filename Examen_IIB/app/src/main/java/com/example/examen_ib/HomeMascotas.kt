package com.example.examen_ib

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeMascotas : AppCompatActivity() {
    var duenioSeleccionada = Duenio("","","","",0.0,0)
    val db = Firebase.firestore
    val duenios = db.collection("Duenios")
    var idSelectItem = 0
    var adaptador: ArrayAdapter<Mascota>?=null
    var mascotaSeleccionado: Mascota? = Mascota("","","","","",0.0,0)


    var resultAddMascota = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK){
            if(result.data != null){
                val data = result.data
                duenioSeleccionada = intent.getParcelableExtra<Duenio>("posicionDuenio")!!
            }
        }
    }

    var resultEditMascota = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK){
            if(result.data != null){
                val data = result.data
                duenioSeleccionada = intent.getParcelableExtra<Duenio>("posicionDuenio")!!
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_mascotas)
    }


    override fun onStart() {
        super.onStart()
        Log.i("ciclo-vida", "onStart")

        duenioSeleccionada = intent.getParcelableExtra<Duenio>("posicionDuenio")!!
        listViewMascotas()

        val txtNombreDuenio = findViewById<TextView>(R.id.tv_nombrePanaderia)
        txtNombreDuenio.setText("Dueño: "+duenioSeleccionada.nombreDuenio)


        val btnNewMascota = findViewById<Button>(R.id.btn_crear_new_mascota)
        btnNewMascota.setOnClickListener {
            abrirActividadAddMascota(CrearMascota::class.java)
            listViewMascotas()
        }

        val btnBack = findViewById<Button>(R.id.btn_volver_mascota)
        btnBack.setOnClickListener {
            val intentBackMascota = Intent(this, HomeDuenios::class.java)
            startActivity(intentBackMascota)
        }

    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_mascotas, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        idSelectItem = info.position
        Log.i("context-menu", "ID Mascota ${idSelectItem}")
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        mascotaSeleccionado = adaptador!!.getItem(idSelectItem)
        return when (item.itemId) {
            R.id.mi_editarMascota -> {
                Log.i("context-menu", "Edit position: ${idSelectItem}")
                abrirActividadEditMascota(EditarMascota::class.java)
                return true
            }
            R.id.mi_eliminarMascota -> {
                Log.i("context-menu", "Delete position: ${idSelectItem}")
                val duenioSubColeccion = duenios.document("${duenioSeleccionada.idDuenio}")
                    .collection("Mascotas")
                    .document("${mascotaSeleccionado!!.idMascota}")
                    .delete()
                    .addOnSuccessListener {
                        Log.i("Eliminar-Mascota","Success")
                    }
                    .addOnFailureListener{
                        Log.i("Eliminar-Mascota","Failed")
                    }
                listViewMascotas()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun listViewMascotas(){
        val panaderiaSubColeccion = duenios.document("${duenioSeleccionada.idDuenio}")
            .collection("Mascotas")
            .whereEqualTo("idDuenio_Mascota","${duenioSeleccionada.idDuenio}")

        val lv_mascotas = findViewById<ListView>(R.id.lv_mascotas_lista)
        panaderiaSubColeccion.get().addOnSuccessListener { result->
            var listaMascotas = arrayListOf<Mascota>()
            for(document in result){
                val pesoMascotaString = document.data.get("pesoMascota")?.toString()
                val pesoMascota = pesoMascotaString?.toDoubleOrNull()

                if (pesoMascota != null) {
                    listaMascotas.add(
                        Mascota(
                            document.id.toString(),
                            document.data.get("idDuenio_Mascota").toString(),
                            document.data.get("nombreMascota").toString(),
                            document.data.get("generoMascota").toString(),
                            document.data.get("esCachorro").toString(),
                            pesoMascota,
                            document.data.get("edadMascota").toString().toInt()
                        )
                    )
                } else {
                    // Manejar el caso donde el valor de 'pesoMascota' es nulo o no válido
                    // Puedes imprimir un mensaje de log o tomar alguna otra acción
                    Log.e("Error", "No se pudo convertir el peso de la mascota a Double")
                }
            }
            adaptador = ArrayAdapter(
                this,
                android.R.layout.simple_expandable_list_item_1,
                listaMascotas
            )
            lv_mascotas.adapter = adaptador
            adaptador!!.notifyDataSetChanged()

            registerForContextMenu(lv_mascotas)
        }
    }

    fun abrirActividadAddMascota(
        clase: Class<*>
    ) {
        val intentAddNewMascota = Intent(this, clase)
        intentAddNewMascota.putExtra("posicionDuenio",duenioSeleccionada)
        resultAddMascota.launch(intentAddNewMascota)
    }

    fun abrirActividadEditMascota(
        clase: Class<*>
    ) {
        val intentEditPan = Intent(this, clase)
        intentEditPan.putExtra("mascota", mascotaSeleccionado)
        intentEditPan.putExtra("posicionDuenioEditar", duenioSeleccionada)
        resultEditMascota.launch(intentEditPan)
    }
}