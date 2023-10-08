package com.gulfappdeveloper.projectreport.share.excel.sales.customer_payment_report

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import com.gulfappdeveloper.projectreport.root.stringToDateStringConverter
import com.gulfappdeveloper.projectreport.share.excel.createHeading
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFFont
import org.apache.poi.xssf.usermodel.XSSFRichTextString
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Date
import java.util.Locale

object CustomerPaymentReportExcel {

    suspend fun writeExcelSheet(
        context: Context,
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime:String,
        getUri: (Uri) -> Unit,
        list: List<CustomerPaymentResponse>,
        companyName:String,
        haveAnyError: (isError: Boolean, errorString: String?) -> Unit
    ) = try {
        val wb = XSSFWorkbook()
        val currentDateAndTime = SimpleDateFormat("dd-MM-yyyy hh;mm;ss a", Locale.getDefault()).format(Date())
        val sheet = wb.createSheet(currentDateAndTime)
        //createHeading(sheet, wb)
        sheet.createHeading(wb = wb, headingTitle = "Customer Payment Report", noOfColumns = 9)
        createPeriodTitle(
            sheet = sheet,
            wb = wb,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            companyName = companyName
        )
        createHeaderOfTable(sheet = sheet, wb = wb)



        val sizeOfList = list.size
        list.forEachIndexed { index, customerPaymentResponse ->
            createItemRow(
                customerPaymentResponse = customerPaymentResponse,
                index = index,
                isItLastRow = sizeOfList == index + 1,
                sheet = sheet,
                wb = wb
            )
        }

        createTotalValueRow(sheet,wb,sizeOfList+3)



        sheet.setColumnWidth(0, 5 * 200)
        sheet.setColumnWidth(1, 35 * 200)
        sheet.setColumnWidth(2, 15 * 200)
        sheet.setColumnWidth(3, 60 * 200)
        sheet.setColumnWidth(4, 16 * 200)
        sheet.setColumnWidth(5, 16 * 200)
        sheet.setColumnWidth(6, 16 * 200)
        sheet.setColumnWidth(7, 16 * 200)
        sheet.setColumnWidth(8, 16 * 200)

        val file = File(context.getExternalFilesDir(null), "CustomerPaymentReport_${Date().time}.xlsx")

        withContext(Dispatchers.IO) {
            val fileOutputStream = FileOutputStream(file)
            try {
                wb.write(fileOutputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                try {
                    fileOutputStream.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        val uri = FileProvider.getUriForFile(context, context.packageName, file)

        getUri(uri)

        haveAnyError(false, null)
    }catch (e:Exception){
        haveAnyError(true, e.message)
    }


    private fun createPeriodTitle(
        sheet: Sheet,
        wb: XSSFWorkbook,
        fromDate: String,
        fromTime:String,
        toDate: String,
        toTime: String,
        companyName: String
    ) {
        val periodRow = sheet.createRow(1)
        val font = wb.createFont().apply {
            color = IndexedColors.BLUE.index
        }

        val richTextString = XSSFRichTextString("Period : $fromDate, $fromTime to $toDate, $toTime").apply {
            val lengthOfFromDate = "$fromDate, $fromTime".length
            val totalLength = "Period : $fromDate, $fromTime to $toDate, $toTime".length - 1

            applyFont(9, 9 + lengthOfFromDate, font)
            applyFont(13 + lengthOfFromDate, totalLength + 1, font)
        }
        periodRow.createCell(0).apply {
            setCellValue(richTextString)
        }
        periodRow.createCell(8).apply {
            setCellValue("Company: $companyName")
            cellStyle = wb.createCellStyle().apply {
                alignment = HorizontalAlignment.RIGHT
            }
        }
    }

    private fun createHeaderOfTable(sheet: Sheet, wb: XSSFWorkbook) {

        val generalHeaderCellStyle = wb.createCellStyle().apply {
            fillForegroundColor = IndexedColors.GOLD.index
            fillPattern = FillPatternType.SOLID_FOREGROUND
            borderRight = BorderStyle.THIN
            borderBottom = BorderStyle.THIN
            borderTop = BorderStyle.MEDIUM
            alignment = HorizontalAlignment.CENTER

        }
        val headerRow = sheet.createRow(2)


        headerRow.createCell(0).apply {
            setCellValue("SI")
            cellStyle = wb.createCellStyle().apply {
                fillForegroundColor = IndexedColors.GOLD.index
                fillPattern = FillPatternType.SOLID_FOREGROUND
                borderRight = BorderStyle.THIN
                borderBottom = BorderStyle.THIN
                borderTop = BorderStyle.MEDIUM
                borderLeft = BorderStyle.MEDIUM
                alignment = HorizontalAlignment.CENTER

            }
        }
        headerRow.createCell(1).apply {
            setCellValue("Date")
            cellStyle = generalHeaderCellStyle
        }
        headerRow.createCell(2).apply {
            setCellValue("Receipt No")
            cellStyle = generalHeaderCellStyle
        }
        headerRow.createCell(3).apply {
            setCellValue("Party")
            cellStyle = generalHeaderCellStyle
        }
        headerRow.createCell(4).apply {
            setCellValue("Cash")
            cellStyle = generalHeaderCellStyle
        }
        headerRow.createCell(5).apply {
            setCellValue("Card")
            cellStyle = generalHeaderCellStyle
        }
        headerRow.createCell(6).apply {
            setCellValue("Online")
            cellStyle = generalHeaderCellStyle
        }
        headerRow.createCell(7).apply {
            setCellValue("Credit")
            cellStyle = generalHeaderCellStyle
        }
        headerRow.createCell(8).apply {
            setCellValue("Total")
            cellStyle = wb.createCellStyle().apply {
                fillForegroundColor = IndexedColors.GOLD.index
                fillPattern = FillPatternType.SOLID_FOREGROUND
                borderRight = BorderStyle.MEDIUM
                borderBottom = BorderStyle.THIN
                borderTop = BorderStyle.MEDIUM
                alignment = HorizontalAlignment.CENTER
            }
        }


    }

    private fun createItemRow(
        customerPaymentResponse: CustomerPaymentResponse,
        index: Int,
        isItLastRow: Boolean,
        sheet: Sheet,
        wb: XSSFWorkbook
    ) {
        val generalHeaderCellStyle = wb.createCellStyle().apply {
            /*fillForegroundColor = IndexedColors.GOLD.index
            fillPattern = FillPatternType.SOLID_FOREGROUND*/
            borderRight = BorderStyle.THIN
            borderBottom = if (isItLastRow) BorderStyle.MEDIUM else BorderStyle.THIN
            alignment = HorizontalAlignment.CENTER

        }
        val headerRow = sheet.createRow(index + 3)


        headerRow.createCell(0).apply {
            setCellValue((index + 1).toString())
            cellStyle = wb.createCellStyle().apply {
                /*fillForegroundColor = IndexedColors.GOLD.index
                fillPattern = FillPatternType.SOLID_FOREGROUND*/
                borderRight = BorderStyle.THIN
                borderBottom = if (isItLastRow) BorderStyle.MEDIUM else BorderStyle.THIN
                borderLeft = BorderStyle.MEDIUM
                alignment = HorizontalAlignment.CENTER

            }
        }
        headerRow.createCell(1).apply {
            setCellValue(customerPaymentResponse.date.stringToDateStringConverter())
            cellStyle = generalHeaderCellStyle
        }
        headerRow.createCell(2).apply {
            setCellValue(customerPaymentResponse.receiptNo.toString())
            cellStyle = generalHeaderCellStyle
        }
        headerRow.createCell(3).apply {
            setCellValue(customerPaymentResponse.party)
            cellStyle = generalHeaderCellStyle
        }
        headerRow.createCell(4).apply {
            setCellValue(customerPaymentResponse.cash.toDouble())
            cellStyle = generalHeaderCellStyle
        }
        headerRow.createCell(5).apply {
            setCellValue(customerPaymentResponse.card.toDouble())
            cellStyle = generalHeaderCellStyle
        }
        headerRow.createCell(6).apply {
            setCellValue(customerPaymentResponse.onlineAmount.toDouble())
            cellStyle = generalHeaderCellStyle
        }
        headerRow.createCell(7).apply {
            setCellValue(customerPaymentResponse.credit.toDouble())
            cellStyle = generalHeaderCellStyle
        }
        headerRow.createCell(8).apply {
            setCellValue(customerPaymentResponse.total.toDouble())
            cellStyle = wb.createCellStyle().apply {
                borderRight = BorderStyle.MEDIUM
                borderBottom = if (isItLastRow) BorderStyle.MEDIUM else BorderStyle.THIN
                alignment = HorizontalAlignment.CENTER
                dataFormat = wb.createDataFormat().getFormat("0.00")
            }
        }

    }

    private fun createTotalValueRow(sheet: Sheet, wb: XSSFWorkbook, rowIndex: Int) {
        val totalRow = sheet.createRow(rowIndex).apply {
            height = (25 * 20).toShort()

        }

        totalRow.createCell(4).apply {
            cellFormula = "Sum(E4:E$rowIndex)"
            cellStyle = wb.createCellStyle().apply {
                val font = wb.createFont().apply {
                    fontHeight = (14*20).toShort()
                    bold = true
                    color = IndexedColors.BLUE.index
                }
                setFont(font)
                borderBottom = BorderStyle.MEDIUM
                borderLeft = BorderStyle.THIN
                alignment = HorizontalAlignment.CENTER
                verticalAlignment = VerticalAlignment.CENTER

            }
        }

        totalRow.createCell(5).apply {
            cellFormula = "Sum(F4:F$rowIndex)"
            cellStyle = wb.createCellStyle().apply {
                val font = wb.createFont().apply {
                    fontHeight = (14*20).toShort()
                    bold = true
                    color = IndexedColors.BLUE.index
                }
                setFont(font)
                borderBottom = BorderStyle.MEDIUM
                borderLeft = BorderStyle.THIN
                alignment = HorizontalAlignment.CENTER
                verticalAlignment = VerticalAlignment.CENTER
            }
        }
        totalRow.createCell(6).apply {
            cellFormula = "Sum(G4:G$rowIndex)"
            cellStyle = wb.createCellStyle().apply {
                val font = wb.createFont().apply {
                    fontHeight = (14*20).toShort()
                    bold = true
                    color = IndexedColors.BLUE.index
                }
                setFont(font)
                borderBottom = BorderStyle.MEDIUM
                borderLeft = BorderStyle.THIN
                alignment = HorizontalAlignment.CENTER
                verticalAlignment = VerticalAlignment.CENTER
            }
        }
        totalRow.createCell(7).apply {
            cellFormula = "Sum(H4:H$rowIndex)"
            cellStyle = wb.createCellStyle().apply {
                val font = wb.createFont().apply {
                    fontHeight = (14*20).toShort()
                    bold = true
                    color = IndexedColors.BLUE.index
                }
                setFont(font)
                borderBottom = BorderStyle.MEDIUM
                borderLeft = BorderStyle.THIN
                alignment = HorizontalAlignment.CENTER
                verticalAlignment = VerticalAlignment.CENTER
            }
        }
        totalRow.createCell(8).apply {
            cellFormula = "Sum(I4:I$rowIndex)"
            cellStyle = wb.createCellStyle().apply {
                val font = wb.createFont().apply {
                    fontHeight = (14*20).toShort()
                    bold = true
                    color = IndexedColors.BLUE.index
                }
                setFont(font)
                borderBottom = BorderStyle.MEDIUM
                borderLeft = BorderStyle.THIN
                borderRight = BorderStyle.MEDIUM
                alignment = HorizontalAlignment.CENTER
                verticalAlignment = VerticalAlignment.CENTER
            }
        }
        sheet.addMergedRegion(CellRangeAddress(rowIndex, rowIndex, 0, 3))
        totalRow.createCell(0).apply {
            setCellValue("TOTAL:- ")
            cellStyle = wb.createCellStyle().apply {
                alignment = HorizontalAlignment.RIGHT
                verticalAlignment = VerticalAlignment.CENTER
                val font = wb.createFont().apply {
                    fontHeight = 280
                    color = IndexedColors.BLUE.index
                    bold = true
                } as XSSFFont
                setFont(font)
                borderBottom = BorderStyle.MEDIUM
                borderLeft = BorderStyle.MEDIUM

            }
        }
        totalRow.createCell(1).apply {

            cellStyle = wb.createCellStyle().apply {

                borderBottom = BorderStyle.MEDIUM

            }
        }
        totalRow.createCell(2).apply {

            cellStyle = wb.createCellStyle().apply {

                borderBottom = BorderStyle.MEDIUM

            }
        }
        totalRow.createCell(3).apply {

            cellStyle = wb.createCellStyle().apply {

                borderBottom = BorderStyle.MEDIUM

            }
        }
    }


}