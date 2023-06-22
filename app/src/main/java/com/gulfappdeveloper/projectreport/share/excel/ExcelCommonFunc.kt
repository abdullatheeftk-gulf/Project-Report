package com.gulfappdeveloper.projectreport.share.excel

import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFFont
import org.apache.poi.xssf.usermodel.XSSFWorkbook

fun Sheet.createHeading(
    wb: XSSFWorkbook,
    headingTitle:String,
    noOfColumns:Int
){
    val headingRow = createRow(0)
    headingRow.height = (40*20).toShort()
    headingRow.createCell(0).apply {
        setCellValue(headingTitle)
        cellStyle = wb.createCellStyle().apply {
            alignment = HorizontalAlignment.CENTER
            verticalAlignment = VerticalAlignment.CENTER
            val font = wb.createFont().apply {
                fontHeight = (21*20).toShort()
                color = IndexedColors.BLUE.index
                underline = XSSFFont.U_SINGLE

            } as XSSFFont
            setFont(font)
        }
    }
    addMergedRegion(CellRangeAddress(0, 0, 0, noOfColumns-1))
}