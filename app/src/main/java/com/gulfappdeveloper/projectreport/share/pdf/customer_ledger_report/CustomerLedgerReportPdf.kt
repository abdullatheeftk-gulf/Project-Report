package com.gulfappdeveloper.projectreport.share.pdf.customer_ledger_report

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.BLACK
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.core.content.FileProvider
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.SupplierLedgerTotals
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.CustomerLedgerTotals
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.ReArrangedCustomerLedgerDetails
import com.gulfappdeveloper.projectreport.share.pdf.calculatePageCountForLedgerReports
import com.gulfappdeveloper.projectreport.share.pdf.writeCompanyName
import com.gulfappdeveloper.projectreport.share.pdf.writeHeading
import com.gulfappdeveloper.projectreport.share.pdf.writePeriodText
import com.gulfappdeveloper.projectreport.share.pdf.writePeriodTextLedger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CustomerLedgerReportPdf {

    suspend fun makePdf(
        companyName: String,
        partyName: String,
        balance: Float,
        pdfDocument: PdfDocument,
        context: Context,
        list: List<ReArrangedCustomerLedgerDetails>,
        customerLedgerDetails: CustomerLedgerTotals,
        fromDate: String,
        toDate: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) {
        try {
            val totalPages = calculatePageCountForLedgerReports(list)

            var newList = mutableListOf<ReArrangedCustomerLedgerDetails>()
            var pageNo = 1
            list.forEachIndexed { index, reArrangedCustomerLedgerDetails ->

                newList.add(reArrangedCustomerLedgerDetails)
                // Check for that first page contains only 39 items
                if ((index + 1) == 39) {
                    // again check for it is the last of the list. it is for to add total value
                    if (list.size == 39) {
                        newList = newList.dropLast(1).toMutableList()
                        createPage(
                            companyName = companyName,
                            pageNo = pageNo,
                            totalPages = totalPages,
                            fromDate = fromDate,
                            toDate = toDate,
                            haveAnyError = haveAnyError,
                            list = newList,
                            customerLedgerTotals = customerLedgerDetails,
                            pdfDocument = pdfDocument,
                            partyName = partyName,
                            balance = balance
                        )
                        newList.clear()
                        newList.add(reArrangedCustomerLedgerDetails)
                        pageNo++
                        createPage(
                            companyName = companyName,
                            pageNo = pageNo,
                            totalPages = totalPages,
                            fromDate = fromDate,
                            toDate = toDate,
                            haveAnyError = haveAnyError,
                            list = newList,
                            customerLedgerTotals = customerLedgerDetails,
                            pdfDocument = pdfDocument,
                            partyName = partyName,
                            balance = balance
                        )
                        newList.clear()
                    } else {
                        createPage(
                            companyName = companyName,
                            pageNo = pageNo,
                            totalPages = totalPages,
                            fromDate = fromDate,
                            toDate = toDate,
                            haveAnyError = haveAnyError,
                            list = newList,
                            customerLedgerTotals = customerLedgerDetails,
                            pdfDocument = pdfDocument,
                            partyName = partyName,
                            balance = balance
                        )
                        newList.clear()
                        pageNo++
                    }

                }
            }
            // Check for remaining item after full list iteration
            when (newList.size) {
                in 1..39 -> {
                    createPage(
                        companyName = companyName,
                        pageNo = pageNo,
                        totalPages = totalPages,
                        fromDate = fromDate,
                        toDate = toDate,
                        haveAnyError = haveAnyError,
                        list = newList,
                        customerLedgerTotals = customerLedgerDetails,
                        pdfDocument = pdfDocument,
                        partyName = partyName,
                        balance = balance
                    )
                }

                40 -> {
                    createPage(
                        companyName = companyName,
                        pageNo = pageNo,
                        totalPages = totalPages,
                        fromDate = fromDate,
                        toDate = toDate,
                        haveAnyError = haveAnyError,
                        list = newList,
                        customerLedgerTotals = customerLedgerDetails,
                        pdfDocument = pdfDocument,
                        partyName = partyName,
                        balance = balance
                    )
                    newList.clear()
                    pageNo++
                    createPage(
                        companyName = companyName,
                        pageNo = pageNo,
                        totalPages = totalPages,
                        fromDate = fromDate,
                        toDate = toDate,
                        haveAnyError = haveAnyError,
                        list = newList,
                        customerLedgerTotals = customerLedgerDetails,
                        pdfDocument = pdfDocument,
                        partyName = partyName,
                        balance = balance
                    )
                }

                else -> {
                    haveAnyError(true, "Error in programing")
                }
            }
            val file =
                File(context.getExternalFilesDir(null), "CustomerLedgerReport_${Date()}.pdf")
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
        partyName: String,
        balance: Float,
        pageNo: Int,
        totalPages: Int,
        fromDate: String,
        pdfDocument: PdfDocument,
        toDate: String,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<ReArrangedCustomerLedgerDetails>,
        customerLedgerTotals: CustomerLedgerTotals,
    ) {
        val isItLastPage: Boolean = pageNo == totalPages
        val isItFirstPage: Boolean = pageNo == 1

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
            headingTitle = "Customer Ledger Report",
            xPosition = xPositionHeading,
            yPosition = yPosition
        )
        if (isItFirstPage) {
            yPosition += 30f
            canvas.drawText("Party Name", 30f, yPosition, Paint().apply {
                textSize = 12f
                color = BLACK
            })
            canvas.drawText(":", 120f, yPosition, Paint().apply {
                textSize = 12f
                color = BLACK
            })
            canvas.drawText(partyName, 130f, yPosition, Paint().apply {
                textSize = 12f
                color = BLACK
            })

            yPosition += 20f
            canvas.drawText("Balance Amt", 30f, yPosition, Paint().apply {
                textSize = 12f
                color = BLACK
            })
            canvas.drawText(":", 120f, yPosition, Paint().apply {
                textSize = 12f
                color = BLACK
            })
            canvas.drawText(balance.toString(), 130f, yPosition, Paint().apply {
                textSize = 12f
                color = BLACK
            })
            yPosition += 20f
        } else {
            yPosition += 30f
        }

        // period & company name
        canvas.writePeriodTextLedger(fromDate, toDate, yPosition)
        canvas.writeCompanyName(companyName = companyName, yPosition = yPosition, xPosition = 677f)

        var xPosition = 30f
        yPosition += 8f


        // Drawing header rectangle
        canvas.drawRect(xPosition, yPosition, 677f, yPosition + 25, Paint().apply {
            color = Color.argb(255, 210, 210, 210)
            style = Paint.Style.FILL
        })
        canvas.drawRect(xPosition, yPosition, 677f, yPosition + 25, Paint().apply {
            style = Paint.Style.STROKE
            color = BLACK
        })

        // header items
        // Si
        xPosition = 44f
        canvas.drawText("SI", xPosition, yPosition + 16.5f, Paint().apply {
            textAlign = Paint.Align.CENTER
            textSize = 12f
        })

        xPosition += 14f
        canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
            color = Color.argb(255, 90, 90, 90)
            strokeWidth = 0.4f
        })

        // Date
        xPosition += 80f
        canvas.drawText("Date", xPosition, yPosition + 16.5f, Paint().apply {
            textAlign = Paint.Align.CENTER
            textSize = 12f
        })

        xPosition += 80f
        canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
            color = Color.argb(255, 90, 90, 90)
            strokeWidth = 0.4f
        })


        // Voucher No
        xPosition += 30f
        canvas.drawText("Vchr. No", xPosition, yPosition + 16.5f, Paint().apply {
            textAlign = Paint.Align.CENTER
            textSize = 12f
        })

        xPosition += 30f
        canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
            color = Color.argb(255, 90, 90, 90)
            strokeWidth = 0.4f
        })

        // Particulars
        xPosition += 100f
        canvas.drawText("Particulars", xPosition, yPosition + 16.5f, Paint().apply {
            textAlign = Paint.Align.CENTER
            textSize = 12f
        })

        xPosition += 100f
        canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
            color = Color.argb(255, 90, 90, 90)
            strokeWidth = 0.4f
        })

        // Debit
        xPosition += 50f
        canvas.drawText("Debit", xPosition, yPosition + 16.5f, Paint().apply {
            textAlign = Paint.Align.CENTER
            textSize = 12f
        })

        xPosition += 50f
        canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
            color = Color.argb(255, 90, 90, 90)
            strokeWidth = 0.4f
        })

        // Credit
        xPosition += 50f
        canvas.drawText("Credit", xPosition, yPosition + 16.5f, Paint().apply {
            textAlign = Paint.Align.CENTER
            textSize = 12f
        })


        // Table items
        yPosition += 25f

        list.forEachIndexed { index, customerLedgerReport ->
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


            //items

            xPosition += 14f
            val printIndex = customerLedgerReport.si
            //Log.d(TAG, "printIndex: $index, printItem count $printIndex")
            canvas.drawText(printIndex.toString(), xPosition, yPosition + 14.2f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 10f
            })
            xPosition += 14f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

            //Date
            xPosition += 80f
            canvas.drawText(
                customerLedgerReport.voucherDate,
                xPosition,
                yPosition + 14.2f,
                Paint().apply {
                    textAlign = Paint.Align.CENTER
                    textSize = 10f
                })

            xPosition += 80f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

            // voucher No
            xPosition += 30
            canvas.drawText(
                customerLedgerReport.voucherNo.toString(),
                xPosition,
                yPosition + 14.2f,
                Paint().apply {
                    textAlign = Paint.Align.CENTER
                    textSize = 10f
                })
            xPosition += 30
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

            // Particulars
            xPosition += 100
            canvas.drawText(
                customerLedgerReport.particulars,
                xPosition,
                yPosition + 14.2f,
                Paint().apply {
                    textAlign = Paint.Align.CENTER
                    textSize = 10f
                })

            xPosition += 100
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

            //debit
            xPosition += 50
            canvas.drawText(
                // posPaymentResponse.cash.toString(),
                String.format("%.2f", customerLedgerReport.debit),
                xPosition,
                yPosition + 14.2f,
                Paint().apply {
                    textAlign = Paint.Align.CENTER
                    textSize = 10f
                })

            xPosition += 50
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

            //credit
            xPosition += 50
            canvas.drawText(
                // posPaymentResponse.cash.toString(),
                String.format("%.2f", customerLedgerReport.credit),
                xPosition,
                yPosition + 14.2f,
                Paint().apply {
                    textAlign = Paint.Align.CENTER
                    textSize = 10f
                })



            yPosition += 20f
        }
        if (isItLastPage) {
            writeTotalValueRow(
                yPosition = yPosition,
                canvas = canvas,
                customerLedgerTotals = customerLedgerTotals
            )
        }


        val date = SimpleDateFormat("dd-MM-yyyy, hh:mm:ss a", Locale.getDefault()).format(Date())
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

    }


    private fun writeTotalValueRow(
        yPosition: Float,
        canvas: Canvas,
        customerLedgerTotals: CustomerLedgerTotals
    ) {
        canvas.drawRect(30f, yPosition, 677f, yPosition + 25f, Paint().apply {
            style = Paint.Style.STROKE
        })

        var xPosition = 478f

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
        xPosition += 50
        canvas.drawText(
            String.format("%.2f", customerLedgerTotals.sumOfDebit),
            xPosition,
            yPosition + 16.5f,
            Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
                strokeWidth = 1f
            })
        xPosition += 50
        canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
            color = Color.argb(255, 90, 90, 90)
            strokeWidth = 0.4f
        })

        xPosition += 50
        canvas.drawText(
            String.format("%.2f", customerLedgerTotals.sumOfCredit),
            xPosition,
            yPosition + 16.5f,
            Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
                strokeWidth = 1f
            })

    }


    /* fun calculatePageCountForLedgerReports(
         list: List<ReArrangedCustomerLedgerDetails>,
     ): Int {
         try {
             val newList = mutableListOf<Int>()
             var pageCount = 0
             list.forEachIndexed{index,_->
                 newList.add(index)
                 if ((index+1)==39){
                     if (list.size==39){
                         pageCount+=2
                         newList.clear()
                     }else{
                         pageCount++
                         newList.clear()
                     }
                 }
                 if ((index-38)%41==0 && (index-38)!=0){
                     if (list.size == (index+1)){
                         pageCount+=2
                         newList.clear()
                     }else{
                         pageCount++
                         newList.clear()
                     }
                 }
             }

             when(newList.size){
                 in 1..39->{
                    pageCount++
                 }
                 40->{
                     pageCount+=2
                 }
                 else->Unit
             }
             return  pageCount
         } catch (e: Exception) {
             throw Exception(e.message)
         }
     }*/


}