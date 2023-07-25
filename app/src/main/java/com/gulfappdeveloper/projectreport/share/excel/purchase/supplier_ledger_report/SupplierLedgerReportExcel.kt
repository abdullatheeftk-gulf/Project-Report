package com.gulfappdeveloper.projectreport.share.excel.purchase.supplier_ledger_report

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.ReArrangedSupplierLedgerDetail
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.ReArrangedCustomerLedgerDetails
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

private const val TAG = "SupplierLedgerReportExc"
object SupplierLedgerReportExcel {
    suspend fun writeExcelSheet(
        companyName: String,
        partyName: String,
        balance: Float,
        context: Context,
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        list: List<ReArrangedSupplierLedgerDetail>,
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
            sheet.createHeading(wb = wb, headingTitle = "Supplier Ledger Report", noOfColumns = 6)
            createPartyNameCell(sheet = sheet, partyName = partyName)
            createBalanceCell(sheet = sheet, balance = balance)
            createPeriodTitleAndCompanyName(
                sheet = sheet,
                wb = wb,
                fromDate = fromDate,
                toDate = toDate,
                companyName = companyName
            )
            createHeaderOfTable(sheet = sheet, wb = wb)

            val sizeOfList = list.size
            list.forEachIndexed { index, reArrangedSupplierLedgerDetails ->
                Log.e(TAG, "writeExcelSheet: $reArrangedSupplierLedgerDetails", )
                createItemRow(
                    reArrangedSupplierLedgerDetails = reArrangedSupplierLedgerDetails,
                    index = index,
                    isItLastRow = sizeOfList == index + 1,
                    sheet = sheet,
                    wb = wb
                )
            }

            createTotalValueRow(sheet, wb, sizeOfList + 5)


            sheet.setColumnWidth(0, 5 * 200)
            sheet.setColumnWidth(1, 35 * 200)
            sheet.setColumnWidth(2, 15 * 200)
            sheet.setColumnWidth(3, 50 * 200)
            sheet.setColumnWidth(4, 20 * 200)
            sheet.setColumnWidth(5, 20 * 200)


            val file =
                File(context.getExternalFilesDir(null), "SupplierLedgerReport_${Date().time}.xlsx")

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

    private fun createPartyNameCell(
        sheet: Sheet,
        partyName: String
    ) {
        try {
            val partyNameRow = sheet.createRow(1)
            partyNameRow.createCell(0).apply {
                setCellValue("Party Name :\t $partyName")
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    private fun createBalanceCell(
        sheet: Sheet,
        balance: Float
    ) {
        try {
            val partyNameRow = sheet.createRow(2)
            partyNameRow.createCell(0).apply {
                setCellValue("Balance :\t $balance")
            }
        } catch (e: Exception) {
            throw Exception(e.message)
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
            val periodRow = sheet.createRow(3)
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

            periodRow.createCell(5).apply {
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
        try {
            val generalHeaderCellStyle = wb.createCellStyle().apply {
                fillForegroundColor = IndexedColors.GOLD.index
                fillPattern = FillPatternType.SOLID_FOREGROUND
                borderRight = BorderStyle.THIN
                borderBottom = BorderStyle.THIN
                borderTop = BorderStyle.MEDIUM
                alignment = HorizontalAlignment.CENTER

            }
            val headerRow = sheet.createRow(4)


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
                setCellValue("Voucher No")
                cellStyle = generalHeaderCellStyle
            }
            headerRow.createCell(3).apply {
                setCellValue("Particulars")
                cellStyle = generalHeaderCellStyle
            }
            headerRow.createCell(4).apply {
                setCellValue("Debit")
                cellStyle = generalHeaderCellStyle
            }

            headerRow.createCell(5).apply {
                setCellValue("Credit")
                cellStyle = wb.createCellStyle().apply {
                    fillForegroundColor = IndexedColors.GOLD.index
                    fillPattern = FillPatternType.SOLID_FOREGROUND
                    borderRight = BorderStyle.MEDIUM
                    borderBottom = BorderStyle.THIN
                    borderTop = BorderStyle.MEDIUM
                    alignment = HorizontalAlignment.CENTER
                }
            }

        } catch (e: Exception) {
            throw Exception(e.message)
        }

    }


    private fun createItemRow(
        reArrangedSupplierLedgerDetails: ReArrangedSupplierLedgerDetail,
        index: Int,
        isItLastRow: Boolean,
        sheet: Sheet,
        wb: XSSFWorkbook
    ) {
        try {
            val generalHeaderCellStyle = wb.createCellStyle().apply {
                borderRight = BorderStyle.THIN
                borderBottom = if (isItLastRow) BorderStyle.MEDIUM else BorderStyle.THIN
                alignment = HorizontalAlignment.CENTER

            }
            val headerRow = sheet.createRow(index + 5)

            // Si
            headerRow.createCell(0).apply {
                setCellValue(reArrangedSupplierLedgerDetails.si.toString())
                cellStyle = wb.createCellStyle().apply {
                    borderRight = BorderStyle.THIN
                    borderBottom = if (isItLastRow) BorderStyle.MEDIUM else BorderStyle.THIN
                    borderLeft = BorderStyle.MEDIUM
                    alignment = HorizontalAlignment.CENTER
                    dataFormat = wb.createDataFormat().getFormat("0")

                }
            }
            // Date
            headerRow.createCell(1).apply {
                setCellValue(reArrangedSupplierLedgerDetails.voucherDate.stringToDateStringConverter())
                cellStyle = generalHeaderCellStyle
            }
            // invoice No
            headerRow.createCell(2).apply {
                setCellValue(reArrangedSupplierLedgerDetails.voucherNo.toString())
                cellStyle = generalHeaderCellStyle
            }
            // Party
            headerRow.createCell(3).apply {
                setCellValue(reArrangedSupplierLedgerDetails.particulars ?: "--")
                cellStyle = generalHeaderCellStyle
            }
            // Debit
            headerRow.createCell(4).apply {
                setCellValue(reArrangedSupplierLedgerDetails.debit.toDouble())
                cellStyle = generalHeaderCellStyle.apply {
                    dataFormat = wb.createDataFormat().getFormat("0.00")
                }
            }

            // Total
            headerRow.createCell(5).apply {
                setCellValue(reArrangedSupplierLedgerDetails.credit.toDouble())
                cellStyle = wb.createCellStyle().apply {
                    borderRight = BorderStyle.MEDIUM
                    borderBottom = if (isItLastRow) BorderStyle.MEDIUM else BorderStyle.THIN
                    alignment = HorizontalAlignment.CENTER
                    dataFormat = wb.createDataFormat().getFormat("0.00")
                }
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }

    }

    private fun createTotalValueRow(sheet: Sheet, wb: XSSFWorkbook, rowIndex: Int) {
        val totalRow = sheet.createRow(rowIndex).apply {
            height = (25 * 20).toShort()

        }

        totalRow.createCell(4).apply {
            cellFormula = "Sum(E6:E$rowIndex)"
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


        totalRow.createCell(5).apply {
            cellFormula = "Sum(F6:F$rowIndex)"
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