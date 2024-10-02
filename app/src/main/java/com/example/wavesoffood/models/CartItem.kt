package com.example.wavesoffood.models

class CartItem(
    name: String,
    price: String,
    imageUrl: String,
    var quantity: Int = 1
) : BaseItem(name, price, imageUrl)