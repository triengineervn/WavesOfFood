package com.example.wavesoffood.models

data class ReBuyItem(
    val reBuyName: String,
    val reBuyPrice: String,
    val reBuyImageUrl: String
) : BaseItem(reBuyName, reBuyPrice, reBuyImageUrl)