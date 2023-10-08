package com.gulfappdeveloper.projectreport.share.excel

import android.content.Context
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
import com.gulfappdeveloper.projectreport.domain.services.ExcelService
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.account_models.ReArrangedExpenseLedgerDetail
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.PurchaseMasterSelection
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.ReArrangedSupplierLedgerDetail
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.ReArrangedCustomerLedgerDetails
import com.gulfappdeveloper.projectreport.root.CommonMemory
import com.gulfappdeveloper.projectreport.share.excel.account.expense_ledger_report.ExpenseLedgerReportExcel
import com.gulfappdeveloper.projectreport.share.excel.account.payements_report.PaymentsReportExcel
import com.gulfappdeveloper.projectreport.share.excel.account.receipts_report.ReceiptsReportExcel
import com.gulfappdeveloper.projectreport.share.excel.purchase.purchase_masters_and_supplier_purchase.PurchaseMasterReportExcel
import com.gulfappdeveloper.projectreport.share.excel.purchase.purchase_summary_report.PurchaseSummaryReportExcel
import com.gulfappdeveloper.projectreport.share.excel.purchase.supplier_ledger_report.SupplierLedgerReportExcel
import com.gulfappdeveloper.projectreport.share.excel.sales.customer_ledger_report.CustomerLedgerReportExcel
import com.gulfappdeveloper.projectreport.share.excel.sales.customer_payment_report.CustomerPaymentReportExcel
import com.gulfappdeveloper.projectreport.share.excel.sales.pos_payment_report.PosPaymentReportExcel
import com.gulfappdeveloper.projectreport.share.excel.sales.sale_summaries_report.SaleSummariesReportExcel
import com.gulfappdeveloper.projectreport.share.excel.sales.sales_invoice_report.SaleInvoiceReportExcel
import com.gulfappdeveloper.projectreport.share.excel.sales.user_sales_report.UserSalesReportExcel

class ExcelServiceImpl(
    private val context: Context,
    private val commonMemory: CommonMemory
) : ExcelService {

    override suspend fun makeExcelForCustomerPaymentReport(
        list: List<CustomerPaymentResponse>,
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (Boolean, String?) -> Unit
    ) {
        CustomerPaymentReportExcel.writeExcelSheet(
            context = context,
            list = list,
            haveAnyError = haveAnyError,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            companyName = commonMemory.companyName
        )
    }

    override suspend fun makeExcelForPosPaymentReport(
        list: List<PosPaymentResponse>,
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (Boolean, String?) -> Unit
    ) {
        PosPaymentReportExcel.writeExcelSheet(
            companyName = commonMemory.companyName,
            context = context,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            list = list,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun makeExcelForCustomerLedgerReport(
        list: List<ReArrangedCustomerLedgerDetails>,
        partyName: String,
        balance: Float,
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (isError: Boolean, errorString: String?) -> Unit
    ) {
        CustomerLedgerReportExcel.writeExcelSheet(
            companyName = commonMemory.companyName,
            partyName = partyName,
            balance = balance,
            context = context,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            list = list,
            haveAnyError = haveAnyError
        )
    }


    override suspend fun makeExcelForSupplierLedgerReport(
        list: List<ReArrangedSupplierLedgerDetail>,
        partyName: String,
        balance: Float,
        fromDate: String,
        fromTime:String,
        toDate: String,
        toTime:String,
        getUri: (Uri) -> Unit,
        haveAnyError: (isError: Boolean, errorString: String?) -> Unit
    ) {
        SupplierLedgerReportExcel.writeExcelSheet(
            companyName = commonMemory.companyName,
            partyName = partyName,
            balance = balance,
            context = context,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            list = list,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun makeExcelForSalesInvoiceReport(
        fromDate: String,
        fromTime:String,
        toDate: String,
        toTime:String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<SalesInvoiceResponse>
    ) {
        SaleInvoiceReportExcel.writeExcelSheet(
            companyName = commonMemory.companyName,
            context = context,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            list = list,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun makeExcelForSaleSummariesReport(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<SaleSummariesResponse>
    ) {
        SaleSummariesReportExcel.writeExcelSheet(
            companyName = commonMemory.companyName,
            context = context,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            list = list,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun makeExcelForExpenseLedgerReport(
        list: List<ReArrangedExpenseLedgerDetail>,
        partyName: String,
        balance: Float,
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (isError: Boolean, errorString: String?) -> Unit
    ) {
        ExpenseLedgerReportExcel.writeExcelSheet(
            companyName = commonMemory.companyName,
            partyName = partyName,
            balance = balance,
            context = context,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            list = list,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun makeExcelForPaymentsReport(
        fromDate: String,
        fromTime:String,
        toDate: String,
        toTime:String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PaymentResponse>
    ) {
        PaymentsReportExcel.writeExcelSheet(
            companyName = commonMemory.companyName,
            context = context,
            fromDate = fromDate,
            fromTime = fromTime,
            toTime = toTime,
            toDate = toDate,
            getUri = getUri,
            list = list,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun makeExcelForReceiptsReport(
        fromDate: String,
        fromTime:String,
        toDate: String,
        toTime:String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<ReceiptResponse>
    ) {
        ReceiptsReportExcel.writeExcelSheet(
            companyName = commonMemory.companyName,
            context = context,
            fromDate = fromDate,
            fromTime= fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            list = list,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun makeExcelForPurchaseMastersReport(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PurchaseMastersResponse>,
        purchaseMasterSelection: PurchaseMasterSelection
    ) {
        PurchaseMasterReportExcel.writeExcelSheet(
            companyName = commonMemory.companyName,
            context = context,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            purchaseMastersSelection = purchaseMasterSelection,
            list = list,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun makeExcelForUserSalesReport(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<UserSalesResponse>
    ) {
        UserSalesReportExcel.writeExcelSheet(
            companyName = commonMemory.companyName,
            context = context,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            list = list,
            haveAnyError = haveAnyError
        )
    }

    override suspend fun makeExcelForPurchaseSummaryReport(
        fromDate: String,
        fromTime:String,
        toDate: String,
        toTime:String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PurchaseSummaryResponse>
    ) {
        PurchaseSummaryReportExcel.writeExcelSheet(
            companyName = commonMemory.companyName,
            context = context,
            fromDate = fromDate,
            fromTime = fromTime,
            toDate = toDate,
            toTime = toTime,
            getUri = getUri,
            list = list,
            haveAnyError = haveAnyError
        )
    }
}