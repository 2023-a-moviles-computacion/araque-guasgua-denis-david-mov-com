package com.example.examen_ib

class Registers {
    companion object{
        var arregloDueniosMascotas = arrayListOf<DueniosMascotas>()

        init{
            //Dueños
            DuenioBDD.TablaDuenio!!.crearDuenio(1,"Denis Araque","24-12-1999","Si","4500.50","23")

            DuenioBDD.TablaDuenio!!.crearDuenio(2,"Michelle Tapia","25-08-1994","No","5000.75","28")

            // Mascotass
            DuenioBDD.TablaDuenio!!.crearMascota(1,"caramelo","salchicha","M","10.5","5")

            DuenioBDD.TablaDuenio!!.crearMascota(2,"chiripa","poddle","F","8.6","3")

            // Dueños y Mascotas
            arregloDueniosMascotas.add(DueniosMascotas(1,1,1))

            arregloDueniosMascotas.add(DueniosMascotas(2,1,2))

        }
    }
}