package com.example.examen_ib

import android.os.Parcel
import android.os.Parcelable

class DueniosMascotas(
    val idDuenioMascota: Int,
    val idDuenio: Int,
    val idMascota: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idDuenioMascota)
        parcel.writeInt(idDuenio)
        parcel.writeInt(idMascota)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DueniosMascotas> {
        override fun createFromParcel(parcel: Parcel): DueniosMascotas {
            return DueniosMascotas(parcel)
        }

        override fun newArray(size: Int): Array<DueniosMascotas?> {
            return arrayOfNulls(size)
        }
    }
}