package com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.pos_payment_report

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.sales.PosPaymentResponse
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository

class ExcelMakerUseCaseForPosPaymentReport(
    private val pdfExcelRepository: PdfExcelRepository
) {
    suspend operator fun invoke(
        list: List<PosPaymentResponse>,
        fromDate: String,
        toDate: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
    ) = pdfExcelRepository.writeExcelForPosPaymentReport(
        list = list,
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError
    )
}