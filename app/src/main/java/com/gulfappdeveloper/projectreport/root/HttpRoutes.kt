package com.gulfappdeveloper.projectreport.root

object HttpRoutes {
    const val BASE_URL = "http://sales.the360pos.com"

    const val WELCOME_MESSAGE = "/api/oem"

    const val REGISTER_COMPANY = "/api/companies"

    const val LOGIN = "/api/UserDetails"


    const val UNI_LICENSE_HEADER = "riolab123456"
    const val UNI_LICENSE_ACTIVATION_URL =
        "http://license.riolabz.com/license-repo/public/api/v1/verifyjson"

    const val SEE_IP4 = "https://ip4.seeip.org/json"



    // ledger
    const val GET_CUSTOMER_FOR_LEDGER = "/api/Accounts/"
    const val LEDGER_REPORT = "/api/customerLedgers/"

    // Customer payment
    const val GET_CUSTOMER_PAYMENT_REPORT = "/api/customerPayment/"

    // Sales


}