package com.gulfappdeveloper.projectreport.repositories

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import com.gulfappdeveloper.projectreport.domain.services.ExcelService
import com.gulfappdeveloper.projectreport.domain.services.PdfService
import javax.inject.Inject

class PdfExcelRepository @Inject constructor(
    private val pdfService: PdfService,
    private val excelService: ExcelService
) {

    suspend fun writePdfAndShareItForCustomerPaymentReport(
        list: List<CustomerPaymentResponse>,
        fromDate: String,
        toDate: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) = pdfService.writePdfAndShareItForCustomerPaymentReport(
        list, fromDate, toDate, getUri, haveAnyError
    )

    suspend fun writeExcelForCustomerPaymentReport(
        list: List<CustomerPaymentResponse>,
        fromDate: String,
        toDate: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) = excelService.makeExcelForCustomerPaymentReport(
        list, fromDate, toDate, getUri, haveAnyError
    )
}