package com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.purchase_master_report

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.purchase.PurchaseMastersResponse
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.PurchaseMasterSelection
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository

class ExcelMakerPurchaseMastersReportUseCase(
    private val pdfExcelRepository: PdfExcelRepository
) {
    suspend operator fun invoke(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PurchaseMastersResponse>,
        purchaseMasterSelection: PurchaseMasterSelection
    ) = pdfExcelRepository.makeExcelForPurchaseMastersReport(
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list,
        purchaseMasterSelection
    )
}