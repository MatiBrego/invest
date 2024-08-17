package com.invest.rest

import com.invest.currency.core.Quotation

fun withAmountAndQuotation(
    uri: String,
    amount: Double,
    quotation: Quotation,
): String {
    return "$uri?amount=$amount&quotation=$quotation"
}
