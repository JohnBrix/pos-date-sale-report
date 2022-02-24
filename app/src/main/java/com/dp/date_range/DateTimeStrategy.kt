package com.dp.date_range

import java.text.SimpleDateFormat
import java.util.*

class DateTimeStrategy {


    /**
     * Set local of time for use in application.
     * @param lang Language.
     * @param reg Region.
     */
    fun setLocale(lang: String, reg: String) {
        var locale: Locale? = null
        locale = Locale(lang, reg)
        var dateFormat: SimpleDateFormat? = null
        dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", locale)
    }

    /**
     * Sets current time format.
     * @param date date of this format.
     * @return current time format.
     */
    fun format(date: String): String? {
        var locale: Locale? = null
        var dateFormat: SimpleDateFormat? = null
        return dateFormat?.format(Calendar.getInstance(locale).time)
    }

    /**
     * Returns current time.
     * @return current time.
     */
    fun getCurrentTime(): String {
        var dateFormat: SimpleDateFormat? = null
        return dateFormat?.format(Calendar.getInstance().time).toString()
    }

    /**
     * Convert the calendar format to date format for adapt in SQL.
     * @param instance calendar .
     * @return date format.
     */
    fun getSQLDateFormat(instance: Calendar): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val date: Date = instance.getTime()
        val formatted = sdf.format(date)

        return formatted.format(instance.time).toString()
    }
}