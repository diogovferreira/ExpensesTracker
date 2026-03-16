package com.dfcoding.expensetracker.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object Formatter{

    //DATE RELATED HELPER
    fun formatDate(epochMillis: Long): String {
        val instant = Instant.fromEpochMilliseconds(epochMillis)
        val localDate = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date

        val day = localDate.dayOfMonth.toString().padStart(2, '0')
        val month = localDate.monthNumber.toString().padStart(2, '0')
        val year = localDate.year

        return "$day/$month/$year"
    }

    fun formatMonthYear(epochMillis: Long): String {
        val instant = Instant.fromEpochMilliseconds(epochMillis)
        val localDate = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
        return "${localDate.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${localDate.year}"
        // Output: "March 2026"
    }

    fun currentTimeMillis(): Long {
        return Clock.System.now().toEpochMilliseconds()
    }

    fun isToday(epochMillis: Long): Boolean {
        val inputDate = Instant.fromEpochMilliseconds(epochMillis)
            .toLocalDateTime(TimeZone.currentSystemDefault()).date
        val today = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault()).date
        return inputDate == today
    }
}