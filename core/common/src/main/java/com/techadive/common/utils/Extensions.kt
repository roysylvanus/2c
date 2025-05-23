package com.techadive.common.utils

import java.time.LocalDate
import kotlin.math.round

/**
 * Extracts the year from an ISO 8601 date string (e.g., "2024-05-23").
 */
fun String.getYear(): String =
    LocalDate.parse(this).year.toString()

/**
 * Rounds the number to 1 decimal place.
 * For true 2 decimal rounding, change to: `(round(this * 100) / 100)`
 */
fun Double.roundTo1Dec(): Double =
    round(this * 10) / 10

/**
 * Converts an Int to a strict Boolean:
 * 0 = false, 1 = true, other values = false.
 */
fun Int.toBooleanStrict(): Boolean = when (this) {
    0 -> false
    1 -> true
    else -> false
}