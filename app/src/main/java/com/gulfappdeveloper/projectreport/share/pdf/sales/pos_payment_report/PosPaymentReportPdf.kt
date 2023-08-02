package com.gulfappdeveloper.projectreport.share.pdf.sales.pos_payment_report

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.gulfappdeveloper.projectreport.domain.models.sales.PosPaymentResponse
import com.gulfappdeveloper.projectreport.root.stringToDateStringConverter
import com.gulfappdeveloper.projectreport.share.pdf.calculatePageCount
import com.gulfappdeveloper.projectreport.share.pdf.writeCompanyName
import com.gulfappdeveloper.projectreport.share.pdf.writeHeading
import com.gulfappdeveloper.projectreport.share.pdf.writePeriodText
/*import com.itextpdf.kernel.pdf.CompressionConstants
import com.itextpdf.kernel.pdf.EncryptionConstants
import com.itextpdf.kernel.pdf.EncryptionProperties
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.WriterProperties
import com.itextpdf.layout.Document*/

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val TAG = "PosPaymentReportPdf"
object PosPaymentReportPdf {

    suspend fun makePdf(
        companyName:String,
        pdfDocument: PdfDocument,
        context: Context,
        list: List<PosPaymentResponse>,
        listOfTotal: List<Double>,
        fromDate: String,
        toDate: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) {
        try {
            val totalPages = calculatePageCount(list, numberOfItemsInOnePage = 27)
            var pageCount = 1

            val pageList = mutableListOf<PosPaymentResponse>()

            list.forEachIndexed { index, customerPaymentResponse ->
                pageList.add(customerPaymentResponse)
                if ((index + 1) % 27 == 0) {

                    createPage(
                        pageNo = pageCount,
                        pdfDocument = pdfDocument,
                        list = pageList,
                        listOfTotal = listOfTotal,
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
                        listOfTotal = listOfTotal,
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
                        listOfTotal = listOfTotal,
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
                        listOfTotal = listOfTotal,
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
                        listOfTotal = listOfTotal,
                        fromDate = fromDate,
                        toDate = toDate,
                        totalPages = totalPages,
                        haveAnyError = haveAnyError,
                        companyName = companyName
                    )
                }
            }
            val file =
                File(context.getExternalFilesDir(null), "PosPaymentReport_${Date()}.pdf")
            try {
                withContext(Dispatchers.IO) {
                    pdfDocument.writeTo(FileOutputStream(file))
                }
            } catch (e: IOException) {
                haveAnyError(true, e.message)
            }

           /* val outPutFile =
                File(context.getExternalFilesDir(null), "modPosPaymentReport_${Date()}.pdf")
            val inputFile = File(file.path)
            withContext(Dispatchers.IO) {



                val reader = PdfReader(inputFile.absolutePath)
                val fos = FileOutputStream(outPutFile)
                val pdfWriter = PdfWriter(
                    fos,WriterProperties().setStandardEncryption("12345678".toByteArray(),"789456123".toByteArray(),EncryptionConstants.ALLOW_PRINTING,EncryptionConstants.STANDARD_ENCRYPTION_128)
                )

                val pdfDoc = com.itextpdf.kernel.pdf.PdfDocument(reader, pdfWriter)
                pdfWriter.compressionLevel = CompressionConstants.BEST_COMPRESSION

                pdfDoc.close()
                fos.close()

            }*/

            pdfDocument.close()

            val uri = FileProvider.getUriForFile(context, context.packageName, file)

            getUri(uri)

            haveAnyError(false, null)
        } catch (e: Exception) {
            Log.e(TAG, "makePdf: ${e.message}", )
            haveAnyError(true, e.message)
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "makePdf: ${e.message}", )
            haveAnyError(true, e.message)
        }
    }


    private fun createPage(
        companyName:String,
        pageNo: Int,
        pdfDocument: PdfDocument,
        list: List<PosPaymentResponse>,
        listOfTotal: List<Double>,
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
                headingTitle = "Pos Payment Report",
                xPosition = xPositionHeading,
                yPosition = yPosition
            )

            // dates
            yPosition += 30f
            canvas.writePeriodText(fromDate, toDate, yPosition)
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
            xPosition += 65f
            canvas.drawText("Date", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 65f
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

            // Party
            xPosition += 122.5f
            canvas.drawText("Party", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 122.5f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Cash
            xPosition += 40f
            canvas.drawText("Cash", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 40f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Card
            xPosition += 40f
            canvas.drawText("Card", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 40f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Online
            xPosition += 40f
            canvas.drawText("Online", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })

            xPosition += 40f
            canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 25, Paint().apply {
                color = Color.argb(255, 90, 90, 90)
                strokeWidth = 0.4f
            })

            // Credit
            xPosition += 40f
            canvas.drawText("Credit", xPosition, yPosition + 16.5f, Paint().apply {
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

            // Total
            xPosition += 45f
            canvas.drawText("Total", xPosition, yPosition + 16.5f, Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 12f
            })


            // Table items
            xPosition = 30f
            yPosition += 25f


            list.forEachIndexed { index, posPaymentResponse ->
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
                xPosition += 65f
                canvas.drawText(
                    posPaymentResponse.date.stringToDateStringConverter(),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 65f
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)


                // Rec. No
                xPosition += 25
                canvas.drawText(
                    posPaymentResponse.invoiceNo.toString(),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })
                xPosition += 25
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                // Party
                xPosition += 122.5f
                canvas.drawText(
                    posPaymentResponse.party ?: "-",
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 122.5f
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                //cash
                xPosition += 40
                canvas.drawText(
                   // posPaymentResponse.cash.toString(),
                    String.format("%.2f",posPaymentResponse.cash),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 40
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                // Card
                xPosition += 40
                canvas.drawText(
                    String.format("%.2f",posPaymentResponse.card),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 40
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)


                // online
                xPosition += 40
                canvas.drawText(
                    String.format("%.2f",posPaymentResponse.onlineAmount),
                    xPosition,
                    yPosition + 14.2f,
                    Paint().apply {
                        textAlign = Paint.Align.CENTER
                        textSize = 10f
                    })

                xPosition += 40
                canvas.drawLine(xPosition, yPosition, xPosition, yPosition + 20, paintTable)

                // credit
                xPosition += 40
                canvas.drawText(
                    String.format("%.2f",posPaymentResponse.credit),
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
                    String.format("%.2f",posPaymentResponse.returnAmount),
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
                    String.format("%.2f",posPaymentResponse.total),
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
                    listOfTotal = listOfTotal
                )
            }

            val date = SimpleDateFormat("dd-MM-yyyy, hh:mm:ss a", Locale.getDefault()).format(Date())
            canvas.drawText(date, 30f, 680f, Paint().apply {
                color = Color.BLACK
                textSize = 8f
                textAlign = Paint.Align.LEFT
                textSkewX = -0.25f
            })

            canvas.drawText("Unipospro",pageInfo.pageWidth/2f,680f,Paint().apply {
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

    private fun writeTotalValueRow(yPosition: Float, canvas: Canvas, listOfTotal: List<Double>) {
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
            String.format("%.2f", listOfTotal[0]),
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
            String.format("%.2f", listOfTotal[1]),
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
            String.format("%.2f", listOfTotal[2]),
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
            String.format("%.2f", listOfTotal[3]),
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
            String.format("%.2f", listOfTotal[4]),
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
            String.format("%.2f", listOfTotal[5]),
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