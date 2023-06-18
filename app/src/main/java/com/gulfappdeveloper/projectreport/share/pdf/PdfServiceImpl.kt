package com.gulfappdeveloper.projectreport.share.pdf

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.PosPaymentResponse
import com.gulfappdeveloper.projectreport.domain.services.PdfService
import com.gulfappdeveloper.projectreport.root.CommonMemory
import com.gulfappdeveloper.projectreport.share.pdf.customer_payment_report.CustomerPaymentReportPdf
import com.gulfappdeveloper.projectreport.share.pdf.pos_payment_report.PosPaymentReportPdf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val TAG = "PdfServiceImpl"

class PdfServiceImpl(
    private val context: Context,
    private val commonMemory: CommonMemory
) : PdfService {

    override suspend fun writePdfAndShareItForCustomerPaymentReport(
        list: List<CustomerPaymentResponse>,
        listOfTotal: List<Double>,
        fromDate: String,
        toDate: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) {
        val pdfDocument = PdfDocument()
        CustomerPaymentReportPdf.makePdf(
            pdfDocument,
            context,
            list,
            listOfTotal,
            fromDate,
            toDate,
            getUri,
            haveAnyError
        )
    }

    override suspend fun writePdfAndShareItForPosPaymentReport(
        list: List<PosPaymentResponse>,
        listOfTotal: List<Double>,
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) {
        val pdfDocument = PdfDocument()
        PosPaymentReportPdf.makePdf(
            companyName = commonMemory.companyName,
            pdfDocument = pdfDocument,
            context = context,
            list = list,
            listOfTotal = listOfTotal,
            fromDate = fromDate,
            toDate = toDate,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
    }


}