package com.gulfappdeveloper.projectreport.domain.services

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
import com.gulfappdeveloper.projectreport.presentation.screens.account_screens.account_models.ReArrangedExpenseLedgerDetail
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.PurchaseMasterSelection
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

    suspend fun makeExcelForSalesInvoiceReport(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError:Boolean,error:String?)->Unit,
        list:List<SalesInvoiceResponse>
    )

    suspend fun makeExcelForSaleSummariesReport(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError:Boolean,error:String?)->Unit,
        list:List<SaleSummariesResponse>
    )

    suspend fun makeExcelForExpenseLedgerReport(
        list: List<ReArrangedExpenseLedgerDetail>,
        partyName:String,
        balance:Float,
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (isError:Boolean, errorString:String?) -> Unit
    )

    suspend fun makeExcelForPaymentsReport(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError:Boolean,error:String?)->Unit,
        list:List<PaymentResponse>,
    )

    suspend fun makeExcelForReceiptsReport(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError:Boolean,error:String?)->Unit,
        list:List<ReceiptResponse>,
    )

    suspend fun makeExcelForPurchaseMastersReport(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PurchaseMastersResponse>,
        purchaseMasterSelection: PurchaseMasterSelection
    )
    suspend fun makeExcelForUserSalesReport(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<UserSalesResponse>,
    )

    suspend fun makeExcelForPurchaseSummaryReport(
        fromDate: String,
        toDate: String,
        getUri: (Uri) -> Unit,
        haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit,
        list: List<PurchaseSummaryResponse>,
    )

}