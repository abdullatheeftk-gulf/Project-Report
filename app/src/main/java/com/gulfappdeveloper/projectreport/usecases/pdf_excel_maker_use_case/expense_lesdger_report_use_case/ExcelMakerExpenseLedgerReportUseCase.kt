package com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.expense_lesdger_report_use_case

import android.net.Uri
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.account_models.ReArrangedExpenseLedgerDetail
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository

class ExcelMakerExpenseLedgerReportUseCase(
    private val pdfExcelRepository: PdfExcelRepository
) {
    suspend operator fun invoke(
        list: List<ReArrangedExpenseLedgerDetail>,
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        partyName: String,
        balance: Float,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) = pdfExcelRepository.makeExcelForExpenseLedgerReport(
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