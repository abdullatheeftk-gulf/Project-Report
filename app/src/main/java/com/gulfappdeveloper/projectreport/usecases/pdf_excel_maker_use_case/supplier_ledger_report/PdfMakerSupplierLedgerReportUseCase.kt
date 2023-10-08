package com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.supplier_ledger_report

import android.net.Uri
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.ReArrangedSupplierLedgerDetail
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.SupplierLedgerTotals
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository

class PdfMakerSupplierLedgerReportUseCase(
    private val pdfExcelRepository: PdfExcelRepository
) {
    suspend operator fun invoke(
        list: List<ReArrangedSupplierLedgerDetail>,
        supplierLedgerTotal: SupplierLedgerTotals,
        fromDate: String,
        fromTime:String,
        toDate: String,
        toTime:String,
        partyName: String,
        balance: Float,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    )=pdfExcelRepository.writePdfForSupplierLedgerReport(
        list = list,
        supplierLedgerTotal = supplierLedgerTotal,
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        partyName = partyName,
        balance = balance,
        getUri = getUri,
        haveAnyError = haveAnyError
    )
}