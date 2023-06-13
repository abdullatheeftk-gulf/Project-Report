package com.gulfappdeveloper.projectreport.share.pdf

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Date

private const val TAG = "CustomerPaymentReportPd"

object CustomerPaymentReportPdf {
    suspend fun makePdf(
        pdfDocument: PdfDocument,
        context: Context,
        list: List<CustomerPaymentResponse>,
        fromDate: String,
        toDate: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) {
        try {
            val totalPages = calculatePageCount(list)
            var pageCount = 1
            val pageList = mutableListOf<CustomerPaymentResponse>()
            list.forEachIndexed { index, customerPaymentResponse ->
                pageList.add(customerPaymentResponse)
                if ((index + 1) % 27 == 0) {
                    createPage(
                        pageNo = pageCount,
                        pdfDocument = pdfDocument,
                        list = pageList,
                        fromDate = fromDate,
                        toDate = toDate,
                        totalPages = totalPages,
                        haveAnyError = haveAnyError
                    )
                    pageCount++
                    pageList.clear()
                }

            }
            if (pageList.size > 0) {
                createPage(
                    pageNo = pageCount,
                    pdfDocument = pdfDocument,
                    list = pageList,
                    fromDate = fromDate,
                    toDate = toDate,
                    totalPages = totalPages,
                    haveAnyError = haveAnyError
                )

                pageList.clear()
            }


            val file =
                File(context.getExternalFilesDir(null), "CustomerPaymentReport_${Date()}.pdf")
            try {
                withContext(Dispatchers.IO) {
                    pdfDocument.writeTo(FileOutputStream(file))
                }
            } catch (e: IOException) {
                haveAnyError(true, e.message)
            }
            pdfDocument.close()


            val uri = FileProvider.getUriForFile(context, context.packageName, file)

            getUri(uri)

            haveAnyError(false, null)
        } catch (e: Exception) {
            haveAnyError(true, e.message)
        } catch (e: java.lang.Exception) {
            haveAnyError(true, e.message)
        }

    }


    private fun calculatePageCount(list: List<CustomerPaymentResponse>): Int {
        try {
            val lengthOfTheList = list.size
            var fullPage = lengthOfTheList / 27
            val inCompletedPage = lengthOfTheList % 27
            return if (inCompletedPage > 0) {
                fullPage += 1
                fullPage
            } else {
                fullPage
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    private fun createPage(
        pageNo: Int,
        pdfDocument: PdfDocument,
        list: List<CustomerPaymentResponse>,
        fromDate: String,
        toDate: String,
        totalPages: Int,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) {
        try {
            val paint = Paint()

            val pageInfo = PdfDocument
                .PageInfo
                .Builder(1000, 707, pageNo)
                .create()

            val page = pdfDocument.startPage(pageInfo)
            val canvas = page.canvas


            /*
             paint.textAlign = Paint.Align.CENTER
             paint.textSize = 20f
             paint.color = Color.BLACK
             paint.flags = Paint.UNDERLINE_TEXT_FLAG*/


            // Write heading
            var yPosition = 50f
            val xPositionHeading = pageInfo.pageWidth / 2f
            canvas.writeHeading(
                headingTitle = "Customer Payment Report",
                xPosition = xPositionHeading,
                yPosition = yPosition
            )

            // dates
            yPosition += 30f
            canvas.writePeriodText(fromDate, toDate, yPosition)
            /*paint.textAlign = Paint.Align.LEFT
            paint.textSize = 14f
            paint.color = Color.BLACK
            paint.flags = Paint.ANTI_ALIAS_FLAG
            */


            //var xPosition = 30f

            /*var textToWrite = "Report from"
            canvas.drawText(textToWrite, xPosition, yPosition, paint)

            var textWidth = paint.measureText(textToWrite)

            paint.color = Color.BLUE

            xPosition += textWidth + 5
            textToWrite = fromDate
            canvas.drawText(textToWrite, xPosition, yPosition, paint)

            textWidth = paint.measureText(textToWrite)

            paint.color = Color.BLACK

            xPosition += textWidth + 5
            textToWrite = "to"
            canvas.drawText(textToWrite, xPosition, yPosition, paint)

            textWidth = paint.measureText(textToWrite)

            paint.color = Color.BLUE

            xPosition += textWidth + 5
            textToWrite = toDate
            canvas.drawText(textToWrite, xPosition, yPosition, paint)*/


            // Table

            // Header
            var xPosition = 30f
            yPosition += 8f
            paint.color = Color.argb(255, 210, 210, 210)
            paint.style = Paint.Style.FILL

            canvas.drawRect(xPosition, yPosition, 970f, yPosition + 25, paint)

            paint.flags = Paint.ANTI_ALIAS_FLAG
            paint.style = Paint.Style.STROKE
            paint.color = Color.BLACK

            canvas.drawRect(xPosition, yPosition, 970f, yPosition + 25, paint)

            paint.color = Color.argb(255, 90, 90, 90)
            paint.strokeWidth = 0.4f

            xPosition += 30
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, paint)
            xPosition += 110
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, paint)
            xPosition += 90
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, paint)
            xPosition += 260
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, paint)
            xPosition += 90
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, paint)
            xPosition += 90
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, paint)
            xPosition += 90
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, paint)
            xPosition += 90
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, paint)

            xPosition = 45f
            yPosition += 16.5f

            val paintText = Paint()
            paintText.textSize = 12f
            paintText.color = Color.BLACK
            paintText.textAlign = Paint.Align.CENTER


            canvas.drawText("SI", xPosition, yPosition, paintText)

            paintText.textAlign = Paint.Align.CENTER
            xPosition = 115f
            canvas.drawText("Date", xPosition, yPosition, paintText)

            paintText.textAlign = Paint.Align.CENTER
            xPosition += 100
            canvas.drawText("Rec.no", xPosition, yPosition, paintText)

            xPosition += 175
            canvas.drawText("Party", xPosition, yPosition, paintText)

            xPosition += 175
            canvas.drawText("Cash", xPosition, yPosition, paintText)

            xPosition += 90
            canvas.drawText("Card", xPosition, yPosition, paintText)

            xPosition += 90
            canvas.drawText("Online", xPosition, yPosition, paintText)

            xPosition += 90
            canvas.drawText("Credit", xPosition, yPosition, paintText)

            xPosition += 90
            canvas.drawText("Total", xPosition, yPosition, paintText)

            // Table items
            xPosition = 30f
            yPosition += 9.5f

            val testYPosition = yPosition

            list.forEachIndexed { index, customerPaymentResponse ->
                xPosition = 30f
                val paintTable = Paint()
                if (index % 2 == 0) {
                    paintTable.color = Color.argb(255, 255, 255, 255)
                    paintTable.style = Paint.Style.FILL
                } else {
                    paintTable.color = Color.argb(255, 219, 215, 211)
                    paintTable.style = Paint.Style.FILL
                }
                canvas.drawRect(xPosition, yPosition, 970f, yPosition + 20f, paintTable)

                paintTable.style = Paint.Style.STROKE
                paintTable.color = Color.BLACK

                canvas.drawRect(xPosition, yPosition, 970f, yPosition + 20f, paintTable)

                paintTable.color = Color.argb(255, 90, 90, 90)
                paintTable.strokeWidth = 0.4f

                // Index
                xPosition += 15f
                val printIndex = pageNo * 27 + index + 1 - 27
                //Log.d(TAG, "printIndex: $index, printItem count $printIndex")
                canvas.drawText(printIndex.toString(), xPosition, yPosition + 14.2f, Paint().apply {
                    textAlign = Paint.Align.CENTER
                    textSize = 10f
                })
                xPosition += 15
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                //Date
                xPosition += 55
                canvas.drawText(
                    customerPaymentResponse.date,
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 55
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)


                // Rec. No
                xPosition += 45
                canvas.drawText(
                    customerPaymentResponse.receiptNo.toString(),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })
                xPosition += 45
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                // Party
                xPosition += 130
                canvas.drawText(
                    customerPaymentResponse.party,
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 130
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                //cash
                xPosition += 45
                canvas.drawText(
                    customerPaymentResponse.cash,
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 45
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                // Card
                xPosition += 45
                canvas.drawText(
                    customerPaymentResponse.card,
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 45
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)


                // online
                xPosition += 45
                canvas.drawText(
                    customerPaymentResponse.onlineAmount,
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 45
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                // credit
                xPosition += 45
                canvas.drawText(
                    customerPaymentResponse.credit,
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })
                xPosition += 45
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                xPosition += 45
                canvas.drawText(
                    customerPaymentResponse.total,
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                        color = Color.BLUE
                    })

                yPosition += 20f


            }

            canvas.drawText("page $pageNo off $totalPages", 925f, 680f, Paint().apply {
                color = Color.BLACK
                textSize = 8f
                textAlign = Paint.Align.CENTER
                textSkewX = -0.25f
            })


            pdfDocument.finishPage(page)
        } catch (e: Exception) {
            haveAnyError(true, e.message)
        }
    }
}



