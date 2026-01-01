package com.codelab.basics.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


fun LocalDateTime.toTimestampWithTimeZone(withSeconds : Boolean = true): String {
    val formatter = DateTimeFormatter.ofPattern(if (withSeconds) "yyyy-MM-dd HH:mm:ss" else "yyyy-MM-dd")
    return this.format(formatter)
}

fun Double.toTwoDecimals(): String =
    String.format(Locale.US, "%.2f", this)