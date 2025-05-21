package com.techadive.common

import java.time.LocalDate
import kotlin.math.round

fun String.getYear() =
    LocalDate.parse(this).year.toString()

fun Double.roundTo2Dec() =
    (round(this * 10)) / 10
