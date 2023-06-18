package com.gulfappdeveloper.projectreport.share.pdf

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

fun Canvas.writeHeading(headingTitle: String, xPosition: Float, yPosition: Float) {
    drawText(headingTitle, xPosition, yPosition, Paint().apply {
        textAlign = Paint.Align.CENTER
        textSize = 20f
        color = Color.argb(255, 102, 80, 164)
        flags = Paint.UNDERLINE_TEXT_FLAG
    })
}

fun Canvas.writePeriodText(fromDate: String, toDate: String, yPosition: Float) {
    var xPosition = 30f
    drawText("Period : ", xPosition, yPosition, Paint().apply {
        textSize = 14f
        color = Color.BLACK
    })

    var textWidth = Paint().measureText("Period :-")+5
    xPosition += textWidth
    drawText(fromDate, xPosition, yPosition, Paint().apply {
        textSize = 14f
        color = Color.argb(255, 102, 80, 164)
    })

    textWidth = Paint().measureText(fromDate)+10f
    xPosition += textWidth
    drawText(" to", xPosition, yPosition, Paint().apply {
        textSize = 14f
        color = Color.BLACK
    })

    textWidth = Paint().measureText("-to")+5f
    xPosition += textWidth
    drawText(toDate, xPosition, yPosition, Paint().apply {
        textSize = 14f
        color = Color.argb(255, 102, 80, 164)
    })

}

fun Canvas.writeCompanyName(companyName:String,yPosition: Float){
    val xPosition = 970f
    drawText("Company : $companyName", xPosition, yPosition, Paint().apply {
        textSize = 10f
        color = Color.BLACK
        textAlign =Paint.Align.RIGHT
    })

}