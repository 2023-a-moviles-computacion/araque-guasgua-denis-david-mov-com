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

class HomeMascotas : AppCompatActivity() {

    var idSelectItem = 0
    var duenioPos = 0
    var itemSelect = 0
    var idDuenio = 0

    var resultAddMascota = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK){
            if(result.data != null){
                val data = result.data
                duenioPos = data?.getIntExtra("posicionDueño",0)!!
                actualizarListaMascotas()
            }
        }
    }
    var resultEditMascota = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK){
            if(result.data != null){
                val data = result.data
                duenioPos = data?.getIntExtra("posicionDueño",0)!!
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_mascotas)
    }


    fun listViewMascotas():ArrayList<Mascota>{
        var listaIDMascotas = arrayListOf<Int>()

        Registers.arregloDueniosMascotas.forEachIndexed { indice: Int, pp: DueniosMascotas ->
            if(pp.idDuenio == idDuenio){
                listaIDMascotas.add(pp.idMascota)
            }
        }
        var mascotaList = arrayListOf<Mascota>()
        DuenioBDD.TablaDuenio!!.listarMascotas().forEachIndexed { index : Int, mascota: Mascota ->
            for(i in listaIDMascotas){
                if(i==mascota.idMascota){
                    mascotaList.add(mascota)
                }
            }
        }

        return mascotaList
    }

    private fun actualizarListaMascotas() {
        val listViewMascota = findViewById<ListView>(R.id.lv_mascotas_lista)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listViewMascotas()
        )
        listViewMascota.adapter = adaptador
        adaptador.notifyDataSetChanged()
    }


    override fun onStart() {
        super.onStart()
        Log.i("ciclo-vida", "onStart")

        duenioPos = intent.getIntExtra("posicionEditar",1)
        DuenioBDD.TablaDuenio!!.listarDuenios().forEachIndexed { indice: Int, duenio: Duenio ->
            if(indice==duenioPos){
                val txtDuenioName = findViewById<TextView>(R.id.tv_nombreDuenio)
                txtDuenioName.setText("Dueño: "+duenio.nombreDuenio)
                idDuenio = duenio.idDuenio
            }
        }

        val listViewMascota = findViewById<ListView>(R.id.lv_mascotas_lista)

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listViewMascotas()
        )

        listViewMascota.adapter = adaptador
        adaptador.notifyDataSetChanged()

        this.registerForContextMenu(listViewMascota)

        val btnNewMascota = findViewById<Button>(R.id.btn_crear_new_mascota)
        btnNewMascota.setOnClickListener {
            abrirActividadAddMascota(CrearMascota::class.java)
        }

        val btnBack = findViewById<Button>(R.id.btn_volver_mascota)
        btnBack.setOnClickListener {
            val intentBackMascota = Intent(this, HomeDuenios::class.java)
            startActivity(intentBackMascota)
        }


    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val mascotaListView = findViewById<ListView>(R.id.lv_mascotas_lista)

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listViewMascotas()
        )
        mascotaListView.adapter = adaptador
        adaptador.notifyDataSetChanged()
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
        val id = info.position
        itemSelect = id
        val idR=listViewMascotas()[id].idMascota
        idSelectItem = idR
        Log.i("context-menu", "ID Mascota ${id}")
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editarMascota -> {
                Log.i("context-menu", "Edit position: ${idSelectItem}")
                abrirActividadEditMascota(EditarMascota::class.java)
                return true
            }
            R.id.mi_eliminarMascota -> {
                Log.i("context-menu", "Delete position: ${idSelectItem}")
                DuenioBDD.TablaDuenio!!.eliminarMascota(idSelectItem)
                actualizarListaMascotas()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirActividadAddMascota(
        clase: Class<*>
    ) {
        val intentAddNewMascota = Intent(this, clase)
        intentAddNewMascota.putExtra("posicionDueño",duenioPos)
        Log.i("positionSend","${duenioPos}")
        resultAddMascota.launch(intentAddNewMascota)
    }

    fun abrirActividadEditMascota(
        clase: Class<*>
    ) {
        val intentEditMascota = Intent(this, clase)
        intentEditMascota.putExtra("Mascota", idSelectItem)
        intentEditMascota.putExtra("posicionDueñoEditar", duenioPos)
        resultEditMascota.launch(intentEditMascota)
    }
}