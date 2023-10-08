package com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.supplier_ledger_report

import android.net.Uri
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.ReArrangedSupplierLedgerDetail
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository

class ExcelMakerForSupplierLedgerReportUseCase(
    private val pdfExcelRepository: PdfExcelRepository
) {
    suspend operator fun invoke(
        list: List<ReArrangedSupplierLedgerDetail>,
        partyName: String,
        balance: Float,
        fromDate: String,
        fromTime:String,
        toDate: String,
        toTime:String,
        getUri: (Uri) -> Unit,
        haveAnyError: (isError: Boolean, errorString: String?) -> Unit
    ) = pdfExcelRepository.makeExcelForSupplierLedgerReport(
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