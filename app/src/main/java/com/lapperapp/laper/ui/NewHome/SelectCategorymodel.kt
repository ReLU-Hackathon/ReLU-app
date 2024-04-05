package com.lapperapp.laper.ui.NewHome

import android.os.Parcel
import android.os.Parcelable

data class SelectCategorymodel(
    var title: String?,
    var image: String?,
    var id: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(image)
        parcel.writeString(id)
    }

    companion object CREATOR : Parcelable.Creator<SelectCategorymodel> {
        override fun createFromParcel(parcel: Parcel): SelectCategorymodel {
            return SelectCategorymodel(parcel)
        }

        override fun newArray(size: Int): Array<SelectCategorymodel?> {
            return arrayOfNulls(size)
        }
    }
}

