package com.gulfappdeveloper.projectreport.usecases

import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.customer_payment.GetCustomerPaymentUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.ledger.GetCustomerForLedgerUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.ledger.GetCustomerLedgers
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.license.GetIP4AddressUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.license.UniLicenseActivationUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.purchase.PurchaseMastersReportUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.purchase.SupplierLedgerReportUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.purchase.SupplierPurchaseReportUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.register_and_login_use_cases.LoginUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.register_and_login_use_cases.RegisterCompanyUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.sales.GetPosPaymentReportUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.sales.GetSalesInvoiceReportUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.sales.GetSalesSummariesReportUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.sales.GetUserSalesReportUseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.welcome.WelcomeMessageUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.company_data_use_case.ReadCompanyDataUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.company_data_use_case.SaveCompanyDataUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.device_id_use_cases.ReadDeviceIdUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.device_id_use_cases.SaveDeviceIdUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.ip_address_use_cases.ReadIpAddressUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.ip_address_use_cases.SaveIpAddressUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.operation_counter_use_cases.ReadOperationCountUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.operation_counter_use_cases.UpdateOperationCountUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.uni_license_use_cases.ReadUniLicenseUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.uni_license_use_cases.SaveUniLicenseUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.user_name_use_case.ReadUserNameUseCase
import com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.user_name_use_case.SaveUserNameUseCase
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.customer_ledger_report.ExcelMakerCustomerLedgerReportUseCase
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.customer_ledger_report.PdfMakerCustomerLedgerReportUseCase
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.customer_payment_report.ExcelMakerUseCaseForCustomerPaymentReport
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.customer_payment_report.PdfMakerUseCaseForCustomerPaymentReport
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.pos_payment_report.ExcelMakerUseCaseForPosPaymentReport
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.pos_payment_report.PdfMakerUseCaseForPosPaymentReport
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.supplier_ledger_report.ExcelMakerForSupplierLedgerReportUseCase
import com.gulfappdeveloper.projectreport.usecases.pdf_excel_maker_use_case.supplier_ledger_report.PdfMakerSupplierLedgerReportUseCase
import com.gulfappdeveloper.projectreport.usecases.room_use_case.GetAllLocalCompanyData
import com.gulfappdeveloper.projectreport.usecases.room_use_case.RoomInsertDataUseCase

data class UseCase(

    val welcomeMessageUseCase: WelcomeMessageUseCase,

    val uniLicenseActivationUseCase: UniLicenseActivationUseCase,

    val getIP4AddressUseCase: GetIP4AddressUseCase,

    val registerCompanyUseCase: RegisterCompanyUseCase,

    val loginUseCase: LoginUseCase,






    // Data store use cases
    val saveIpAddressUseCase: SaveIpAddressUseCase,
    val readIpAddressUseCase: ReadIpAddressUseCase,

    val saveDeviceIdUseCase: SaveDeviceIdUseCase,
    val readDeviceIdUseCase: ReadDeviceIdUseCase,

    val updateOperationCountUseCase: UpdateOperationCountUseCase,
    val readOperationCountUseCase: ReadOperationCountUseCase,

    val saveUniLicenseUseCase: SaveUniLicenseUseCase,
    val readUniLicenseUseCase: ReadUniLicenseUseCase,

    val saveCompanyDataUseCase: SaveCompanyDataUseCase,
    val readCompanyDataUseCase: ReadCompanyDataUseCase,

    val saveUserNameUseCase: SaveUserNameUseCase,
    val readUserNameUseCase: ReadUserNameUseCase,


    // Room
    val roomInsertDataUseCase: RoomInsertDataUseCase,
    val getAllLocalCompanyData: GetAllLocalCompanyData,

    // ledger
    val getCustomerForLedgerUseCase: GetCustomerForLedgerUseCase,
    val getCustomerLedgers: GetCustomerLedgers,

    // customer payment
    val getCustomerPaymentUseCase: GetCustomerPaymentUseCase,

    // Sales api
    val getSalesInvoiceReportUseCase: GetSalesInvoiceReportUseCase,
    val getSalesSummariesReportUseCase: GetSalesSummariesReportUseCase,
    val getUserSalesReportUseCase: GetUserSalesReportUseCase,
    val getPosPaymentReportUseCase: GetPosPaymentReportUseCase,

    val pdfMakerUseCaseForCustomerPaymentReport: PdfMakerUseCaseForCustomerPaymentReport,
    val excelMakerUseCaseForCustomerPaymentReport: ExcelMakerUseCaseForCustomerPaymentReport,

    val pdfMakerUseCaseForPosPaymentReport: PdfMakerUseCaseForPosPaymentReport,
    val excelMakerUseCasePosPaymentReportUseCase: ExcelMakerUseCaseForPosPaymentReport,

    val pdfMakerCustomerLedgerReportUseCase: PdfMakerCustomerLedgerReportUseCase,
    val excelMakerCustomerLedgerReportUseCase: ExcelMakerCustomerLedgerReportUseCase,

    val pdfMakerSupplierLedgerReportUseCase: PdfMakerSupplierLedgerReportUseCase,
    val excelMakerForSupplierLedgerReportUseCase: ExcelMakerForSupplierLedgerReportUseCase,

    val purchaseMastersReportUseCase: PurchaseMastersReportUseCase,
    val supplierPurchaseReportUseCase: SupplierPurchaseReportUseCase,
    val supplierLedgerReportUseCase: SupplierLedgerReportUseCase


)