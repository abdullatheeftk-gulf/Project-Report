package com.gulfappdeveloper.projectreport.share.pdf

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.accounts.PaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.accounts.ReceiptResponse
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.purchase.PurchaseMastersResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.PosPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.SaleSummariesResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.SalesInvoiceResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.UserSalesResponse
import com.gulfappdeveloper.projectreport.domain.services.PdfService
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.account_models.ExpenseLedgerReportTotals
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.account_models.ReArrangedExpenseLedgerDetail
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.PurchaseMasterSelection
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.PurchaseMasterTotals
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
import com.gulfappdeveloper.projectreport.share.pdf.sales.customer_ledger_report.CustomerLedgerReportPdf
import com.gulfappdeveloper.projectreport.share.pdf.sales.customer_payment_report.CustomerPaymentReportPdf
import com.gulfappdeveloper.projectreport.share.pdf.sales.pos_payment_report.PosPaymentReportPdf
import com.gulfappdeveloper.projectreport.share.pdf.purchase.supplier_ledger_report.SupplierLedgerReportPdf
import com.gulfappdeveloper.projectreport.share.pdf.sales.sale_summaries_report.SaleSummariesReportPdf
import com.gulfappdeveloper.projectreport.share.pdf.sales.sales_invoice_report.SalesInvoiceReportPdf
import com.gulfappdeveloper.projectreport.share.pdf.sales.user_sales_report.UserSalesReportPdf
import com.gulfappdeveloper.projectreport.share.pdf.sales.user_sales_report.UserSalesReportPdfItext
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

    override suspend fun writePdfForExpenseLedgerReport(
        list: List<ReArrangedExpenseLedgerDetail>,
        expenseLedgerTotal: ExpenseLedgerReportTotals,
        fromDate: String,
        toDate: String,
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
            toDate = toDate,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun writePdfForSalesInvoiceReport(
        fromDate: String,
        toDate: String,
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
            toDate = toDate,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun writePdfForSaleSummariesReport(
        fromDate: String,
        toDate: String,
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
            toDate = toDate,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun writePdfForPaymentsReport(
        fromDate: String,
        toDate: String,
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
            toDate = toDate,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun writePdfForReceiptsReport(
        fromDate: String,
        toDate: String,
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
            toDate = toDate,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun writePdfForPurchaseMastersReport(
        fromDate: String,
        toDate: String,
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
            toDate = toDate,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun writePdfForUserSalesReportByItext(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<UserSalesResponse>
    ) {
        /* UserSalesReportPdfItext.writePdf(
             context = context,
             fromDate = fromDate,
             toDate = toDate,
             companyName = commonMemory.companyName,
             list = list,
             getUri = getUri, haveAnyError = haveAnyError
         )*/

        UserSalesReportPdf.makePdf(
            companyName = commonMemory.companyName,
            pdfDocument = PdfDocument(),
            context = context,
            list = list,
            fromDate = fromDate,
            toDate = toDate,
            getUri = getUri,
            haveAnyError = haveAnyError
        )
    }


}