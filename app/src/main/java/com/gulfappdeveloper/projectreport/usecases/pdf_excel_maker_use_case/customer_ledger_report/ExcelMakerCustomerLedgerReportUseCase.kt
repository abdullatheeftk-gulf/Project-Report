package com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.customer_ledger_report

import android.net.Uri
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.ReArrangedCustomerLedgerDetails
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository

class ExcelMakerCustomerLedgerReportUseCase
    (
    private val pdfExcelRepository: PdfExcelRepository
) {
    suspend operator fun invoke(
        list: List<ReArrangedCustomerLedgerDetails>,
        partyName: String,
        balance: Float,
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (isError: Boolean, errorString: String?) -> Unit
    ) =
        pdfExcelRepository.makeExcelForCustomerLedgerReport(
            list = list,
            partyName = partyName,
            balance = balance,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
}