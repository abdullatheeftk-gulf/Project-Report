package com.gulfappdeveloper.projectreport.share.pdf.purchase.purchase_summary_report

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.core.content.FileProvider
import com.gulfappdeveloper.projectreport.domain.models.purchase.PurchaseSummaryResponse
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.PurchaseSummaryTotals
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

object PurchaseSummaryReportPdf {

    suspend fun makePdf(
        companyName: String,
        pdfDocument: PdfDocument,
        context: Context,
        list: List<PurchaseSummaryResponse>,
        purchaseSummaryTotals: PurchaseSummaryTotals,
        fromDate: String,
        toDate: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) {
        try {
            val totalPages = calculatePageCount(list, numberOfItemsInOnePage = 27)
            var pageCount = 1

            val pageList = mutableListOf<PurchaseSummaryResponse>()

            list.forEachIndexed { index, purchaseSummaryResponse ->
                pageList.add(purchaseSummaryResponse)
                if ((index + 1) % 27 == 0) {

                    createPage(
                        pageNo = pageCount,
                        pdfDocument = pdfDocument,
                        list = pageList,
                        purchaseSummaryTotals = purchaseSummaryTotals,
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
                in 1..25 -> {
                    createPage(
                        pageNo = pageCount,
                        pdfDocument = pdfDocument,
                        list = pageList,
                        purchaseSummaryTotals = purchaseSummaryTotals,
                        fromDate = fromDate,
                        toDate = toDate,
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
                        purchaseSummaryTotals = purchaseSummaryTotals,
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
                        purchaseSummaryTotals = purchaseSummaryTotals,
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
                        purchaseSummaryTotals = purchaseSummaryTotals,
                        fromDate = fromDate,
                        toDate = toDate,
                        totalPages = totalPages,
                        haveAnyError = haveAnyError,
                        companyName = companyName
                    )
                }
            }
            val fileName = "PurchaseSummaryReport"
            val file =
                File(context.getExternalFilesDir(null), "${fileName}_${Date()}.pdf")
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
        list: List<PurchaseSummaryResponse>,
        purchaseSummaryTotals: PurchaseSummaryTotals,
        fromDate: String,
        toDate: String,
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
                headingTitle = "Purchase Summary Report",
                xPosition = xPositionHeading,
                yPosition = yPosition
            )

            // dates
            yPosition += 30f
            canvas.writePeriodText(fromDate, toDate, yPosition)
            canvas.writeCompanyName(
                companyName = companyName,
                yPosition = yPosition,
                xPosition = 970f
            )


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
            xPosition += 75f
            canvas.drawText("Date", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 75f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })


            // Invoice No
            xPosition += 40f
            canvas.drawText("Inv. No", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 40f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Supplier
            xPosition += 100f
            canvas.drawText("Supplier", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 100f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Tax Id
            xPosition += 60f
            canvas.drawText("Tax Id", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 60f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Taxable
            xPosition += 60f
            canvas.drawText("Taxable", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 60f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Tax
            xPosition += 60f
            canvas.drawText("Tax", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 60f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })


            // Net
            xPosition += 60f
            canvas.drawText("Net", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })


            // Table items
            xPosition = 30f
            yPosition += 25f


            list.forEachIndexed { index, purchaseSummaryResponse ->
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
                xPosition += 75f
                canvas.drawText(
                    purchaseSummaryResponse.invoiceDate.stringToDateStringConverter(),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 75f
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)


                // Inv. No
                xPosition += 40
                canvas.drawText(
                    purchaseSummaryResponse.invoiceNo.toString(),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })
                xPosition += 40
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                // Party
                xPosition += 100
                canvas.drawText(
                    purchaseSummaryResponse.supplier ?: "-",
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 100
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                //Tax id
                xPosition += 60
                canvas.drawText(
                    purchaseSummaryResponse.taxId ?: "-",
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 60
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                // Taxable
                xPosition += 60
                canvas.drawText(
                    String.format("%.2f", purchaseSummaryResponse.taxable),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 60
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)


                // tax
                xPosition += 60
                canvas.drawText(
                    String.format("%.2f", purchaseSummaryResponse.tax),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 60
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)


                // net
                xPosition += 60
                canvas.drawText(
                    String.format("%.2f", purchaseSummaryResponse.net),
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
                    purchaseSummaryTotals = purchaseSummaryTotals
                )
            }

            val date =
                SimpleDateFormat("dd-MM-yyyy, hh:mm:ss a", Locale.getDefault()).format(Date())
            canvas.drawText(date, 30f, 680f, Paint().apply {
                color = Color.BLACK
                textSize = 8f
                textAlign = Paint.Align.LEFT
                textSkewX = -0.25f
            })

            canvas.drawText("Unipospro", pageInfo.pageWidth / 2f, 680f, Paint().apply {
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

    private fun writeTotalValueRow(
        yPosition: Float,
        canvas: Canvas,
        purchaseSummaryTotals: PurchaseSummaryTotals
    ) {
        canvas.drawRect(30f, yPosition, 970f, yPosition + 25f, Paint().apply {
            style = Paint.Style.STROKE
        })

        var xPosition = 610f

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
        xPosition += 60
        canvas.drawText(
            String.format("%.2f", purchaseSummaryTotals.sumOfTaxable),
            xPosition,
            yPosition + 16.5f,
            Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
                strokeWidth = 1f
            })
        xPosition += 60
        canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
            color = Color.argb(255, 90, 90, 90)
            strokeWidth = 0.4f
        })

        xPosition += 60
        canvas.drawText(
            String.format("%.2f", purchaseSummaryTotals.sumOfTax),
            xPosition,
            yPosition + 16.5f,
            Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
                strokeWidth = 1f
            })
        xPosition += 60
        canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
            color = Color.argb(255, 90, 90, 90)
            strokeWidth = 0.4f
        })


        //Sum of net
        xPosition += 60
        canvas.drawText(
            String.format("%.2f", purchaseSummaryTotals.sumOfNet),
            xPosition,
            yPosition + 16.5f,
            Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
                strokeWidth = 1f
            })
    }
}