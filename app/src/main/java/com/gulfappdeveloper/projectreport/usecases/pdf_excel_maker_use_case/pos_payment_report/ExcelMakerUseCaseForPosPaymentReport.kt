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
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
    ) = pdfExcelRepository.writeExcelForPosPaymentReport(
        list = list,
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError
    )
}