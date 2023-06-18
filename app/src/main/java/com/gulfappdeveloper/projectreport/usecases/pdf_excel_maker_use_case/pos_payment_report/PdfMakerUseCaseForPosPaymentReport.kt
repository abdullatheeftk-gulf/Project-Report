package com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.pos_payment_report

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.sales.PosPaymentResponse
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository

class PdfMakerUseCaseForPosPaymentReport(
    private val pdfExcelRepository: PdfExcelRepository
) {
    suspend operator fun invoke(
        list: List<PosPaymentResponse>,
        listOfTotal: List<Double>,
        fromDate: String,
        toDate: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) = pdfExcelRepository.writePdfAndShareItForPosPaymentReport(
        list = list,
        listOfTotal = listOfTotal,
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError

    )
}