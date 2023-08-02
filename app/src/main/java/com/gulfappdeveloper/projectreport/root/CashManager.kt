package com.gulfappdeveloper.projectreport.root


fun Float.formatFloatToTwoDecimalPlaces():String{
    return String.format("%.2f",this)
}