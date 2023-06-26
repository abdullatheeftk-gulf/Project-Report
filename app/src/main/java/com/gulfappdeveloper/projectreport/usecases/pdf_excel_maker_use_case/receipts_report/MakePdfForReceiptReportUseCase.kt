package com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.receipts_report

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.accounts.ReceiptResponse
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository

class MakePdfForReceiptReportUseCase(
    private val pdfExcelRepository: PdfExcelRepository
) {
    suspend operator fun invoke(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<ReceiptResponse>,
        receiptReportListTotal: Double
    ) = pdfExcelRepository.writePdfForReceiptsReport(
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list,
        receiptReportListTotal = receiptReportListTotal
    )
}