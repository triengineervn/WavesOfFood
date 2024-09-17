package com.example.wavesoffood.models

data class ReBuyItem(
    val reBuyName: String,
    val reBuyPrice: String,
    val reBuyImageResId: Int
) : BaseItem(reBuyName, reBuyPrice, reBuyImageResId)