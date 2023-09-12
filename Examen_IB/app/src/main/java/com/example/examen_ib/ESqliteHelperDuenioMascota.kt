package com.example.examen_ib

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ESqliteHelperDuenioMascota(
    contexto: Context?,
): SQLiteOpenHelper(contexto, "moviles", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaDuenio: ArrayList<String> = arrayListOf(
            """
               CREATE TABLE MASCOTA(
               id INTEGER PRIMARY KEY,
               nombre VARCHAR(50),
               raza VARCHAR(50),
               sexo VARCHAR(50),
               peso VARCHAR(50),
               edad VARCHAR(50)
               );
             """, """
               CREATE TABLE DUENIO(
               id INTEGER PRIMARY KEY,
               nombre VARCHAR(50),
               fecha VARCHAR(100),
               activo VARCHAR(50),
               salario VARCHAR(50),
               edad VARCHAR(50)
               );
            """
        )
        for (i in scriptSQLCrearTablaDuenio) {
            db!!.execSQL(i)
        }
        Log.i("creart", "Dueños")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    // ------------------- DUEÑO -------------------
    fun crearDuenio(id:Int, nombre:String, fecha:String, activo:String, salario:String, edad:String ): Boolean{
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("id",id)
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("fecha", fecha)
        valoresAGuardar.put("activo", activo)
        valoresAGuardar.put("salario", salario)
        valoresAGuardar.put("edad", edad)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "DUENIO",
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return if(resultadoGuardar.toInt() == -1) false else true
    }

    fun listarDuenios(): ArrayList<Duenio>{
        var lista = arrayListOf<Duenio>()
        var duenio: Duenio?
        val baseDatosLectura = readableDatabase
        val scriptConsultarUsuario = "SELECT * FROM DUENIO"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario,
            null
        )
        if(resultadoConsultaLectura.moveToFirst()){
            do {
                duenio=Duenio(0,"","","",0.0,0)
                duenio!!.idDuenio= resultadoConsultaLectura.getInt(0)
                duenio.nombreDuenio= resultadoConsultaLectura.getString(1)
                duenio.fechaNacimiento= resultadoConsultaLectura.getString(2)
                duenio.activo= resultadoConsultaLectura.getString(3)
                duenio.salarioDuenio= resultadoConsultaLectura.getString(4).toDouble()
                duenio.edadDuenio= resultadoConsultaLectura.getString(5).toInt()
                lista.add(duenio)
            }while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return lista
    }
    fun actualizarDuenio(id1:Int, nombre:String, fecha:String, activo:String, salario:String, edad:String ):Boolean{
        var lista= DuenioBDD.TablaDuenio!!.listarDuenios()
        val id=lista[id1].idDuenio.toString()
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("fecha", fecha)
        valoresAActualizar.put("activo", activo)
        valoresAActualizar.put("salario", salario)
        valoresAActualizar.put("edad", edad)
        val resultadoActualizacion = conexionEscritura
            .update(
                "DUENIO", // Nombre tabla
                valoresAActualizar,  // Valores a actualizar
                "id=?", // Clausula Where
                arrayOf(
                    id.toString()
                ) // Parametros clausula Where
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true
    }

    fun eliminarDuenio(id:Int):Boolean{
        var lista= DuenioBDD.TablaDuenio!!.listarDuenios()
        val idE=lista[id].idDuenio.toString()
        val conexion= writableDatabase
        val resultadoEliminacion=conexion
            .delete("DUENIO","id=?", arrayOf(idE))
        conexion.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    // ------------------- MASCOTA -------------------
    fun crearMascota(id:Int, nombre:String, raza:String, sexo: String, peso: String, edad: String):Boolean{
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("id",id)
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("raza", raza)
        valoresAGuardar.put("sexo", sexo)
        valoresAGuardar.put("peso", peso)
        valoresAGuardar.put("edad", edad)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "MASCOTA",
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return if(resultadoGuardar.toInt() == -1) false else true
    }

    fun listarMascotas(): ArrayList<Mascota>{
        var lista = arrayListOf<Mascota>()
        var mascotaXD: Mascota?
        val baseDatosLectura = readableDatabase
        val scriptConsultarUsuario = "SELECT * FROM MASCOTA"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario,
            null
        )
        if(resultadoConsultaLectura.moveToFirst()){
            do {
                mascotaXD= Mascota(0,"","","",0.0,0)
                mascotaXD!!.idMascota= resultadoConsultaLectura.getInt(0)
                mascotaXD.nombreMascota= resultadoConsultaLectura.getString(1)
                mascotaXD.razaMascota = resultadoConsultaLectura.getString(2)
                mascotaXD.sexoMascota = resultadoConsultaLectura.getString(3)
                mascotaXD.pesoMascota = resultadoConsultaLectura.getString(4).toDouble()
                mascotaXD.edadMascota = resultadoConsultaLectura.getString(5).toInt()
                lista.add(mascotaXD)
            }while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return lista
    }

    fun actualizarMascota(id:Int, nombre:String, raza:String, sexo: String, peso: String, edad: String):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("raza", raza)
        valoresAActualizar.put("sexo", sexo)
        valoresAActualizar.put("peso", peso )
        valoresAActualizar.put("edad", edad)
        val resultadoActualizacion = conexionEscritura
            .update(
                "MASCOTA", // Nombre tabla
                valoresAActualizar,  // Valores a actualizar
                "id=?", // Clausula Where
                arrayOf(
                    id.toString()
                ) // Parametros clausula Where
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true
    }

    fun eliminarMascota(id:Int):Boolean{
        val conexion= writableDatabase
        val resultadoEliminacion=conexion
            .delete("MASCOTA","id=?", arrayOf(id.toString()))
        conexion.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }
}