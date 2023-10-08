package com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.customer_payment_report

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository

class ExcelMakerUseCaseForCustomerPaymentReport(
    private val pdfExcelRepository: PdfExcelRepository
) {
    suspend operator fun invoke(
        list: List<CustomerPaymentResponse>,
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
    ) = pdfExcelRepository.writeExcelForCustomerPaymentReport(
        list = list,
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
    )
}