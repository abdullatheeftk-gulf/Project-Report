package com.gulfappdeveloper.projectreport.repositories

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.PosPaymentResponse
import com.gulfappdeveloper.projectreport.domain.services.ExcelService
import com.gulfappdeveloper.projectreport.domain.services.PdfService
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.ReArrangedSupplierLedgerDetail
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.SupplierLedgerTotals
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.CustomerLedgerTotals
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.ReArrangedCustomerLedgerDetails
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
    )=pdfService.writePdfForSupplierLedgerReport(
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



}