package com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.sales_invoice_report

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.sales.SalesInvoiceResponse
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository

class ExcelMakerSalesInvoiceReportUseCase(
    private val pdfExcelRepository: PdfExcelRepository
) {
    suspend operator fun invoke(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<SalesInvoiceResponse>
    ) = pdfExcelRepository.makeExcelForSalesInvoiceReport(
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )
}