package com.gulfappdeveloper.projectreport.repositories

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
import javax.inject.Inject

class PdfExcelRepository @Inject constructor(
    private val pdfService: PdfService,
    private val excelService: ExcelService
) {

    suspend fun writePdfAndShareItForCustomerPaymentReport(
        list: List<CustomerPaymentResponse>,
        listOfTotal: List<Double>,
        fromDate: String,
        fromTime:String,
        toDate: String,
        toTime:String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) = pdfService.writePdfAndShareItForCustomerPaymentReport(
        list = list,
        listOfTotal = listOfTotal,
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError
    )

    suspend fun writeExcelForCustomerPaymentReport(
        list: List<CustomerPaymentResponse>,
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) = excelService.makeExcelForCustomerPaymentReport(
        list = list,
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError
    )

    suspend fun writePdfAndShareItForPosPaymentReport(
        list: List<PosPaymentResponse>,
        listOfTotal: List<Double>,
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) = pdfService.writePdfAndShareItForPosPaymentReport(
        list = list,
        listOfTotal = listOfTotal,
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError
    )


    suspend fun writePdfForCustomerLedgerReport(
        list: List<ReArrangedCustomerLedgerDetails>,
        customerLedgerTotals: CustomerLedgerTotals,
        partyName: String,
        balance: Float,
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) = pdfService.writePdfForCustomerLedgerReport(
        list = list,
        customerLedgerTotals = customerLedgerTotals,
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        partyName = partyName,
        balance = balance,
        getUri = getUri,
        haveAnyError = haveAnyError
    )

    suspend fun writePdfForSupplierLedgerReport(
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
    ) = pdfService.writePdfForSupplierLedgerReport(
        list = list,
        supplierLedgerTotal = supplierLedgerTotal,
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        partyName = partyName,
        balance = balance,
        getUri = getUri,
        haveAnyError = haveAnyError
    )


    suspend fun writeExcelForPosPaymentReport(
        list: List<PosPaymentResponse>,
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) = excelService.makeExcelForPosPaymentReport(
        list = list,
        fromDate = fromDate,
        fromTime,
        toDate = toDate,
        toTime,
        getUri = getUri,
        haveAnyError = haveAnyError
    )

    suspend fun makeExcelForCustomerLedgerReport(
        list: List<ReArrangedCustomerLedgerDetails>,
        partyName: String,
        balance: Float,
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (isError: Boolean, errorString: String?) -> Unit
    ) = excelService.makeExcelForCustomerLedgerReport(
        list = list,
        partyName = partyName,
        balance = balance,
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError
    )

    suspend fun makeExcelForSupplierLedgerReport(
        list: List<ReArrangedSupplierLedgerDetail>,
        partyName: String,
        balance: Float,
        fromDate: String,
        fromTime:String,
        toDate: String,
        toTime:String,
        getUri: (Uri) -> Unit,
        haveAnyError: (isError: Boolean, errorString: String?) -> Unit
    ) = excelService.makeExcelForSupplierLedgerReport(
        list = list,
        partyName = partyName,
        balance = balance,
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError
    )


    suspend fun writePdfForSalesInvoiceReport(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<SalesInvoiceResponse>,
        salesInvoiceReportTotals: SalesInvoiceReportTotals
    ) = pdfService.writePdfForSalesInvoiceReport(
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list,
        salesInvoiceReportTotals = salesInvoiceReportTotals
    )

    suspend fun writePdfForSaleSummariesReport(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<SaleSummariesResponse>,
        saleSummariesReportTotals: SaleSummariesReportTotals
    ) = pdfService.writePdfForSaleSummariesReport(
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list,
        saleSummariesReportTotals = saleSummariesReportTotals
    )

    suspend fun makeExcelForSalesInvoiceReport(
        fromDate: String,
        fromTime:String,
        toDate: String,
        toTime:String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<SalesInvoiceResponse>
    ) = excelService.makeExcelForSalesInvoiceReport(
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )

    suspend fun makeExcelForSaleSummariesReport(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<SaleSummariesResponse>
    ) = excelService.makeExcelForSaleSummariesReport(
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )

    suspend fun writePdfForExpenseLedgerReport(
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
    ) = pdfService.writePdfForExpenseLedgerReport(
        list = list,
        expenseLedgerTotal = expenseLedgerTotal,
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        partyName = partyName,
        balance = balance,
        getUri = getUri,
        haveAnyError = haveAnyError
    )

    suspend fun makeExcelForExpenseLedgerReport(
        list: List<ReArrangedExpenseLedgerDetail>,
        partyName: String,
        balance: Float,
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (isError: Boolean, errorString: String?) -> Unit
    ) = excelService.makeExcelForExpenseLedgerReport(
        list = list,
        partyName = partyName,
        balance = balance,
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError
    )

    suspend fun writePdfForPaymentsReport(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PaymentResponse>,
        paymentReportListTotal: Double
    ) = pdfService.writePdfForPaymentsReport(
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list,
        paymentReportListTotal = paymentReportListTotal
    )

    suspend fun writePdfForReceiptsReport(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<ReceiptResponse>,
        receiptReportListTotal: Double
    ) = pdfService.writePdfForReceiptsReport(
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list,
        receiptReportListTotal = receiptReportListTotal
    )

    suspend fun makeExcelForPaymentsReport(
        fromDate: String,
        fromTime:String,
        toDate: String,
        toTime:String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PaymentResponse>
    ) = excelService.makeExcelForPaymentsReport(
        fromDate = fromDate,
        fromTime= fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )


    suspend fun makeExcelForReceiptsReport(
        fromDate: String,
        fromTime:String,
        toDate: String,
        toTime:String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<ReceiptResponse>
    ) = excelService.makeExcelForReceiptsReport(
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )

    suspend fun writePdfForPurchaseMastersReport(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PurchaseMastersResponse>,
        purchaseMastersReportListTotals: PurchaseMasterTotals,
        purchaseMasterSelection: PurchaseMasterSelection
    ) = pdfService.writePdfForPurchaseMastersReport(
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list,
        purchaseMastersReportListTotals = purchaseMastersReportListTotals,
        purchaseMasterSelection = purchaseMasterSelection
    )

    suspend fun makeExcelForPurchaseMastersReport(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PurchaseMastersResponse>,
        purchaseMasterSelection: PurchaseMasterSelection
    ) = excelService.makeExcelForPurchaseMastersReport(
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list,
        purchaseMasterSelection = purchaseMasterSelection
    )

    suspend fun writePdfForUserSalesReportByItext(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<UserSalesResponse>
    ) = pdfService.writePdfForUserSalesReportByItext(
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )

    suspend fun makeExcelForUserSalesReport(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<UserSalesResponse>
    ) = excelService.makeExcelForUserSalesReport(
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )

    suspend fun makeExcelForPurchaseSummaryReport(
        fromDate: String,
        fromTime:String,
        toDate: String,
        toTime:String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PurchaseSummaryResponse>
    ) = excelService.makeExcelForPurchaseSummaryReport(
        fromDate = fromDate,
        fromTime = fromTime,
        toDate = toDate,
        toTime = toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )

    suspend fun writePdfForPurchaseSummaryReport(
        fromDate: String,
        fromTime: String,
        toDate: String,
        toTime: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PurchaseSummaryResponse>,
        purchaseSummaryTotals: PurchaseSummaryTotals
    ) = pdfService.writePdfForPurchaseSummaryReport(
        fromDate = fromDate,
        fromTime,
        toDate = toDate,
        toTime,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list,
        purchaseSummaryTotals = purchaseSummaryTotals
    )


}