package com.gulfappdeveloper.projectreport.share.pdf.purchase.purchase_master_and_supplier_purchase

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.core.content.FileProvider
import com.gulfappdeveloper.projectreport.domain.models.purchase.PurchaseMastersResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.PosPaymentResponse
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.PurchaseMasterSelection
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.PurchaseMasterTotals
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

object PurchaseMasterAndSupplierPurchaseReportPdf {

    suspend fun makePdf(
        companyName: String,
        pdfDocument: PdfDocument,
        context: Context,
        list: List<PurchaseMastersResponse>,
        purchaseMasterTotals: PurchaseMasterTotals,
        purchaseMasterSelection: PurchaseMasterSelection,
        fromDate: String,
        toDate: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) {
        try {
            val totalPages = calculatePageCount(list, numberOfItemsInOnePage = 27)
            var pageCount = 1

            val pageList = mutableListOf<PurchaseMastersResponse>()

            list.forEachIndexed { index, purchaseMastersResponse ->
                pageList.add(purchaseMastersResponse)
                if ((index + 1) % 27 == 0) {

                    createPage(
                        pageNo = pageCount,
                        pdfDocument = pdfDocument,
                        list = pageList,
                        purchaseMasterTotals = purchaseMasterTotals,
                        purchaseMasterSelection = purchaseMasterSelection,
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
                        purchaseMasterTotals = purchaseMasterTotals,
                        purchaseMasterSelection = purchaseMasterSelection,
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
                        purchaseMasterTotals = purchaseMasterTotals,
                        purchaseMasterSelection = purchaseMasterSelection,
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
                        purchaseMasterTotals = purchaseMasterTotals,
                        purchaseMasterSelection = purchaseMasterSelection,
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
                        purchaseMasterTotals = purchaseMasterTotals,
                        purchaseMasterSelection = purchaseMasterSelection,
                        fromDate = fromDate,
                        toDate = toDate,
                        totalPages = totalPages,
                        haveAnyError = haveAnyError,
                        companyName = companyName
                    )
                }
            }
            val fileName = if (purchaseMasterSelection == PurchaseMasterSelection.PURCHASE_MASTER) "PurchaseMastersReport" else "SupplierPurchaseReport"
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
        list: List<PurchaseMastersResponse>,
        purchaseMasterTotals: PurchaseMasterTotals,
        purchaseMasterSelection: PurchaseMasterSelection,
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
                headingTitle = if (purchaseMasterSelection == PurchaseMasterSelection.PURCHASE_MASTER) "Purchase Masters Report" else "Supplier Purchase Report",
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
            xPosition = 42.5f
            canvas.drawText("SI", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 12.5f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Date
            xPosition += 57.5f
            canvas.drawText("Date", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 57.5f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })


            // Invoice No
            xPosition += 25f
            canvas.drawText("Inv. No", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 25f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Supplier
            xPosition += 130f
            canvas.drawText("Supplier", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 130f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Taxable
            xPosition += 40f
            canvas.drawText("Taxable", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 40f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Tax
            xPosition += 40f
            canvas.drawText("Tax", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 40f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Net
            xPosition += 40f
            canvas.drawText("Net", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 40f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Payment
            xPosition += 40f
            canvas.drawText("Payment", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 40f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Return Amount
            xPosition += 40f
            canvas.drawText("Return Amt", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 40f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Balance Amt
            xPosition += 45f
            canvas.drawText("Balance Amt", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })


            // Table items
            xPosition = 30f
            yPosition += 25f


            list.forEachIndexed { index, purchaseMasterResponse ->
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
                xPosition += 12.5f
                val printIndex = pageNo * 27 + index + 1 - 27
                //Log.d(TAG, "printIndex: $index, printItem count $printIndex")
                canvas.drawText(printIndex.toString(), xPosition, yPosition + 14.2f, Paint().apply {
                    textAlign = Paint.Align.CENTER
                    textSize = 10f
                })
                xPosition += 12.5f
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                //Date
                xPosition += 57.5f
                canvas.drawText(
                    purchaseMasterResponse.invoiceDate.stringToDateStringConverter(),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 57.5f
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)


                // Inv. No
                xPosition += 25
                canvas.drawText(
                    purchaseMasterResponse.invoiceNo.toString(),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })
                xPosition += 25
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                // Party
                xPosition += 130
                canvas.drawText(
                    purchaseMasterResponse.supplier ?: "-",
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 130
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                //Taxable
                xPosition += 40
                canvas.drawText(
                    String.format("%.2f", purchaseMasterResponse.taxable),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 40
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                // Tax
                xPosition += 40
                canvas.drawText(
                    String.format("%.2f", purchaseMasterResponse.tax),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 40
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)


                // net
                xPosition += 40
                canvas.drawText(
                    String.format("%.2f", purchaseMasterResponse.net),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 40
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                // payment
                xPosition += 40
                canvas.drawText(
                    String.format("%.2f", purchaseMasterResponse.payment),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })
                xPosition += 40
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                // Return Amount
                xPosition += 40
                canvas.drawText(
                    String.format("%.2f", purchaseMasterResponse.returnAmount),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })
                xPosition += 40
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                xPosition += 45
                canvas.drawText(
                    String.format("%.2f", purchaseMasterResponse.balanceAmount),
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
                    purchaseMasterTotals = purchaseMasterTotals
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

    private fun writeTotalValueRow(yPosition: Float, canvas: Canvas, purchaseMasterTotals: PurchaseMasterTotals) {
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
        xPosition += 40
        canvas.drawText(
            String.format("%.2f", purchaseMasterTotals.sumOfTaxable),
            xPosition,
            yPosition + 16.5f,
            Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
                strokeWidth = 1f
            })
        xPosition += 40
        canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
            color = Color.argb(255, 90, 90, 90)
            strokeWidth = 0.4f
        })

        xPosition += 40
        canvas.drawText(
            String.format("%.2f", purchaseMasterTotals.sumOfTax),
            xPosition,
            yPosition + 16.5f,
            Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
                strokeWidth = 1f
            })
        xPosition += 40
        canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
            color = Color.argb(255, 90, 90, 90)
            strokeWidth = 0.4f
        })

        xPosition += 40
        canvas.drawText(
            String.format("%.2f", purchaseMasterTotals.sumOfNet),
            xPosition,
            yPosition + 16.5f,
            Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
                strokeWidth = 1f
            })
        xPosition += 40
        canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
            color = Color.argb(255, 90, 90, 90)
            strokeWidth = 0.4f
        })

        xPosition += 40
        canvas.drawText(
            String.format("%.2f", purchaseMasterTotals.sumOfPayment),
            xPosition,
            yPosition + 16.5f,
            Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
                strokeWidth = 1f
            })
        xPosition += 40
        canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
            color = Color.argb(255, 90, 90, 90)
            strokeWidth = 0.4f
        })

        xPosition += 40
        canvas.drawText(
            String.format("%.2f", purchaseMasterTotals.sumOfReturnAmount),
            xPosition,
            yPosition + 16.5f,
            Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
                strokeWidth = 1f
            })
        xPosition += 40
        canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
            color = Color.argb(255, 90, 90, 90)
            strokeWidth = 0.4f
        })

        xPosition += 45
        canvas.drawText(
            String.format("%.2f", purchaseMasterTotals.sumOfBalanceAmount),
            xPosition,
            yPosition + 16.5f,
            Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
                strokeWidth = 1f
            })
    }


    /*private fun calculatePageCount(list: List<PosPaymentResponse>): Int {
        try {
            val lengthOfTheList = list.size
            var fullPage = lengthOfTheList / 27
            return when (lengthOfTheList % 27) {
                in 0..25 -> {
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
    }*/
}