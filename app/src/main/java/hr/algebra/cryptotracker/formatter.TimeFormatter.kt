package hr.algebra.cryptotracker

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun formatTimestampToTime(timestamp: Long): String {
    val ofEpochMilli = Instant.ofEpochMilli(timestamp)
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.of("Europe/Zagreb"))
    return formatter.format(ofEpochMilli)
}