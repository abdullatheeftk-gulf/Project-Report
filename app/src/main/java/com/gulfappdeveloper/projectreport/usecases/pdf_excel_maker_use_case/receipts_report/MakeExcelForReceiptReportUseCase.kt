package com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.receipts_report

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.accounts.ReceiptResponse
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository

class MakeExcelForReceiptReportUseCase(
    private val pdfExcelRepository: PdfExcelRepository
) {
    suspend operator fun invoke(
        fromDate: String,
        fromTime:String,
        toDate: String,
        toTime:String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<ReceiptResponse>,
    ) = pdfExcelRepository.makeExcelForReceiptsReport(
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )
}