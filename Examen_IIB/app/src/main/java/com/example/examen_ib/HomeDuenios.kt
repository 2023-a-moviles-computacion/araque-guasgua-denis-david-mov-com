package com.example.examen_ib


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomeDuenios :  AppCompatActivity() {
    val db = Firebase.firestore
    val duenios = db.collection("Duenios")
    var idSelectItem = 0
    var adaptador: ArrayAdapter<Duenio>?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_duenios)
        Log.i("ciclo-vida", "onCreate")

    }

    override fun onStart() {
        super.onStart()
        Log.i("ciclo-vida", "onStart")

        listarDuenios()

        val btnAnadirDuenio = findViewById<Button>(R.id.btn_crear_new_duenio)
        btnAnadirDuenio.setOnClickListener {
            val intentAddDuenio = Intent(this, CrearDuenio::class.java)
            startActivity(intentAddDuenio)
        }

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idSelectItem = id
        Log.i("context-menu", "ID ${id}")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var duenioSeleccionada:Duenio = adaptador!!.getItem(idSelectItem)!!

        return when (item.itemId) {
            R.id.mi_editar -> {
                Log.i("context-menu", "Edit position: ${duenioSeleccionada.idDuenio}")
                val abrirEditarDuenios = Intent(this,EditarDuenios::class.java)
                abrirEditarDuenios.putExtra("PosDuenio",duenioSeleccionada)
                startActivity(abrirEditarDuenios)
                return true
            }
            R.id.mi_eliminar -> {
                Log.i("context-menu", "Delete position: ${idSelectItem}")
                duenios.document("${duenioSeleccionada.idDuenio}").delete()
                    .addOnSuccessListener {
                        Log.i("Eliminar-Dueño","Success")
                    }
                    .addOnFailureListener {
                        Log.i("Eliminar-Dueño","Failed")
                    }
                listarDuenios()
                return true
            }
            R.id.mi_mascotas -> {
                Log.i("context-menu", "Panes: ${idSelectItem}")
                val abrirHomeMascotas = Intent(this, HomeMascotas::class.java)
                abrirHomeMascotas.putExtra("posicionDuenio",duenioSeleccionada)
                startActivity(abrirHomeMascotas)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }


    fun listarDuenios(){
        val lv_panaderias = findViewById<ListView>(R.id.lv_duenios_lista)
        duenios.get().addOnSuccessListener { result->
            var listDuenios = arrayListOf<Duenio>()
            for(document in result){
                listDuenios.add(
                    Duenio(
                        document.id.toString(),
                        document.get("nombreDuenio").toString(),
                        document.get("direccion").toString(),
                        document.get("generoDuenio").toString(),
                        document.get("salarioDuenio").toString().toDouble(),
                        document.get("edadDuenio").toString().toInt(),
                    )
                )
            }
            adaptador = ArrayAdapter(
                this,
                android.R.layout.simple_expandable_list_item_1,
                listDuenios
            )
            lv_panaderias.adapter = adaptador
            adaptador!!.notifyDataSetChanged()
            registerForContextMenu(lv_panaderias)
        }.addOnFailureListener {
            Log.i("Error", "Creacion de lista de duenios fallida")
        }
    }
}