package com.gulfappdeveloper.projectreport.share.pdf

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.PosPaymentResponse
import com.gulfappdeveloper.projectreport.domain.services.PdfService
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.ReArrangedSupplierLedgerDetail
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.SupplierLedgerTotals
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.CustomerLedgerTotals
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.ReArrangedCustomerLedgerDetails
import com.gulfappdeveloper.projectreport.root.CommonMemory
import com.gulfappdeveloper.projectreport.share.pdf.customer_ledger_report.CustomerLedgerReportPdf
import com.gulfappdeveloper.projectreport.share.pdf.customer_payment_report.CustomerPaymentReportPdf
import com.gulfappdeveloper.projectreport.share.pdf.pos_payment_report.PosPaymentReportPdf
import com.gulfappdeveloper.projectreport.share.pdf.supplier_ledger_report.SupplierLedgerReportPdf
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

    override suspend fun writePdfForCustomerLedgerReport(
        list: List<ReArrangedCustomerLedgerDetails>,
        customerLedgerTotals: CustomerLedgerTotals,
        fromDate: String,
        toDate: String,
        partyName: String,
        balance: Float,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) {
        val pdfDocument = PdfDocument()
        CustomerLedgerReportPdf.makePdf(
            companyName = commonMemory.companyName,
            partyName = partyName,
            balance = balance,
            pdfDocument = pdfDocument,
            context = context,
            list = list,
            customerLedgerDetails = customerLedgerTotals,
            fromDate = fromDate,
            toDate = toDate,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun writePdfForSupplierLedgerReport(
        list: List<ReArrangedSupplierLedgerDetail>,
        supplierLedgerTotal: SupplierLedgerTotals,
        fromDate: String,
        toDate: String,
        partyName: String,
        balance: Float,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) {
        val pdfDocument = PdfDocument()
        SupplierLedgerReportPdf.makePdf(
            companyName = commonMemory.companyName,
            partyName = partyName,
            balance = balance,
            pdfDocument = pdfDocument,
            context = context,
            list = list,
            supplierLedgerTotals = supplierLedgerTotal,
            fromDate = fromDate,
            toDate = toDate,
            getUri = getUri,
            haveAnyError = haveAnyError
        )

    }


}