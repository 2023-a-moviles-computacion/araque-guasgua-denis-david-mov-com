package com.example.examen_ib

import android.os.Parcel
import android.os.Parcelable

class Duenio(
    var idDuenio: String?,
    var nombreDuenio: String?,
    var direccionDuenio: String?,
    var generoDuenio: String?,
    var salarioDuenio: Double?,
    var edadDuenio: Int?
) :Parcelable{
    override fun toString(): String {
        return "${nombreDuenio}"
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idDuenio)
        parcel.writeString(nombreDuenio)
        parcel.writeString(direccionDuenio)
        parcel.writeString(generoDuenio)
        parcel.writeDouble(salarioDuenio!!)
        parcel.writeInt(edadDuenio!!)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Duenio> {
        override fun createFromParcel(parcel: Parcel): Duenio {
            return Duenio(parcel)
        }

        override fun newArray(size: Int): Array<Duenio?> {
            return arrayOfNulls(size)
        }
    }
}