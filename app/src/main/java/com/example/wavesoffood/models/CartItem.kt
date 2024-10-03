package com.example.wavesoffood.models

class CartItem(
    name: String? = null,
    price: String? = null,
    imageUrl: String? = null,
    description: String? = null,
    ingredients: String? = null,
    var quantity: Int? = null
) : BaseItem(name, price, imageUrl, description, ingredients)