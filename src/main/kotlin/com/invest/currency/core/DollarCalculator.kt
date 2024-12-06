package com.invest.currency.core

import java.time.LocalDate

class DollarCalculator(
    private val rateProvider: RateProvider,
) {
    fun toPesos(
        dollars: Dollars,
        dollarQuotation: Quotation,
    ): Pesos {
        return Pesos(dollars.amount * rateProvider.getRateForQuotation(dollarQuotation))
    }

    fun toDollars(
        pesos: Pesos,
        dollarQuotation: Quotation,
    ): Dollars {
        return Dollars(pesos.amount / rateProvider.getRateForQuotation(dollarQuotation))
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
