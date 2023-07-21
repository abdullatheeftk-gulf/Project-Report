package com.gulfappdeveloper.projectreport.repositories

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.accounts.PaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.accounts.ReceiptResponse
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.purchase.PurchaseMastersResponse
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
        toDate: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) = pdfService.writePdfAndShareItForCustomerPaymentReport(
        list = list,
        listOfTotal = listOfTotal,
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError
    )

    suspend fun writeExcelForCustomerPaymentReport(
        list: List<CustomerPaymentResponse>,
        fromDate: String,
        toDate: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) = excelService.makeExcelForCustomerPaymentReport(
        list = list,
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError
    )

    suspend fun writePdfAndShareItForPosPaymentReport(
        list: List<PosPaymentResponse>,
        listOfTotal: List<Double>,
        fromDate: String,
        toDate: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) = pdfService.writePdfAndShareItForPosPaymentReport(
        list = list,
        listOfTotal = listOfTotal,
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError
    )


    suspend fun writePdfForCustomerLedgerReport(
        list: List<ReArrangedCustomerLedgerDetails>,
        customerLedgerTotals: CustomerLedgerTotals,
        partyName: String,
        balance: Float,
        fromDate: String,
        toDate: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) = pdfService.writePdfForCustomerLedgerReport(
        list = list,
        customerLedgerTotals = customerLedgerTotals,
        fromDate = fromDate,
        toDate = toDate,
        partyName = partyName,
        balance = balance,
        getUri = getUri,
        haveAnyError = haveAnyError
    )

    suspend fun writePdfForSupplierLedgerReport(
        list: List<ReArrangedSupplierLedgerDetail>,
        supplierLedgerTotal: SupplierLedgerTotals,
        fromDate: String,
        toDate: String,
        partyName: String,
        balance: Float,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) = pdfService.writePdfForSupplierLedgerReport(
        list = list,
        supplierLedgerTotal = supplierLedgerTotal,
        fromDate = fromDate,
        toDate = toDate,
        partyName = partyName,
        balance = balance,
        getUri = getUri,
        haveAnyError = haveAnyError
    )


    suspend fun writeExcelForPosPaymentReport(
        list: List<PosPaymentResponse>,
        fromDate: String,
        toDate: String,
        getUri: (uri: Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) = excelService.makeExcelForPosPaymentReport(
        list = list,
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError
    )

    suspend fun makeExcelForCustomerLedgerReport(
        list: List<ReArrangedCustomerLedgerDetails>,
        partyName: String,
        balance: Float,
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (isError: Boolean, errorString: String?) -> Unit
    ) = excelService.makeExcelForCustomerLedgerReport(
        list = list,
        partyName = partyName,
        balance = balance,
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError
    )

    suspend fun makeExcelForSupplierLedgerReport(
        list: List<ReArrangedSupplierLedgerDetail>,
        partyName: String,
        balance: Float,
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (isError: Boolean, errorString: String?) -> Unit
    ) = excelService.makeExcelForSupplierLedgerReport(
        list = list,
        partyName = partyName,
        balance = balance,
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError
    )


    suspend fun writePdfForSalesInvoiceReport(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<SalesInvoiceResponse>,
        salesInvoiceReportTotals: SalesInvoiceReportTotals
    ) = pdfService.writePdfForSalesInvoiceReport(
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list,
        salesInvoiceReportTotals = salesInvoiceReportTotals
    )

    suspend fun writePdfForSaleSummariesReport(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<SaleSummariesResponse>,
        saleSummariesReportTotals: SaleSummariesReportTotals
    ) = pdfService.writePdfForSaleSummariesReport(
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list,
        saleSummariesReportTotals = saleSummariesReportTotals
    )

    suspend fun makeExcelForSalesInvoiceReport(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<SalesInvoiceResponse>
    ) = excelService.makeExcelForSalesInvoiceReport(
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )

    suspend fun makeExcelForSaleSummariesReport(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<SaleSummariesResponse>
    ) = excelService.makeExcelForSaleSummariesReport(
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )

    suspend fun writePdfForExpenseLedgerReport(
        list: List<ReArrangedExpenseLedgerDetail>,
        expenseLedgerTotal: ExpenseLedgerReportTotals,
        fromDate: String,
        toDate: String,
        partyName: String,
        balance: Float,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
    ) = pdfService.writePdfForExpenseLedgerReport(
        list = list,
        expenseLedgerTotal = expenseLedgerTotal,
        fromDate = fromDate,
        toDate = toDate,
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
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (isError: Boolean, errorString: String?) -> Unit
    ) = excelService.makeExcelForExpenseLedgerReport(
        list = list,
        partyName = partyName,
        balance = balance,
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError
    )

    suspend fun writePdfForPaymentsReport(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PaymentResponse>,
        paymentReportListTotal: Double
    ) = pdfService.writePdfForPaymentsReport(
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list,
        paymentReportListTotal = paymentReportListTotal
    )

    suspend fun writePdfForReceiptsReport(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<ReceiptResponse>,
        receiptReportListTotal: Double
    ) = pdfService.writePdfForReceiptsReport(
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list,
        receiptReportListTotal = receiptReportListTotal
    )

    suspend fun makeExcelForPaymentsReport(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PaymentResponse>
    ) = excelService.makeExcelForPaymentsReport(
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )


    suspend fun makeExcelForReceiptsReport(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<ReceiptResponse>
    ) = excelService.makeExcelForReceiptsReport(
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )

    suspend fun writePdfForPurchaseMastersReport(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PurchaseMastersResponse>,
        purchaseMastersReportListTotals: PurchaseMasterTotals,
        purchaseMasterSelection: PurchaseMasterSelection
    ) = pdfService.writePdfForPurchaseMastersReport(
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list,
        purchaseMastersReportListTotals = purchaseMastersReportListTotals,
        purchaseMasterSelection = purchaseMasterSelection
    )

    suspend fun makeExcelForPurchaseMastersReport(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PurchaseMastersResponse>,
        purchaseMasterSelection: PurchaseMasterSelection
    ) = excelService.makeExcelForPurchaseMastersReport(
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list,
        purchaseMasterSelection = purchaseMasterSelection
    )

    suspend fun writePdfForUserSalesReportByItext(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<UserSalesResponse>
    ) = pdfService.writePdfForUserSalesReportByItext(
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )

    suspend fun makeExcelForUserSalesReport(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<UserSalesResponse>
    ) = excelService.makeExcelForUserSalesReport(
        fromDate = fromDate,
        toDate = toDate,
        getUri = getUri,
        haveAnyError = haveAnyError,
        list = list
    )


}