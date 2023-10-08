package com.gulfappdeveloper.projectreport.share.pdf

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.accounts.PaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.accounts.ReceiptResponse
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.purchase.PurchaseMastersResponse
import com.gulfappdeveloper.projectreport.domain.models.purchase.PurchaseSummaryResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.PosPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.SaleSummariesResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.SalesInvoiceResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.UserSalesResponse
import com.gulfappdeveloper.projectreport.domain.services.PdfService
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.account_models.ExpenseLedgerReportTotals
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.account_models.ReArrangedExpenseLedgerDetail
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.PurchaseMasterSelection
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.PurchaseMasterTotals
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.PurchaseSummaryTotals
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.ReArrangedSupplierLedgerDetail
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.SupplierLedgerTotals
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.CustomerLedgerTotals
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.ReArrangedCustomerLedgerDetails
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.SaleSummariesReportTotals
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.SalesInvoiceReportTotals
import com.gulfappdeveloper.projectreport.root.CommonMemory
import com.gulfappdeveloper.projectreport.share.pdf.account.expense_ledger_report.ExpenseLedgerReportPdf
import com.gulfappdeveloper.projectreport.share.pdf.account.payments_report.PdfPaymentsReport
import com.gulfappdeveloper.projectreport.share.pdf.account.recceipt_report.PdfReceiptsReport
import com.gulfappdeveloper.projectreport.share.pdf.purchase.purchase_master_and_supplier_purchase.PurchaseMasterAndSupplierPurchaseReportPdf
import com.gulfappdeveloper.projectreport.share.pdf.purchase.purchase_summary_report.PurchaseSummaryReportPdf
import com.gulfappdeveloper.projectreport.share.pdf.purchase.supplier_ledger_report.SupplierLedgerReportPdf
import com.gulfappdeveloper.projectreport.share.pdf.sales.customer_ledger_report.CustomerLedgerReportPdf
import com.gulfappdeveloper.projectreport.share.pdf.sales.customer_payment_report.CustomerPaymentReportPdf
import com.gulfappdeveloper.projectreport.share.pdf.sales.pos_payment_report.PosPaymentReportPdf
import com.gulfappdeveloper.projectreport.share.pdf.sales.sale_summaries_report.SaleSummariesReportPdf
import com.gulfappdeveloper.projectreport.share.pdf.sales.sales_invoice_report.SalesInvoiceReportPdf
import com.gulfappdeveloper.projectreport.share.pdf.sales.user_sales_report.UserSalesReportPdf

private const val TAG = "PdfServiceImpl"

class PdfServiceImpl(
    private val context: Context,
    private val commonMemory: CommonMemory
) : PdfService {

    override suspend fun writePdfAndShareItForCustomerPaymentReport(
        list: List<CustomerPaymentResponse>,
        listOfTotal: List<Double>,
        fromDate: String,
        fromTime:String,
        toDate: String,
        toTime:String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) {
        val pdfDocument = PdfDocument()
        CustomerPaymentReportPdf.makePdf(
            companyName = commonMemory.companyName,
            pdfDocument = pdfDocument,
            context = context,
            list = list,
            listOfTotal = listOfTotal,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun writePdfAndShareItForPosPaymentReport(
        list: List<PosPaymentResponse>,
        listOfTotal: List<Double>,
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
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
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun writePdfForCustomerLedgerReport(
        list: List<ReArrangedCustomerLedgerDetails>,
        customerLedgerTotals: CustomerLedgerTotals,
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
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
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun writePdfForSupplierLedgerReport(
        list: List<ReArrangedSupplierLedgerDetail>,
        supplierLedgerTotal: SupplierLedgerTotals,
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
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
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            haveAnyError = haveAnyError
        )

    }

    override suspend fun writePdfForExpenseLedgerReport(
        list: List<ReArrangedExpenseLedgerDetail>,
        expenseLedgerTotal: ExpenseLedgerReportTotals,
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        partyName: String,
        balance: Float,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) {
        ExpenseLedgerReportPdf.makePdf(
            companyName = commonMemory.companyName,
            partyName = partyName,
            balance = balance,
            pdfDocument = PdfDocument(),
            context = context,
            list = list,
            expenseLedgerTotals = expenseLedgerTotal,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun writePdfForSalesInvoiceReport(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<SalesInvoiceResponse>,
        salesInvoiceReportTotals: SalesInvoiceReportTotals
    ) {
        SalesInvoiceReportPdf.makePdf(
            companyName = commonMemory.companyName,
            pdfDocument = PdfDocument(),
            context = context,
            list = list,
            salesInvoiceReportTotals = salesInvoiceReportTotals,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun writePdfForSaleSummariesReport(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<SaleSummariesResponse>,
        saleSummariesReportTotals: SaleSummariesReportTotals
    ) {
        SaleSummariesReportPdf.makePdf(
            companyName = commonMemory.companyName,
            pdfDocument = PdfDocument(),
            context = context,
            list = list,
            saleSummariesReportTotals = saleSummariesReportTotals,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun writePdfForPaymentsReport(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PaymentResponse>,
        paymentReportListTotal: Double
    ) {
        PdfPaymentsReport.makePdf(
            companyName = commonMemory.companyName,
            pdfDocument = PdfDocument(),
            context = context,
            list = list,
            paymentReportListTotal = paymentReportListTotal,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun writePdfForReceiptsReport(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<ReceiptResponse>,
        receiptReportListTotal: Double
    ) {
        PdfReceiptsReport.makePdf(
            companyName = commonMemory.companyName,
            pdfDocument = PdfDocument(),
            context = context,
            list = list,
            receiptReportListTotal = receiptReportListTotal,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun writePdfForPurchaseMastersReport(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PurchaseMastersResponse>,
        purchaseMastersReportListTotals: PurchaseMasterTotals,
        purchaseMasterSelection: PurchaseMasterSelection
    ) {
        PurchaseMasterAndSupplierPurchaseReportPdf.makePdf(
            companyName = commonMemory.companyName,
            pdfDocument = PdfDocument(),
            context = context,
            list = list,
            purchaseMasterTotals = purchaseMastersReportListTotals,
            purchaseMasterSelection = purchaseMasterSelection,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun writePdfForUserSalesReportByItext(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<UserSalesResponse>
    ) {

        UserSalesReportPdf.makePdf(
            companyName = commonMemory.companyName,
            pdfDocument = PdfDocument(),
            context = context,
            list = list,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun writePdfForPurchaseSummaryReport(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PurchaseSummaryResponse>,
        purchaseSummaryTotals: PurchaseSummaryTotals
    ) {
        PurchaseSummaryReportPdf.makePdf(
            companyName = commonMemory.companyName,
            pdfDocument = PdfDocument(),
            context = context,
            list = list,
            purchaseSummaryTotals = purchaseSummaryTotals,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
    }


}