package com.example.examen_ib
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class HomeDuenios :  AppCompatActivity() {
    var idSelectItem = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_duenio)
        Log.i("ciclo-vida", "onCreate")

    }
    override fun onStart() {
        super.onStart()
        Log.i("ciclo-vida", "onStart")

        val listViewDuenio = findViewById<ListView>(R.id.lv_duenios_lista)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            DuenioBDD.TablaDuenio!!.listarDuenios()
        )
        listViewDuenio.adapter = adaptador
        adaptador.notifyDataSetChanged()

        this.registerForContextMenu(listViewDuenio)

        val btnAnadirDuenio = findViewById<Button>(R.id.btn_crear_new_duenio)
        btnAnadirDuenio.setOnClickListener {
            val intentAddDuenio = Intent(this, CrearDuenio::class.java)
            startActivity(intentAddDuenio)
        }

    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            // Guardar las variables
            // primitivos
            putInt("idItemSeleccionado",idSelectItem)
            putParcelableArrayList("arregloDueños",DuenioBDD.TablaDuenio!!.listarDuenios())
            putParcelableArrayList("arregloPP",Registers.arregloDueniosMascotas)

        }
        super.onSaveInstanceState(outState)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        idSelectItem = savedInstanceState.getInt("idItemSeleccionado")

        Registers.arregloDueniosMascotas = savedInstanceState.getParcelableArrayList<DueniosMascotas>("arregloPP")!!
        if (idSelectItem == null){
            idSelectItem = 0
        }
        val listViewDuenio = findViewById<ListView>(R.id.lv_duenios_lista)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            DuenioBDD.TablaDuenio!!.listarDuenios()
        )
        listViewDuenio.adapter = adaptador
        adaptador.notifyDataSetChanged()
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
        return when (item.itemId) {
            R.id.mi_editar -> {
                Log.i("context-menu", "Edit position: ${idSelectItem}")
                abrirActividadConParametros(EditarDuenio::class.java)
                return true
            }
            R.id.mi_eliminar -> {
                Log.i("context-menu", "Delete position: ${idSelectItem}")
                DuenioBDD.TablaDuenio!!.eliminarDuenio(idSelectItem)
                val listViewDuenio = findViewById<ListView>(R.id.lv_duenios_lista)
                val adaptador = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    DuenioBDD.TablaDuenio!!.listarDuenios()
                )
                listViewDuenio.adapter = adaptador
                adaptador.notifyDataSetChanged()
                return true
            }
            R.id.mi_mascotas -> {
                Log.i("context-menu", "Mascotas: ${idSelectItem}")
                abrirActividadConParametros(HomeMascotas::class.java)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    fun abrirActividadConParametros(
        clase: Class<*>
    ) {
        val intentEditarDuenio = Intent(this, clase)
        intentEditarDuenio.putExtra("posicionEditar", idSelectItem)
        startActivity(intentEditarDuenio)
    }
}