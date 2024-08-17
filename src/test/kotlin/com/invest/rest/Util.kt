package com.invest.rest

import com.invest.currency.core.Quotation
import java.time.LocalDate

fun withAmountAndQuotation(
    uri: String,
    amount: Double,
    quotation: Quotation,
): String {
    return "$uri?amount=$amount&quotation=$quotation"
}

fun withAmountQuotationAndDate(
    uri: String,
    amount: Double,
    quotation: Quotation,
    date: LocalDate
): String {
    val withAmountAndQuotation = withAmountAndQuotation(uri, amount, quotation)

    return "${withAmountAndQuotation}&date=${date}"
}