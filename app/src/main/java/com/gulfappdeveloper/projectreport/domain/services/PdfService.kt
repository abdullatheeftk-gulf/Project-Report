package com.gulfappdeveloper.projectreport.domain.services

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse
import com.gulfappdeveloper.projectreport.domain.models.sales.PosPaymentResponse
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



}