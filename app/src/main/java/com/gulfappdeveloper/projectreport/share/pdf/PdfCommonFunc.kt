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

fun Canvas.writePeriodText(
    fromDate: String,
    fromTime:String,
    toDate: String,
    toTime:String,
    yPosition: Float,

) {
    var xPosition = 30f
    drawText("Period : ", xPosition, yPosition, Paint().apply {
        textSize = 14f
        color = Color.BLACK
    })

    var textWidth = Paint().measureText("Period :-") + 5
    xPosition += textWidth
    drawText("$fromDate, $fromTime", xPosition, yPosition, Paint().apply {
        textSize = 14f
        color = Color.argb(255, 102, 80, 164)
    })

    textWidth = Paint().measureText("$fromDate, $fromTime  ") + 10f
    xPosition += textWidth
    drawText(" to", xPosition, yPosition, Paint().apply {
        textSize = 14f
        color = Color.BLACK
    })

    textWidth = Paint().measureText("-to") + 5f
    xPosition += textWidth
    drawText("$toDate, $toTime", xPosition, yPosition, Paint().apply {
        textSize = 14f
        color = Color.argb(255, 102, 80, 164)
    })

}

fun Canvas.writePeriodTextLedger(
    fromDate: String,
    fromTime: String,
    toDate: String,
    toTime: String,
    yPosition: Float
) {
    var xPosition = 30f
    drawText("Period", xPosition, yPosition, Paint().apply {
        textSize = 12f
        color = Color.BLACK
    })
    xPosition += 90f
    drawText(":", xPosition, yPosition, Paint().apply {
        textSize = 12f
        color = Color.BLACK
    })

    var textWidth = 0f
    xPosition += 10
    drawText("$fromDate, $fromTime", xPosition, yPosition, Paint().apply {
        textSize = 12f
        color = Color.argb(255, 102, 80, 164)
    })

    textWidth = Paint().measureText("$fromDate, $fromTime")
    xPosition += textWidth
    drawText(" to", xPosition, yPosition, Paint().apply {
        textSize = 12f
        color = Color.BLACK
    })

    textWidth = Paint().measureText("-to")+4f
    xPosition += textWidth
    drawText("$toDate, $toTime", xPosition, yPosition, Paint().apply {
        textSize = 12f
        color = Color.argb(255, 102, 80, 164)
    })

}

fun Canvas.writeCompanyName(companyName: String, yPosition: Float,xPosition: Float) {

    drawText("Company : $companyName", xPosition, yPosition, Paint().apply {
        textSize = 10f
        color = Color.BLACK
        textAlign = Paint.Align.RIGHT
    })

}

fun <T> calculatePageCount(list: List<T>, numberOfItemsInOnePage: Int): Int {
    try {
        val lengthOfTheList = list.size
        var fullPage = lengthOfTheList / numberOfItemsInOnePage
        return when (lengthOfTheList % numberOfItemsInOnePage) {
            in 0..(numberOfItemsInOnePage - 2) -> {
                fullPage += 1
                fullPage
            }

            else -> {
                fullPage += 2
                fullPage
            }
        }
    } catch (e: Exception) {
        throw Exception(e.message)
    }
}

fun <T> calculatePageCountForLedgerReports(list: List<T>): Int {
    try {
        val newList = mutableListOf<Int>()
        var pageCount = 0
        list.forEachIndexed { index, _ ->
            newList.add(index)
            if ((index + 1) == 39) {
                if (list.size == 39) {
                    pageCount += 2
                    newList.clear()
                } else {
                    pageCount++
                    newList.clear()
                }
            }
            if ((index - 38) % 41 == 0 && (index - 38) != 0) {
                if (list.size == (index + 1)) {
                    pageCount += 2
                    newList.clear()
                } else {
                    pageCount++
                    newList.clear()
                }
            }
        }

        when (newList.size) {
            in 1..39 -> {
                pageCount++
            }

            40 -> {
                pageCount += 2
            }

            else -> Unit
        }
        return pageCount
    } catch (e: Exception) {
        throw Exception(e.message)
    }
}