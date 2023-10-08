package com.gulfappdeveloper.projectreport.share.pdf.sales.sales_invoice_report

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.core.content.FileProvider
import com.gulfappdeveloper.projectreport.domain.models.sales.PosPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.SalesInvoiceResponse
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.SalesInvoiceReportTotals
import com.gulfappdeveloper.projectreport.root.stringToDateStringConverter
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

object SalesInvoiceReportPdf {

    suspend fun makePdf(
        companyName:String,
        pdfDocument: PdfDocument,
        context: Context,
        list: List<SalesInvoiceResponse>,
        salesInvoiceReportTotals: SalesInvoiceReportTotals,
        fromDate: String,
        fromTime:String,
        toDate: String,
        toTime:String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) {
        try {
            val totalPages = calculatePageCount(list, numberOfItemsInOnePage = 27)
            var pageCount = 1

            val pageList = mutableListOf<SalesInvoiceResponse>()

            list.forEachIndexed { index, salesInvoiceResponse ->
                pageList.add(salesInvoiceResponse)
                if ((index + 1) % 27 == 0) {

                    createPage(
                        pageNo = pageCount,
                        pdfDocument = pdfDocument,
                        list = pageList,
                        salesInvoiceReportTotals = salesInvoiceReportTotals,
                        fromDate = fromDate,
                        fromTime = fromTime,
                        toDate = toDate,
                        toTime = toTime,
                        totalPages = totalPages,
                        haveAnyError = haveAnyError,
                        companyName = companyName
                    )
                    pageCount++
                    pageList.clear()
                }

            }
            when (pageList.size) {
                in 1..25 -> {
                    createPage(
                        pageNo = pageCount,
                        pdfDocument = pdfDocument,
                        list = pageList,
                        salesInvoiceReportTotals = salesInvoiceReportTotals,
                        fromDate = fromDate,
                        fromTime = fromTime,
                        toDate = toDate,
                        toTime = toTime,
                        totalPages = totalPages,
                        haveAnyError = haveAnyError,
                        companyName = companyName
                    )

                    pageList.clear()
                }

                26 -> {
                    createPage(
                        pageNo = pageCount,
                        pdfDocument = pdfDocument,
                        list = pageList,
                        salesInvoiceReportTotals = salesInvoiceReportTotals,
                        fromDate = fromDate,
                        fromTime = fromTime,
                        toDate = toDate,
                        toTime = toTime,
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
                        salesInvoiceReportTotals = salesInvoiceReportTotals,
                        fromDate = fromDate,
                        fromTime = fromTime,
                        toDate = toDate,
                        toTime = toTime,
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
                        salesInvoiceReportTotals = salesInvoiceReportTotals,
                        fromDate = fromDate,
                        fromTime = fromTime,
                        toDate = toDate,
                        toTime = toTime,
                        totalPages = totalPages,
                        haveAnyError = haveAnyError,
                        companyName = companyName
                    )
                }
            }
            val file =
                File(context.getExternalFilesDir(null), "SalesInvoiceReport_${Date()}.pdf")
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
        companyName:String,
        pageNo: Int,
        pdfDocument: PdfDocument,
        list: List<SalesInvoiceResponse>,
        salesInvoiceReportTotals: SalesInvoiceReportTotals,
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        totalPages: Int,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
    ) {
        try {
            val isItLastPage: Boolean = pageNo == totalPages


            val pageInfo = PdfDocument
                .PageInfo
                .Builder(1000, 707, pageNo)
                .create()

            val page = pdfDocument.startPage(pageInfo)
            val canvas = page.canvas


            // Write heading
            var yPosition = 50f
            val xPositionHeading = pageInfo.pageWidth / 2f
            canvas.writeHeading(
                headingTitle = "Sales Invoice Report",
                xPosition = xPositionHeading,
                yPosition = yPosition
            )

            // dates
            yPosition += 30f
            canvas.writePeriodText(
                fromDate = fromDate,
                fromTime = fromTime,
                toDate = toDate,
                toTime = toTime,
                yPosition = yPosition
            )
            canvas.writeCompanyName(companyName = companyName,yPosition = yPosition, xPosition = 970f)


            // Table

            // Header
            var xPosition = 30f
            yPosition += 8f
            //val paint = Paint()


            // Drawing header rectangle
            canvas.drawRect(xPosition, yPosition, 970f, yPosition + 25, Paint().apply {
                color = Color.argb(255, 210, 210, 210)
                style = Paint.Style.FILL
            })
            canvas.drawRect(xPosition, yPosition, 970f, yPosition + 25, Paint().apply {
                style = Paint.Style.STROKE
                color = Color.BLACK
            })


            // header items
            // Si
            xPosition = 45f
            canvas.drawText("SI", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 15f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Date
            xPosition += 60f
            canvas.drawText("Date", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 60f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })


            // Invoice No
            xPosition += 30f
            canvas.drawText("Rcpt No", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 30f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Account
            xPosition += 120f
            canvas.drawText("Account", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 120f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Taxable
            xPosition += 45f
            canvas.drawText("Taxable", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 45f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Tax
            xPosition += 45f
            canvas.drawText("Tax", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 45f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Return
            xPosition += 45f
            canvas.drawText("Return", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 45f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Tax on return
            xPosition += 50f
            canvas.drawText("Tax on return", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 50f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })



            // Total
            xPosition += 52.5f
            canvas.drawText("Net", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })


            // Table items
            yPosition += 25f


            list.forEachIndexed { index, salesInvoiceResponse ->
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
                xPosition += 15f
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                //Date
                xPosition += 60f
                canvas.drawText(
                    salesInvoiceResponse.date.stringToDateStringConverter(),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 60f
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)


                // Inv. No
                xPosition += 30
                canvas.drawText(
                    salesInvoiceResponse.invoiceNo.toString(),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })
                xPosition += 30
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                // Account
                xPosition += 120
                canvas.drawText(
                    salesInvoiceResponse.party ?: "-",
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 120
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                // Taxable
                xPosition += 45f
                canvas.drawText(
                    // posPaymentResponse.cash.toString(),
                    String.format("%.2f",salesInvoiceResponse.taxable),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 45f
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                // Tax
                xPosition += 45f
                canvas.drawText(
                    String.format("%.2f",salesInvoiceResponse.tax),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 45f
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)


                // return
                xPosition += 45f
                canvas.drawText(
                    String.format("%.2f",salesInvoiceResponse.returnTaxable),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 45f
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                // Tax on return
                xPosition += 50f
                canvas.drawText(
                    String.format("%.2f",salesInvoiceResponse.taxOnReturn),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })
                xPosition += 52.5f
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)


                xPosition += 52.5f
                canvas.drawText(
                    String.format("%.2f",salesInvoiceResponse.net),
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
                    yPosition,
                    canvas,
                    salesInvoiceReportTotals = salesInvoiceReportTotals
                )
            }

            val date = SimpleDateFormat("dd-MM-yyyy, hh:mm:ss a", Locale.getDefault()).format(Date())
            canvas.drawText(date, 30f, 680f, Paint().apply {
                color = Color.BLACK
                textSize = 8f
                textAlign = Paint.Align.LEFT
                textSkewX = -0.25f
            })

            canvas.drawText("Unipospro",pageInfo.pageWidth/2f,680f, Paint().apply {
                color = Color.BLACK
                textSize = 8f
                textAlign = Paint.Align.CENTER
            })

            canvas.drawText("page $pageNo of $totalPages", 970f, 680f, Paint().apply {
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

    private fun writeTotalValueRow(yPosition: Float, canvas: Canvas, salesInvoiceReportTotals: SalesInvoiceReportTotals) {
        canvas.drawRect(30f, yPosition, 970f, yPosition + 25f, Paint().apply {
            style = Paint.Style.STROKE
        })

        var xPosition = 480f

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
        xPosition += 45f
        canvas.drawText(
            String.format("%.2f", salesInvoiceReportTotals.sumOfTaxable),
            xPosition,
            yPosition + 16.5f,
            Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
                strokeWidth = 1f
            })
        xPosition += 45f
        canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
            color = Color.argb(255, 90, 90, 90)
            strokeWidth = 0.4f
        })

        xPosition += 45f
        canvas.drawText(
            String.format("%.2f", salesInvoiceReportTotals.sumOfTax),
            xPosition,
            yPosition + 16.5f,
            Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
                strokeWidth = 1f
            })
        xPosition += 45f
        canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
            color = Color.argb(255, 90, 90, 90)
            strokeWidth = 0.4f
        })

        xPosition += 45f
        canvas.drawText(
            String.format("%.2f", salesInvoiceReportTotals.sumOfReturn),
            xPosition,
            yPosition + 16.5f,
            Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
                strokeWidth = 1f
            })
        xPosition += 45f
        canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
            color = Color.argb(255, 90, 90, 90)
            strokeWidth = 0.4f
        })

        xPosition += 50f
        canvas.drawText(
            String.format("%.2f", salesInvoiceReportTotals.sumOfTaxOnReturn),
            xPosition,
            yPosition + 16.5f,
            Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
                strokeWidth = 1f
            })
        xPosition += 50f
        canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
            color = Color.argb(255, 90, 90, 90)
            strokeWidth = 0.4f
        })


        xPosition += 52.5f
        canvas.drawText(
            String.format("%.2f", salesInvoiceReportTotals.sumOfNet),
            xPosition,
            yPosition + 16.5f,
            Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
                strokeWidth = 1f
            })
    }



}