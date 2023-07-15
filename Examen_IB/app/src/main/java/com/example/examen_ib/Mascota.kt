package com.example.examen_ib

import android.os.Parcel
import android.os.Parcelable

class Mascota(
    var  idMascota: Int,
    var nombreMascota: String?,
    var razaMascota: String?,
    var sexoMascota: String?,
    var pesoMascota: Double?,
    var edadMascota: Int?

) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idMascota)
        parcel.writeString(nombreMascota)
        parcel.writeString(razaMascota)
        parcel.writeString(sexoMascota)
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