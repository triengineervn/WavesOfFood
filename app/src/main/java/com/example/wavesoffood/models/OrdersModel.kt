package com.example.wavesoffood.models

import CartItem
import android.os.Parcel
import android.os.Parcelable

class OrdersModel(
    var userId: String? = null,
    var userName: String? = null,
    var cartItems: MutableList<CartItem>? = null,
    var totalPrice: String? = null,
    var address: String? = null,
    var phone: String? = null,
    var orderAccepted: Boolean = false,
    var paymentReceived: Boolean = false,
    var itemPushKey: String? = null,
    var currentTime: Long? = null
) : Parcelable {

    private constructor(parcel: Parcel) : this(
        userId = parcel.readString(),
        userName = parcel.readString(),
        cartItems = parcel.createTypedArrayList(CartItem.CREATOR),
        totalPrice = parcel.readString(),
        address = parcel.readString(),
        phone = parcel.readString(),
        orderAccepted = parcel.readBoolean(),
        paymentReceived = parcel.readBoolean(),
        itemPushKey = parcel.readString(),
        currentTime = parcel.readValue(Long::class.java.classLoader) as? Long
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(userName)
        parcel.writeTypedList(cartItems)
        parcel.writeString(totalPrice)
        parcel.writeString(address)
        parcel.writeString(phone)
        parcel.writeBoolean(orderAccepted)
        parcel.writeBoolean(paymentReceived)
        parcel.writeString(itemPushKey)
        parcel.writeValue(currentTime)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<OrdersModel> {
        override fun createFromParcel(parcel: Parcel): OrdersModel = OrdersModel(parcel)
        override fun newArray(size: Int): Array<OrdersModel?> = arrayOfNulls(size)
    }
}
