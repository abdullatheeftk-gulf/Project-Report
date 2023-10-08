package com.gulfappdeveloper.projectreport.root

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.stringToDateStringConverter(): String {
    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(this)!!
    return SimpleDateFormat("dd-MM-yyyy, HH:mm", Locale.getDefault()).format(date)
}

fun LocalDate.localDateToStringConverter():String{
    return format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
}

fun LocalTime.localTimeToStringConverter():String{
    return format(DateTimeFormatter.ofPattern("HH:mm"))
}