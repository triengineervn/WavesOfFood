package com.example.wavesoffood.models

data class ProductItem(
    val productName: String,
    val productPrice: String,
    val productImageResId: Int
) : BaseItem(productName, productPrice, productImageResId)