package com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.sale_summaries_report

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.sales.SaleSummariesResponse
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository

class ExcelMakerSaleSummariesReportUseCase(
    private val pdfExcelRepository: PdfExcelRepository
) {
    suspend operator fun invoke(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<SaleSummariesResponse>
    ) = pdfExcelRepository.makeExcelForSaleSummariesReport(
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )
}