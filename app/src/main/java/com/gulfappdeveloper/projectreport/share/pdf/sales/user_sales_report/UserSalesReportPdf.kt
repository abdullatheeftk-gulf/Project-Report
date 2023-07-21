package com.gulfappdeveloper.projectreport.share.pdf.sales.user_sales_report

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.core.content.FileProvider
import com.gulfappdeveloper.projectreport.domain.models.sales.UserSalesResponse
import com.gulfappdeveloper.projectreport.share.pdf.calculatePageCount
import com.gulfappdeveloper.projectreport.share.pdf.writeCompanyName
import com.gulfappdeveloper.projectreport.share.pdf.writeHeading
import com.gulfappdeveloper.projectreport.share.pdf.writePeriodText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object UserSalesReportPdf {

    suspend fun makePdf(
        companyName: String,
        pdfDocument: PdfDocument,
        context: Context,
        list: List<UserSalesResponse>,
        fromDate: String,
        toDate: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) {
        try {
            val totalPages = calculatePageCount(list, numberOfItemsInOnePage = 41)
            var pageCount = 1

            val pageList = mutableListOf<UserSalesResponse>()
            var userSalesReportTotal = 0.0

            list.forEachIndexed { index, userSalesResponse ->
                userSalesReportTotal += userSalesResponse.salesAmount
                pageList.add(userSalesResponse)
                if ((index + 1) % 41 == 0) {

                    createPage(
                        pageNo = pageCount,
                        pdfDocument = pdfDocument,
                        list = pageList,
                        userSalesReportTotal = userSalesReportTotal,
                        fromDate = fromDate,
                        toDate = toDate,
                        totalPages = totalPages,
                        haveAnyError = haveAnyError,
                        companyName = companyName
                    )
                    pageCount++
                    pageList.clear()
                }

            }
            when (pageList.size) {
                in 1..39 -> {
                    createPage(
                        pageNo = pageCount,
                        pdfDocument = pdfDocument,
                        list = pageList,
                        userSalesReportTotal = userSalesReportTotal,
                        fromDate = fromDate,
                        toDate = toDate,
                        totalPages = totalPages,
                        haveAnyError = haveAnyError,
                        companyName = companyName
                    )

                    pageList.clear()
                }

                40 -> {
                    createPage(
                        pageNo = pageCount,
                        pdfDocument = pdfDocument,
                        list = pageList,
                        userSalesReportTotal = userSalesReportTotal,
                        fromDate = fromDate,
                        toDate = toDate,
                        totalPages = totalPages,
                        haveAnyError = haveAnyError,
                        companyName = companyName
                    )
                    pageList.clear()
                    pageCount++
                    createPage(
                        pageNo = pageCount,
                        pdfDocument = pdfDocument,
                        list = pageList,
                        userSalesReportTotal = userSalesReportTotal,
                        fromDate = fromDate,
                        toDate = toDate,
                        totalPages = totalPages,
                        haveAnyError = haveAnyError,
                        companyName = companyName
                    )
                }

                else -> {
                    pageList.clear()
                    createPage(
                        pageNo = pageCount,
                        pdfDocument = pdfDocument,
                        list = pageList,
                        userSalesReportTotal = userSalesReportTotal,
                        fromDate = fromDate,
                        toDate = toDate,
                        totalPages = totalPages,
                        haveAnyError = haveAnyError,
                        companyName = companyName
                    )
                }
            }
            val file =
                File(context.getExternalFilesDir(null), "UserSalesReport_${Date()}.pdf")
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


    private fun createPage(
        companyName: String,
        pageNo: Int,
        pdfDocument: PdfDocument,
        list: List<UserSalesResponse>,
        userSalesReportTotal: Double,
        fromDate: String,
        toDate: String,
        totalPages: Int,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
    ) {
        try {
            val isItLastPage: Boolean = pageNo == totalPages


            val pageInfo = PdfDocument
                .PageInfo
                .Builder(707, 1000, pageNo)
                .create()

            val page = pdfDocument.startPage(pageInfo)
            val canvas = page.canvas


            // Write heading
            var yPosition = 50f
            val xPositionHeading = pageInfo.pageWidth / 2f
            canvas.writeHeading(
                headingTitle = "User Sales Report",
                xPosition = xPositionHeading,
                yPosition = yPosition
            )

            // dates
            yPosition += 30f
            canvas.writePeriodText(fromDate, toDate, yPosition)
            canvas.writeCompanyName(
                companyName = companyName,
                yPosition = yPosition,
                xPosition = 677f
            )


            // Table

            // Header
            var xPosition = 30f
            yPosition += 8f
            //val paint = Paint()


            // Drawing header rectangle
            canvas.drawRect(xPosition, yPosition, 677f, yPosition + 25, Paint().apply {
                color = Color.argb(255, 210, 210, 210)
                style = Paint.Style.FILL
            })
            canvas.drawRect(xPosition, yPosition, 677f, yPosition + 25, Paint().apply {
                style = Paint.Style.STROKE
                color = Color.BLACK
            })


            // header items
            // Si
            xPosition = 60f
            canvas.drawText("SI", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 30f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // User Name
            xPosition += 178.5f
            canvas.drawText("User Name", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 178.5f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })


            // Sales Amount
            xPosition += 115f
            canvas.drawText("sales Amount", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })


            // Table items
            yPosition += 25f


            list.forEachIndexed { index, userSales ->
                xPosition = 30f
                val paintTable = Paint()
                if (index % 2 == 0) {
                    paintTable.color = Color.argb(255, 255, 255, 255)
                    paintTable.style = Paint.Style.FILL
                } else {
                    paintTable.color = Color.argb(255, 219, 215, 211)
                    paintTable.style = Paint.Style.FILL
                }
                canvas.drawRect(xPosition, yPosition, 677f, yPosition + 20f, paintTable)

                paintTable.style = Paint.Style.STROKE
                paintTable.color = Color.BLACK

                canvas.drawRect(xPosition, yPosition, 677f, yPosition + 20f, paintTable)

                paintTable.color = Color.argb(255, 90, 90, 90)
                paintTable.strokeWidth = 0.4f

                // Index
                xPosition += 30f
                val printIndex = pageNo * 41 + index + 1 - 41
                canvas.drawText(printIndex.toString(), xPosition, yPosition + 14.2f, Paint().apply {
                    textAlign = Paint.Align.CENTER
                    textSize = 10f
                })
                xPosition += 30f
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                // User Name
                xPosition += 178.5f
                canvas.drawText(
                    userSales.userName,
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 178.5f
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)


                xPosition += 115f
                canvas.drawText(
                    String.format("%.2f", userSales.salesAmount),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                        color = Color.BLUE
                    })

                yPosition += 20f


            }

            if (isItLastPage) {
                writeTotalValueRow(
                    yPosition = yPosition,
                    canvas = canvas,
                    userSalesReportTotal = userSalesReportTotal
                )
            }

            val date =
                SimpleDateFormat("dd-MM-yyyy, hh:mm:ss a", Locale.getDefault()).format(Date())
            canvas.drawText(date, 30f, 973f, Paint().apply {
                color = Color.BLACK
                textSize = 8f
                textAlign = Paint.Align.LEFT
                textSkewX = -0.25f
            })

            canvas.drawText("Unipospro", pageInfo.pageWidth / 2f, 973f, Paint().apply {
                color = Color.BLACK
                textSize = 8f
                textAlign = Paint.Align.CENTER
            })

            canvas.drawText("page $pageNo of $totalPages", 677f, 973f, Paint().apply {
                color = Color.BLACK
                textSize = 8f
                textAlign = Paint.Align.RIGHT
                textSkewX = -0.25f
            })



            pdfDocument.finishPage(page)
        } catch (e: Exception) {
            haveAnyError(true, e.message)
        }
    }

    private fun writeTotalValueRow(
        yPosition: Float,
        canvas: Canvas,
        userSalesReportTotal: Double
    ) {
        canvas.drawRect(30f, yPosition, 677f, yPosition + 25f, Paint().apply {
            style = Paint.Style.STROKE
        })

        var xPosition = 447f

        canvas.drawText(
            "TOTAL:- ",
            xPosition,
            yPosition + 16.5f,
            Paint().apply {
                textAlign = Paint.Align.RIGHT
                textSize = 14f
                strokeWidth = 1f
            })
        canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
            color = Color.argb(255, 90, 90, 90)
            strokeWidth = 0.4f
        })


        xPosition += 115f
        canvas.drawText(
            String.format("%.2f", userSalesReportTotal),
            xPosition,
            yPosition + 16.5f,
            Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
                strokeWidth = 1f
            })
    }
}