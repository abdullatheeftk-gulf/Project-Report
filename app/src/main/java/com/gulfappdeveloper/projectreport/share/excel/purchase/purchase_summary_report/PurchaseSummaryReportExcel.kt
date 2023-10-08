package com.gulfappdeveloper.projectreport.share.excel.purchase.purchase_summary_report

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.gulfappdeveloper.projectreport.domain.models.purchase.PurchaseSummaryResponse
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
import java.util.Date
import java.util.Locale

object PurchaseSummaryReportExcel {
    suspend fun writeExcelSheet(
        companyName: String,
        context: Context,
        fromDate: String,
        fromTime:String,
        toDate: String,
        toTime:String,
        getUri: (Uri) -> Unit,
        list: List<PurchaseSummaryResponse>,
        haveAnyError: (isError: Boolean, errorString: String?) -> Unit
    ) {
        try {
            val wb = XSSFWorkbook()
            val currentDateAndTime =
                SimpleDateFormat("dd-MM-yyyy hh;mm;ss a", Locale.getDefault()).format(
                    Date()
                )
            val sheet = wb.createSheet(currentDateAndTime)


            // Heading creation
            sheet.createHeading(
                wb = wb,
                headingTitle = "Purchase Summary Report",
                noOfColumns = 8
            )
            // Period
            createPeriodTitleAndCompanyName(
                sheet = sheet,
                wb = wb,
                fromDate = fromDate,
                fromTime = fromTime,
                toDate = toDate,
                toTime = toTime,
                companyName = companyName,
            )
            // Header of the table
            createHeaderOfTable(sheet = sheet, wb = wb)

            val sizeOfList = list.size
            list.forEachIndexed { index, purchaseSummaryResponse ->
                createItemRow(
                    purchaseSummaryResponse = purchaseSummaryResponse,
                    index = index,
                    isItLastRow = sizeOfList == index + 1,
                    sheet = sheet,
                    wb = wb
                )
            }

            createTotalValueRow(sheet, wb, sizeOfList + 3)



            sheet.setColumnWidth(0, 5 * 200)
            sheet.setColumnWidth(1, 35 * 200)
            sheet.setColumnWidth(2, 15 * 200)
            sheet.setColumnWidth(3, 60 * 200)
            sheet.setColumnWidth(4, 20 * 200)
            sheet.setColumnWidth(5, 20 * 200)
            sheet.setColumnWidth(6, 20 * 200)
            sheet.setColumnWidth(7, 20 * 200)

            val fileName = "PurchaseSummaryReport"
            val file =
                File(context.getExternalFilesDir(null), "${fileName}_${Date().time}.xlsx")

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
        fromTime:String,
        toDate: String,
        toTime:String,
        companyName: String,
    ) {
        try {
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

            periodRow.createCell(7).apply {
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
            setCellValue("Date")
            cellStyle = generalHeaderCellStyle
        }
        headerRow.createCell(2).apply {
            setCellValue("Invoice No")
            cellStyle = generalHeaderCellStyle
        }
        headerRow.createCell(3).apply {
            setCellValue("Supplier")
            cellStyle = generalHeaderCellStyle
        }
        headerRow.createCell(4).apply {
            setCellValue("Tax Id")
            cellStyle = generalHeaderCellStyle
        }
        headerRow.createCell(5).apply {
            setCellValue("Taxable")
            cellStyle = generalHeaderCellStyle
        }
        headerRow.createCell(6).apply {
            setCellValue("Tax")
            cellStyle = generalHeaderCellStyle
        }

        headerRow.createCell(7).apply {
            setCellValue("Net")
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
        purchaseSummaryResponse: PurchaseSummaryResponse,
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
        // Date
        headerRow.createCell(1).apply {
            setCellValue(purchaseSummaryResponse.invoiceDate.stringToDateStringConverter())
            cellStyle = generalHeaderCellStyle
        }
        // invoice No
        headerRow.createCell(2).apply {
            setCellValue(purchaseSummaryResponse.invoiceNo.toString())
            cellStyle = generalHeaderCellStyle
        }
        // Supplier
        headerRow.createCell(3).apply {
            setCellValue(purchaseSummaryResponse.supplier)
            cellStyle = generalHeaderCellStyle
        }
        // Tax id
        headerRow.createCell(4).apply {
            setCellValue(purchaseSummaryResponse.taxId?:"")
            cellStyle = generalHeaderCellStyle.apply {
                dataFormat = wb.createDataFormat().getFormat("0.00")
            }
        }
        // Taxable
        headerRow.createCell(5).apply {
            setCellValue(purchaseSummaryResponse.taxable.toDouble())
            cellStyle = generalHeaderCellStyle.apply {
                dataFormat = wb.createDataFormat().getFormat("0.00")
            }
        }
        // tax
        headerRow.createCell(6).apply {
            setCellValue(purchaseSummaryResponse.tax.toDouble())
            cellStyle = generalHeaderCellStyle.apply {
                dataFormat = wb.createDataFormat().getFormat("0.00")
            }
        }

        // net
        headerRow.createCell(7).apply {
            setCellValue(purchaseSummaryResponse.net.toDouble())
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

        totalRow.createCell(5).apply {
            cellFormula = "Sum(F4:F$rowIndex)"
            cellStyle = wb.createCellStyle().apply {
                val font = wb.createFont().apply {
                    fontHeight = (14 * 20).toShort()
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

        totalRow.createCell(6).apply {
            cellFormula = "Sum(G4:G$rowIndex)"
            cellStyle = wb.createCellStyle().apply {
                val font = wb.createFont().apply {
                    fontHeight = (14 * 20).toShort()
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


        totalRow.createCell(7).apply {
            cellFormula = "Sum(H4:H$rowIndex)"
            cellStyle = wb.createCellStyle().apply {
                val font = wb.createFont().apply {
                    fontHeight = (14 * 20).toShort()
                    bold = true
                    color = IndexedColors.BLUE.index
                }
                setFont(font)
                borderBottom = BorderStyle.MEDIUM
                borderLeft = BorderStyle.THIN
                borderRight = BorderStyle.MEDIUM
                alignment = HorizontalAlignment.CENTER
                verticalAlignment = VerticalAlignment.CENTER
                dataFormat = wb.createDataFormat().getFormat("0.00")
            }
        }
        sheet.addMergedRegion(CellRangeAddress(rowIndex, rowIndex, 0, 4))
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
        totalRow.createCell(4).apply {

            cellStyle = wb.createCellStyle().apply {

                borderBottom = BorderStyle.MEDIUM

            }
        }
    }


}