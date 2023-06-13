package com.gulfappdeveloper.projectreport.share.pdf

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.util.Log
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import com.gulfappdeveloper.projectreport.domain.services.PdfService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

private const val TAG = "PdfServiceImpl"

class PdfServiceImpl(
    private val context: Context,
) : PdfService {

    override suspend fun writePdfAndShareItForCustomerPaymentReport(
        list: List<CustomerPaymentResponse>,
        fromDate: String,
        toDate: String,
        getUri:(uri:Uri)->Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) {
        val pdfDocument = PdfDocument()

        CustomerPaymentReportPdf.makePdf(pdfDocument, context, list, fromDate, toDate,getUri, haveAnyError)

    }

}