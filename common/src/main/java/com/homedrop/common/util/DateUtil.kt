package com.homedrop.common.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun getDayOfMonthSuffix(day: Int): String {
        if (day in 1..31) {
            return if (day in 11..13) {
                "th"
            } else when (day % 10) {
                1 -> "st"
                2 -> "nd"
                3 -> "rd"
                else -> "th"
            }
        } else {
            throw IllegalArgumentException("illegal day of month: $day")
        }
    }

    fun convertDateFormat(patternFrom: String, patternTo: String, dateString: String = ""): String {
        val dateFormatFrom = SimpleDateFormat(patternFrom, Locale.getDefault())
        val dateFormatTo = SimpleDateFormat(patternTo, Locale.getDefault())
        try {
            val date = dateFormatFrom.parse(dateString)
            date?.let {
                return dateFormatTo.format(date)
            }
        } catch (e: ParseException) {
        }
        return dateString
    }

    /**
     * @param milliseconds - This should be the UNIX timestamp
     */
    fun createDateFormat(pattern: String, milliseconds: Long): String {
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return dateFormat.format(Date(milliseconds * 1000))
    }

    /**
     * this - This should be the UNIX timestamp
     */
    fun Long.getDayAgoTime(): String {
        val currentDateCalendar = Calendar.getInstance()
        val startDateCalendar = Calendar.getInstance()
        startDateCalendar.timeInMillis = this * 1000

        val millisecondsDifference = currentDateCalendar.time.time - startDateCalendar.time.time
        val dayLeft = millisecondsDifference / (24 * 60 * 60 * 1000)

        val timeString = when {
            dayLeft > 1 -> "$dayLeft days"
            dayLeft == 1.toLong() -> "$dayLeft day"
            else -> {
                when (val hourLeft = millisecondsDifference / (60 * 60 * 1000)) {
                    in 2..23 -> "$hourLeft hours"
                    1.toLong() -> "$hourLeft hour"
                    else -> {
                        when (val minuteLeft = millisecondsDifference / (60 * 1000)) {
                            in 2..59 -> "$minuteLeft mins"
                            1.toLong() -> "$minuteLeft min"
                            else -> "few secs"
                        }
                    }
                }
            }
        }

        return "$timeString ago"
    }

    fun getDateTitleForGallery(date: Long): String {
        val targetCalendar = Calendar.getInstance()
        targetCalendar.timeInMillis = date * 1000
        val calendar = Calendar.getInstance()
        return if (isCurrentWeek(calendar, targetCalendar)) "Recent"
        else {
            calendar.add(Calendar.WEEK_OF_YEAR, -1)
            if (isPastWeek(calendar, targetCalendar)) "Last Week"
            else {
                calendar.set(Calendar.DAY_OF_WEEK, 1)
                if (isCurrentMonth(calendar, targetCalendar)) "Last Month"
                else getMonthName(date)
            }
        }
    }

    private fun isCurrentWeek(calendar: Calendar, targetCalendar: Calendar): Boolean {
        return calendar[Calendar.WEEK_OF_YEAR] == targetCalendar[Calendar.WEEK_OF_YEAR]
                && calendar[Calendar.YEAR] == targetCalendar[Calendar.YEAR]
    }

    private fun isPastWeek(calendar: Calendar, targetCalendar: Calendar): Boolean {
        return calendar[Calendar.WEEK_OF_YEAR] == targetCalendar[Calendar.WEEK_OF_YEAR]
                && calendar[Calendar.YEAR] == targetCalendar[Calendar.YEAR]
    }

    private fun isCurrentMonth(calendar: Calendar, targetCalendar: Calendar): Boolean {
        return calendar[Calendar.YEAR] == targetCalendar[Calendar.YEAR]
                && calendar[Calendar.MONTH] == targetCalendar[Calendar.MONTH]
                && calendar[Calendar.DAY_OF_MONTH] > targetCalendar[Calendar.DAY_OF_MONTH]
    }

    private fun getMonthName(date: Long): String {
        return SimpleDateFormat("MMMM", Locale.getDefault()).format(date * 1000)
    }
}