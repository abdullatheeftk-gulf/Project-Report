package com.gulfappdeveloper.projectreport.share.excel

import android.content.Context
import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.PosPaymentResponse
import com.gulfappdeveloper.projectreport.domain.services.ExcelService
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.ReArrangedSupplierLedgerDetail
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.ReArrangedCustomerLedgerDetails
import com.gulfappdeveloper.projectreport.root.CommonMemory
import com.gulfappdeveloper.projectreport.share.excel.sales.customer_ledger_report.CustomerLedgerReportExcel
import com.gulfappdeveloper.projectreport.share.excel.sales.customer_payment_report.CustomerPaymentReportExcel
import com.gulfappdeveloper.projectreport.share.excel.sales.pos_payment_report.PosPaymentReportExcel

class ExcelServiceImpl(
    private val context: Context,
    private val commonMemory: CommonMemory
) : ExcelService {

    override suspend fun makeExcelForCustomerPaymentReport(
        list: List<CustomerPaymentResponse>,
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (Boolean, String?) -> Unit
    ) {
        CustomerPaymentReportExcel.writeExcelSheet(
            context = context,
            list = list,
            haveAnyError = haveAnyError,
            fromDate = fromDate,
            toDate = toDate,
            getUri = getUri

        )
    }

    override suspend fun makeExcelForPosPaymentReport(
        list: List<PosPaymentResponse>,
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (Boolean, String?) -> Unit
    ) {
        PosPaymentReportExcel.writeExcelSheet(
            companyName = commonMemory.companyName,
            context = context,
            fromDate = fromDate,
            toDate = toDate,
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
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (isError: Boolean, errorString: String?) -> Unit
    ) {
        CustomerLedgerReportExcel.writeExcelSheet(
            companyName = commonMemory.companyName,
            partyName = partyName,
            balance = balance,
            context = context,
            fromDate = fromDate,
            toDate = toDate,
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
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (isError: Boolean, errorString: String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}