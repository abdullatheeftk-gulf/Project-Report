package com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.expense_lesdger_report_use_case

import android.net.Uri
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.account_models.ExpenseLedgerReportTotals
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.account_models.ReArrangedExpenseLedgerDetail
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository

class PdfMakerExpenseLedgerReportUseCase(
    private val pdfExcelRepository: PdfExcelRepository
) {
    suspend operator fun invoke(
        list: List<ReArrangedExpenseLedgerDetail>,
        expenseLedgerTotal: ExpenseLedgerReportTotals,
        fromDate: String,
        toDate: String,
        partyName: String,
        balance: Float,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) = pdfExcelRepository.writePdfForExpenseLedgerReport(
        list = list,
        expenseLedgerTotal = expenseLedgerTotal,
        fromDate = fromDate,
        toDate = toDate,
        partyName = partyName,
        balance = balance,
        getUri = getUri,
        haveAnyError = haveAnyError
    )
}