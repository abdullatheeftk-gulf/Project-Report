package com.gulfappdeveloper.projectreport.domain.services

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.PosPaymentResponse
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.ReArrangedSupplierLedgerDetail
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.ReArrangedCustomerLedgerDetails

interface ExcelService {
    suspend fun makeExcelForCustomerPaymentReport(
        list: List<CustomerPaymentResponse>,
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (Boolean, String?) -> Unit
    )
    suspend fun makeExcelForPosPaymentReport(
        list: List<PosPaymentResponse>,
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (Boolean, String?) -> Unit
    )

    suspend fun makeExcelForCustomerLedgerReport(
        list: List<ReArrangedCustomerLedgerDetails>,
        partyName:String,
        balance:Float,
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (isError:Boolean, errorString:String?) -> Unit
    )

    suspend fun makeExcelForSupplierLedgerReport(
        list: List<ReArrangedSupplierLedgerDetail>,
        partyName:String,
        balance:Float,
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (isError:Boolean, errorString:String?) -> Unit
    )
}