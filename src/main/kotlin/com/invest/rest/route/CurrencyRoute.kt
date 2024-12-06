package com.invest.rest.route

import com.invest.currency.core.DollarCalculator
import com.invest.currency.core.Dollars
import com.invest.currency.core.Pesos
import com.invest.currency.core.Quotation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class CurrencyRoute
    @Autowired
    constructor(
        private val dollarCalculator: DollarCalculator,
    ) : CurrencyRouteSpec {
        override fun toDollar(
            amount: Double,
            quotation: Quotation,
        ): Double {
            return dollarCalculator.toDollars(Pesos(amount), quotation).amount
        }

        override fun toPeso(
            amount: Double,
            quotation: Quotation,
        ): Double {
            return dollarCalculator.toPesos(Dollars(amount), quotation).amount
        }

        override fun toHistoricDollar(
            amount: Double,
            quotation: Quotation,
            date: LocalDate,
        ): Double {
            return dollarCalculator.toHistoricDollar(Pesos(amount), quotation, date).amount
        }

        override fun toHistoricPeso(
            amount: Double,
            quotation: Quotation,
            date: LocalDate,
        ): Double {
            return dollarCalculator.toHistoricPesos(Dollars(amount), quotation, date).amount
        }
    }
