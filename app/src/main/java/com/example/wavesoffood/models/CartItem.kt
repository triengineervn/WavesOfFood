package com.example.wavesoffood.models

class CartItem(
    name: String,
    price: String,
    imageResId: Int,
    var quantity: Int = 1
) : BaseItem(name, price, imageResId)