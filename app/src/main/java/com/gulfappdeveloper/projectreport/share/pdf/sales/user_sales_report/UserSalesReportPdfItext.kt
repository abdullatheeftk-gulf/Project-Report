package com.gulfappdeveloper.projectreport.share.pdf.sales.user_sales_report

/*
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.gulfappdeveloper.projectreport.domain.models.sales.UserSalesResponse
import com.itextpdf.kernel.colors.Color
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.colors.DeviceGray
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.geom.Rectangle
import com.itextpdf.kernel.pdf.EncryptionConstants
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.WriterProperties
import com.itextpdf.kernel.pdf.canvas.PdfCanvas
import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace
import com.itextpdf.layout.Canvas
import com.itextpdf.layout.Document
import com.itextpdf.layout.Style
import com.itextpdf.layout.borders.Border
import com.itextpdf.layout.element.AreaBreak
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Div
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.element.Text
import com.itextpdf.layout.properties.HorizontalAlignment
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import com.itextpdf.layout.properties.VerticalAlignment
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val TAG = "UserSalesReportPdfItext"

object UserSalesReportPdfItext {

    fun writePdf(
        context: Context,
        fromDate: String,
        toDate: String,
        companyName: String,
        list: List<UserSalesResponse>,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) {
        try {
            val file =
                File(context.getExternalFilesDir(null), "UserSalesReport_${Date()}.pdf")
            val fileOutputStream = FileOutputStream(file)
            val userPassword = "12345678"
            val ownerPassword = "9526317685"
            val writer = PdfWriter(
                fileOutputStream,
                */
/*WriterProperties().setStandardEncryption(
                    userPassword.toByteArray(),
                    ownerPassword.toByteArray(),
                    EncryptionConstants.ALLOW_PRINTING,
                    EncryptionConstants.ENCRYPTION_AES_256
                )*//*

            )
            val pdf = PdfDocument(writer)


            val document = Document(pdf)
            val rectangle = document.getPageEffectiveArea(PageSize.A4)
            val width = rectangle.width
            val height = rectangle.height
            Log.e(TAG, "writePdf: $width, $height")
            document.setMargins(20f, 12f, 20f, 12f)

            val headingTable = Table(1).apply {
                useAllAvailableWidth()
                addCell(Cell().apply {
                    setFontSize(14f)
                    setTextAlignment(TextAlignment.CENTER)
                    setBorder(Border.NO_BORDER)
                    setFontColor(ColorConstants.BLUE)
                }.add(Paragraph("User Sales Report")))
            }
            document.add(headingTable)


            val periodTable = Table(2)
            val period = Text("Period:- ")
            val fromD = Text(fromDate).setFontColor(ColorConstants.BLUE)
            val to = Text(" to ")
            val toD =Text(toDate).setFontColor(ColorConstants.BLUE)
            val periodParagraph = Paragraph().setFontSize(8f)
            periodParagraph.apply {
                add(period)
                add(fromD)
                add(to)
                add(toD)
            }
            periodTable.setBorder(Border.NO_BORDER)
                periodTable.apply {
                useAllAvailableWidth()

                addCell(Cell().apply {
                    setTextAlignment(TextAlignment.LEFT)
                    setFontSize(8f)
                    setBorder(Border.NO_BORDER)
                }.add(periodParagraph))
                addCell(Cell().apply {
                    setTextAlignment(TextAlignment.RIGHT)
                    setFontSize(8f)
                    setBorder(Border.NO_BORDER)
                }.add(Paragraph(companyName)))
            }


            document.add(periodTable)


            // create Table
            val table = Table(3).apply {
                addCell(Cell().apply {
                    setTextAlignment(TextAlignment.CENTER)
                    setFontSize(10f)
                    setBackgroundColor(ColorConstants.LIGHT_GRAY)
                }.add(Paragraph("Si")))
                addCell(Cell().apply {
                    setFontSize(10f)
                    setTextAlignment(TextAlignment.CENTER)
                    setBackgroundColor(ColorConstants.LIGHT_GRAY)
                }.add(Paragraph("User Name")))
                addCell(Cell().apply {
                    setFontSize(10f)
                    setTextAlignment(TextAlignment.CENTER)
                    setBackgroundColor(ColorConstants.LIGHT_GRAY)
                }.add(Paragraph("Sales Amount")))
            }
            table.setHorizontalAlignment(HorizontalAlignment.CENTER)
            table.useAllAvailableWidth()

            var sum = 0.0
            list.forEachIndexed { index, userSalesResponse ->

                sum += userSalesResponse.salesAmount
                table.addCell(Cell().apply {
                    setTextAlignment(TextAlignment.CENTER)
                    setFontSize(10f)
                }.add(Paragraph((index + 1).toString())))
                table.addCell(Cell().apply {
                    setTextAlignment(TextAlignment.CENTER)
                    setFontSize(10f)
                }.add(Paragraph(userSalesResponse.userName)))
                table.addCell(Cell().apply {
                    setTextAlignment(TextAlignment.CENTER)
                    setFontSize(10f)
                }.add(Paragraph(userSalesResponse.salesAmount.toString())))
            }
            table.addCell(Cell(1, 2).apply {
                setTextAlignment(TextAlignment.RIGHT)
                setFontSize(10f)
            }.add(Paragraph("Total:- ")))

            table.addCell(
                Cell().apply {
                    setTextAlignment(TextAlignment.CENTER)
                    setFontSize(10f)
                }.add(Paragraph(sum.toString()))
            )

            document.add(table)



            document.add(AreaBreak())
            document.add(Paragraph(Text("Latheef tk")))


            val totalPages = pdf.numberOfPages
            for (pageNo in 1..totalPages){
                val page =pdf.getPage(pageNo)
                val pageSize = page.pageSize
                val pdfCanvas = PdfCanvas(page)
                val footer = Paragraph("Unipospro")
                    .setFontSize(7f)
                    .setTextAlignment(TextAlignment.CENTER)

                // Create a canvas and position it at the bottom of the page
                val canvas = Canvas(pdfCanvas, pageSize)

                // Set the position of the footer on the page
                canvas.showTextAligned(
                    footer,
                    pageSize.width / 2,
                    pageSize.bottom + 20,
                    pageNo,
                    TextAlignment.CENTER,
                    VerticalAlignment.BOTTOM,
                    0f
                )

                canvas.showTextAligned(
                    Paragraph(SimpleDateFormat("dd/MM/yyyy hh:MM:ss a", Locale.getDefault()).format(Date())).setFontSize(7f),
                    15f,
                    pageSize.bottom + 20,
                    pageNo,
                    TextAlignment.LEFT,
                    VerticalAlignment.BOTTOM,
                    0f
                )

                canvas.showTextAligned(
                    Paragraph("Page $pageNo of $totalPages").setFontSize(7f),
                    pageSize.width-15f,
                    pageSize.bottom + 20,
                    pageNo,
                    TextAlignment.RIGHT,
                    VerticalAlignment.BOTTOM,
                    0f
                )

                // Close the canvas
                canvas.close()

            }

            document.close()

            val uri = FileProvider.getUriForFile(context, context.packageName, file)

            getUri(uri)

            haveAnyError(false, null)
        } catch (e: Exception) {
            haveAnyError(true, e.message)
        }


    }
}*/
