package com.gulfappdeveloper.projectreport.domain.services

import android.net.Uri
import com.gulfappdeveloper.projectreport.domain.models.customer_payment.CustomerPaymentResponse

interface PdfService {
   suspend fun writePdfAndShareItForCustomerPaymentReport(
      list: List<CustomerPaymentResponse>,
      fromDate:String,
      toDate:String,
      getUri:(Uri)->Unit,
      haveAnyError: (haveAnyError: Boolean, error: String?) -> Unit
   )
}