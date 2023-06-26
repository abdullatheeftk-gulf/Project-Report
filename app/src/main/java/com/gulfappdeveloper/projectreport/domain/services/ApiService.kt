package com.gulfappdeveloper.projectreport.domain.services

import com.gulfappdeveloper.projectreport.domain.models.accounts.ExpenseLedgerReportResponse
import com.gulfappdeveloper.projectreport.domain.models.accounts.PaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.accounts.ReceiptResponse
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.general.GetDataFromRemote
import com.gulfappdeveloper.projectreport.domain.models.ip4.SeeIp
import com.gulfappdeveloper.projectreport.domain.models.ledger.GetCustomerForLedgerReportResponse
import com.gulfappdeveloper.projectreport.domain.models.ledger.LedgerReportResponse
import com.gulfappdeveloper.projectreport.domain.models.license.LicenseRequestBody
import com.gulfappdeveloper.projectreport.domain.models.license.LicenseResponse
import com.gulfappdeveloper.projectreport.domain.models.login_and_register.CompanyRegisterResponse
import com.gulfappdeveloper.projectreport.domain.models.login_and_register.LoginResponse
import com.gulfappdeveloper.projectreport.domain.models.purchase.PurchaseMastersResponse
import com.gulfappdeveloper.projectreport.domain.models.purchase.supplier_ledger_report.SupplierLedgerReportResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.PosPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.SaleSummariesResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.SalesInvoiceResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.UserSalesResponse
import kotlinx.coroutines.flow.Flow

interface ApiService {
    suspend fun getWelcomeMessage(url: String): Flow<GetDataFromRemote<String>>

    suspend fun uniLicenseActivation(
        rioLabKey: String,
        url: String,
        licenseRequestBody: LicenseRequestBody
    ): Flow<GetDataFromRemote<LicenseResponse>>

    suspend fun getIp4Address(url: String): Flow<GetDataFromRemote<SeeIp>>

    suspend fun registerCompany(url: String): Flow<GetDataFromRemote<CompanyRegisterResponse>>

    suspend fun login(url: String): Flow<GetDataFromRemote<LoginResponse>>


    // ledger
    suspend fun getCustomerForLedger(url: String): Flow<GetDataFromRemote<List<GetCustomerForLedgerReportResponse>>>
    suspend fun getCustomerLedgers(url: String): Flow<GetDataFromRemote<LedgerReportResponse>>


    // customer payment
    suspend fun getCustomerPayment(url: String): Flow<GetDataFromRemote<List<CustomerPaymentResponse>>>

    // Sales
    suspend fun getSaleSummariesReport(url: String): Flow<GetDataFromRemote<List<SaleSummariesResponse>>>
    suspend fun getSalesInvoiceReport(url: String): Flow<GetDataFromRemote<List<SalesInvoiceResponse>>>
    suspend fun getUserSalesReport(url: String): Flow<GetDataFromRemote<List<UserSalesResponse>>>
    suspend fun getPosPaymentReport(url: String): Flow<GetDataFromRemote<List<PosPaymentResponse>>>

    // Purchase
    suspend fun getPurchaseMastersReport(url: String): Flow<GetDataFromRemote<List<PurchaseMastersResponse>>>
    suspend fun getSupplierPurchaseReport(url: String): Flow<GetDataFromRemote<List<PurchaseMastersResponse>>>
    suspend fun getSupplierLedgerReport(url:String) :Flow<GetDataFromRemote<SupplierLedgerReportResponse>>

    // Accounts
    suspend fun getExpenseLedgerReport(url: String,dateFrom:String,dateTo:String,expenseId:Int,companyId:Int):Flow<GetDataFromRemote<ExpenseLedgerReportResponse>>
    suspend fun getPaymentsReport(url: String,dateFrom:String,dateTo:String,companyId:Int):Flow<GetDataFromRemote<List<PaymentResponse>>>
    suspend fun getReceiptReport(url: String,dateFrom:String,dateTo:String,companyId:Int):Flow<GetDataFromRemote<List<ReceiptResponse>>>
}