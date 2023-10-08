package com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.sale_summaries_report

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.sales.SaleSummariesResponse
import com.gulfappdeveloper.projectreport.repositories.PdfExcelRepository

class ExcelMakerSaleSummariesReportUseCase(
    private val pdfExcelRepository: PdfExcelRepository
) {
    suspend operator fun invoke(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<SaleSummariesResponse>
    ) = pdfExcelRepository.makeExcelForSaleSummariesReport(
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )
}