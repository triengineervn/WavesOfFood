package com.example.wavesoffood.models

import android.os.Parcel
import android.os.Parcelable

data class ReBuyItem(
    var reBuyName: String? = null,
    var reBuyPrice: String? = null,
    var reBuyImageUrl: String? = null
) : BaseItem(reBuyName, reBuyPrice, reBuyImageUrl), Parcelable {
    private constructor(parcel: Parcel) : this(
        reBuyName = parcel.readString(),
        reBuyPrice = parcel.readString(),
        reBuyImageUrl = parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(reBuyName)
        parcel.writeString(reBuyPrice)
        parcel.writeString(reBuyImageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReBuyItem> {
        override fun createFromParcel(parcel: Parcel): ReBuyItem {
            return ReBuyItem(parcel)
        }

        override fun newArray(size: Int): Array<ReBuyItem?> {
            return arrayOfNulls(size)
        }
    }
}