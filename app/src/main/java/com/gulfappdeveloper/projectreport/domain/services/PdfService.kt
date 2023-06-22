package com.gulfappdeveloper.projectreport.domain.services

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.PosPaymentResponse
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.ReArrangedSupplierLedgerDetail
import com.gulfappdeveloper.projectreport.presentation.screens.purchase_screens.purchase_models.SupplierLedgerTotals
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.CustomerLedgerTotals
import com.gulfappdeveloper.projectreport.presentation.screens.sales_screens.sales_models.ReArrangedCustomerLedgerDetails
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



}