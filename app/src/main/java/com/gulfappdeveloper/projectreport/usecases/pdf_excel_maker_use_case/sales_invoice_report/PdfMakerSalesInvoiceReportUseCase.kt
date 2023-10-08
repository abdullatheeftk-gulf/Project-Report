package com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.sales_invoice_report

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.sales.SalesInvoiceResponse
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.SalesInvoiceReportTotals
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository

class PdfMakerSalesInvoiceReportUseCase(
    private val pdfExcelRepository: PdfExcelRepository
) {
    suspend operator fun invoke(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<SalesInvoiceResponse>,
        salesInvoiceReportTotals: SalesInvoiceReportTotals
    ) = pdfExcelRepository.writePdfForSalesInvoiceReport(
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list,
        salesInvoiceReportTotals = salesInvoiceReportTotals
    )
}