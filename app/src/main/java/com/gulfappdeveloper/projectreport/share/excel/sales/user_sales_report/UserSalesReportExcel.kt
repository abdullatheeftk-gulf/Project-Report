package com.gulfappdeveloper.projectreport.share.excel.sales.user_sales_report

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.gulfappdeveloper.projectreport.domain.models.sales.PosPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.UserSalesResponse
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
import java.util.Date
import java.util.Locale

object UserSalesReportExcel {

    suspend fun writeExcelSheet(
        companyName: String,
        context: Context,
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        list: List<UserSalesResponse>,
        haveAnyError: (isError: Boolean, errorString: String?) -> Unit
    ) {
        try {
            val wb = XSSFWorkbook()
            val currentDateAndTime = SimpleDateFormat("dd-MM-yyyy hh;mm;ss a", Locale.getDefault()).format(Date())
            val sheet = wb.createSheet(currentDateAndTime)

            // Heading creation
            sheet.createHeading(wb = wb, headingTitle = "User Sales Report", noOfColumns = 3)
            // Period
            createPeriodTitleAndCompanyName(
                sheet = sheet,
                wb = wb,
                fromDate = fromDate,
                toDate = toDate,
                companyName = companyName,
            )
            // Header of the table
            createHeaderOfTable(sheet = sheet, wb = wb)

            val sizeOfList = list.size

            list.forEachIndexed { index, userSalesResponse ->
                createItemRow(
                    userSalesResponse = userSalesResponse,
                    index = index,
                    isItLastRow = sizeOfList == index + 1,
                    sheet = sheet,
                    wb = wb
                )
            }

            createTotalValueRow(sheet, wb, sizeOfList + 3)



            sheet.setColumnWidth(0, 5 * 200)
            sheet.setColumnWidth(1, 50 * 200)
            sheet.setColumnWidth(2, 50 * 200)


            val file =
                File(context.getExternalFilesDir(null), "UserSalesReport_${Date().time}.xlsx")

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
        } catch (e: Exception) {
            haveAnyError(true, e.message)
        }

    }


    private fun createPeriodTitleAndCompanyName(
        sheet: Sheet,
        wb: XSSFWorkbook,
        fromDate: String,
        toDate: String,
        companyName: String,
    ) {
        try {
            val periodRow = sheet.createRow(1)
            val font = wb.createFont().apply {
                color = IndexedColors.BLUE.index
            }

            val richTextString = XSSFRichTextString("Period : $fromDate to $toDate").apply {
                val lengthOfFromDate = fromDate.length
                val totalLength = "Period : $fromDate to $toDate".length - 1

                applyFont(9, 9 + lengthOfFromDate, font)
                applyFont(13 + lengthOfFromDate, totalLength + 1, font)
            }
            periodRow.createCell(0).apply {
                setCellValue(richTextString)
            }

            periodRow.createCell(2).apply {
                setCellValue("Company: $companyName")
                cellStyle = wb.createCellStyle().apply {
                    alignment = HorizontalAlignment.RIGHT
                }

            }
        } catch (e: Exception) {
            throw Exception(e.message)
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
            setCellValue("User Name")
            cellStyle = generalHeaderCellStyle
        }

        headerRow.createCell(2).apply {
            setCellValue("Sales Amount")
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
        userSalesResponse: UserSalesResponse,
        index: Int,
        isItLastRow: Boolean,
        sheet: Sheet,
        wb: XSSFWorkbook
    ) {
        val generalHeaderCellStyle = wb.createCellStyle().apply {
            borderRight = BorderStyle.THIN
            borderBottom = if (isItLastRow) BorderStyle.MEDIUM else BorderStyle.THIN
            alignment = HorizontalAlignment.CENTER

        }
        val headerRow = sheet.createRow(index + 3)

        // Si
        headerRow.createCell(0).apply {
            setCellValue((index + 1).toString())
            cellStyle = wb.createCellStyle().apply {
                /*fillForegroundColor = IndexedColors.GOLD.index
                fillPattern = FillPatternType.SOLID_FOREGROUND*/
                borderRight = BorderStyle.THIN
                borderBottom = if (isItLastRow) BorderStyle.MEDIUM else BorderStyle.THIN
                borderLeft = BorderStyle.MEDIUM
                alignment = HorizontalAlignment.CENTER
                dataFormat = wb.createDataFormat().getFormat("0")

            }
        }
        // User Name
        headerRow.createCell(1).apply {
            setCellValue(userSalesResponse.userName)
            cellStyle = generalHeaderCellStyle
        }

        // Sales Amount
        headerRow.createCell(2).apply {
            setCellValue(userSalesResponse.salesAmount.toDouble())
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

        totalRow.createCell(2).apply {
            cellFormula = "Sum(C4:C$rowIndex)"
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
                dataFormat = wb.createDataFormat().getFormat("0.00")

            }
        }


        sheet.addMergedRegion(CellRangeAddress(rowIndex, rowIndex, 0, 1))
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

    }


}