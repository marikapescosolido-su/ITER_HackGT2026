package com.iter.app.ui.clinician

import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val shortDate = DateTimeFormatter.ofPattern("MMM d")

fun formatDate(date: LocalDate): String = date.format(shortDate)

fun formatAverage(value: Float?): String = value?.let { "%.1f".format(it) } ?: "–"
