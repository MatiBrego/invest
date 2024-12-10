package com.invest.currency.core

import java.time.LocalDate

class DollarCalculator(
    private val rateProvider: RateProvider,
) {
    fun toPesos(
        dollars: Dollars,
        quotation: Quotation,
    ): Pesos {
        return Pesos(dollars.amount * rateProvider.getRateForQuotation(quotation))
    }

    fun toDollars(
        pesos: Pesos,
        quotation: Quotation,
    ): Dollars {
        return Dollars(pesos.amount / rateProvider.getRateForQuotation(quotation))
    }

    fun toHistoricPesos(
        dollars: Dollars,
        quotation: Quotation,
        date: LocalDate,
    ): Pesos {
        return Pesos(dollars.amount * rateProvider.getHistoricRateForQuotation(quotation, date))
    }

    fun toHistoricDollar(
        pesos: Pesos,
        quotation: Quotation,
        date: LocalDate,
    ): Dollars {
        return Dollars(pesos.amount / rateProvider.getHistoricRateForQuotation(quotation, date))
    }
}
