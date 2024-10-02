package com.example.wavesoffood.models

data class MenuItem(
    override val name: String? = null,
    override val price: String? = null,
    override val image: String? = null,
    override val description: String? = null,
    override val ingredients: String? = null
) : BaseItem(name, price, image, description, ingredients)
