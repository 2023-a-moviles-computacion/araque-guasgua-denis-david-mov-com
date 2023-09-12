package com.example.examen_ib

import android.os.Parcel
import android.os.Parcelable

class Mascota(
    var idMascota: String?,
    var idDuenio_Mascota: String?,
    var nombreMascota: String?,
    var generoMascota: String?,
    var esCachorro: String?,
    var pesoMascota: Double?,
    var edadMascota: Int?

) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idMascota)
        parcel.writeString(idDuenio_Mascota)
        parcel.writeString(nombreMascota)
        parcel.writeString(generoMascota)
        parcel.writeString(esCachorro)
        parcel.writeDouble(pesoMascota!!)
        parcel.writeInt(edadMascota!!)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "${nombreMascota}"
    }

    companion object CREATOR : Parcelable.Creator<Mascota> {
        override fun createFromParcel(parcel: Parcel): Mascota {
            return Mascota(parcel)
        }

        override fun newArray(size: Int): Array<Mascota?> {
            return arrayOfNulls(size)
        }
    }

}