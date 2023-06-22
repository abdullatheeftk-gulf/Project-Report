package com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.customer_ledger_report

import android.net.Uri
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.CustomerLedgerTotals
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.ReArrangedCustomerLedgerDetails
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository

class PdfMakerCustomerLedgerReportUseCase(
    private val pdfExcelRepository: PdfExcelRepository
) {
    suspend operator fun invoke(
        list: List<ReArrangedCustomerLedgerDetails>,
        customerLedgerTotals: CustomerLedgerTotals,
        partyName: String,
        balance: Float,
        fromDate: String,
        toDate: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) = pdfExcelRepository.writePdfForCustomerLedgerReport(
        list = list,
        customerLedgerTotals = customerLedgerTotals,
        partyName = partyName,
        balance = balance,
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError
    )
}