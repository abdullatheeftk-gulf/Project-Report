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
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PurchaseMastersResponse>,
        purchaseMasterSelection: PurchaseMasterSelection
    ) = pdfExcelRepository.makeExcelForPurchaseMastersReport(
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list,
        purchaseMasterSelection = purchaseMasterSelection
    )
}