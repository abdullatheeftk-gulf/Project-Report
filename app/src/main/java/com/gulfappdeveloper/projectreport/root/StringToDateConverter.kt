package com.gulfappdeveloper.projectreport.root

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale

fun String.stringToDateStringConverter(): String {
    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(this)!!
    return SimpleDateFormat("dd-MM-yyy, h:mm:ss a", Locale.getDefault()).format(date)
}

fun LocalDate.localDateToStringConverter():String{
    return  "${dayOfMonth}-${monthValue}-${year}"
}