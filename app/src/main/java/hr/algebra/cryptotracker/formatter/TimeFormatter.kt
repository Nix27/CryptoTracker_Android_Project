package hr.algebra.cryptotracker.formatter

import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField

private val FORMATTER = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.of("Europe/Zagreb"))
fun formatTimestampToTime(timestamp: Long): String {
    val ofEpochMilli = Instant.ofEpochMilli(timestamp)
    return FORMATTER.format(ofEpochMilli)
}

fun formatStringTimeToTimeStamp(timeAsString: String): Long {
    val time = LocalTime.parse(timeAsString, FORMATTER)
    val epochSecond = time.getLong(ChronoField.SECOND_OF_DAY)
    return epochSecond * 1000L
}