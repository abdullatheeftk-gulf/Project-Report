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
import kotlinx.coroutines.flow.Flow

interface PdfService {
   suspend fun writePdfAndShareItForCustomerPaymentReport(
      list: List<CustomerPaymentResponse>,
      listOfTotal: List<Double>,
      fromDate:String,
      toDate:String,
      getUri:(Uri)->Unit,
      haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
   )

   suspend fun writePdfAndShareItForPosPaymentReport(
      list: List<PosPaymentResponse>,
      listOfTotal: List<Double>,
      fromDate:String,
      toDate:String,
      getUri:(Uri)->Unit,
      haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
   )

   suspend fun writePdfForCustomerLedgerReport(
      list:List<ReArrangedCustomerLedgerDetails>,
      customerLedgerTotals: CustomerLedgerTotals,
      fromDate: String,
      toDate: String,
      partyName:String,
      balance:Float,
      getUri: (Uri) -> Unit,
      haveAnyError: (haveAnyError:Boolean,error:String?)->Unit
   )

   suspend fun writePdfForSupplierLedgerReport(
      list:List<ReArrangedSupplierLedgerDetail>,
      supplierLedgerTotal: SupplierLedgerTotals,
      fromDate: String,
      toDate: String,
      partyName:String,
      balance:Float,
      getUri: (Uri) -> Unit,
      haveAnyError: (haveAnyError:Boolean,error:String?)->Unit
   )

   suspend fun writePdfForExpenseLedgerReport(
      list:List<ReArrangedExpenseLedgerDetail>,
      expenseLedgerTotal: ExpenseLedgerReportTotals,
      fromDate: String,
      toDate: String,
      partyName:String,
      balance:Float,
      getUri: (Uri) -> Unit,
      haveAnyError: (haveAnyError:Boolean,error:String?)->Unit
   )

   suspend fun writePdfForSalesInvoiceReport(
      fromDate: String,
      toDate: String,
      getUri: (Uri) -> Unit,
      haveAnyError: (haveAnyError:Boolean,error:String?)->Unit,
      list:List<SalesInvoiceResponse>,
      salesInvoiceReportTotals:SalesInvoiceReportTotals
   )
   suspend fun writePdfForSaleSummariesReport(
      fromDate: String,
      toDate: String,
      getUri: (Uri) -> Unit,
      haveAnyError: (haveAnyError:Boolean,error:String?)->Unit,
      list:List<SaleSummariesResponse>,
      saleSummariesReportTotals: SaleSummariesReportTotals
   )

   suspend fun writePdfForPaymentsReport(
      fromDate: String,
      toDate: String,
      getUri: (Uri) -> Unit,
      haveAnyError: (haveAnyError:Boolean,error:String?)->Unit,
      list:List<PaymentResponse>,
      paymentReportListTotal:Double
   )

   suspend fun writePdfForReceiptsReport(
      fromDate: String,
      toDate: String,
      getUri: (Uri) -> Unit,
      haveAnyError: (haveAnyError:Boolean,error:String?)->Unit,
      list:List<ReceiptResponse>,
      receiptReportListTotal:Double
   )

   suspend fun writePdfForPurchaseMastersReport(
      fromDate: String,
      toDate: String,
      getUri: (Uri) -> Unit,
      haveAnyError: (haveAnyError:Boolean,error:String?)->Unit,
      list:List<PurchaseMastersResponse>,
      purchaseMastersReportListTotals:PurchaseMasterTotals,
      purchaseMasterSelection: PurchaseMasterSelection
   )

   suspend fun writePdfForUserSalesReportByItext(
      fromDate: String,
      toDate: String,
      getUri: (Uri) -> Unit,
      haveAnyError: (haveAnyError:Boolean,error:String?)->Unit,
      list:List<UserSalesResponse>,
   )

   suspend fun writePdfForPurchaseSummaryReport(
      fromDate: String,
      toDate: String,
      getUri: (Uri) -> Unit,
      haveAnyError: (haveAnyError:Boolean,error:String?)->Unit,
      list:List<PurchaseSummaryResponse>,
      purchaseSummaryTotals: PurchaseSummaryTotals,
   )






}