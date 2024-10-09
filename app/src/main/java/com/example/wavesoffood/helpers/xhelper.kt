package com.example.wavesoffood.helpers

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object xhelper {
    fun formatTime(milliseconds: Long): String {
        val instant = Instant.ofEpochMilli(milliseconds)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            .withZone(ZoneId.systemDefault())
        return formatter.format(instant)
    }
}