package com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.payments_report

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.accounts.PaymentResponse
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository

class MakeExcelForPaymentReportUseCase(
    private val pdfExcelRepository: PdfExcelRepository
) {
    suspend operator fun invoke(
        fromDate: String,
        fromTime:String,
        toDate: String,
        toTime:String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PaymentResponse>,
    ) = pdfExcelRepository.makeExcelForPaymentsReport(
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )
}