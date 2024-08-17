package com.invest.rest.route

import com.invest.currency.core.DollarCalculator
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
            return dollarCalculator.toDollars(amount, quotation)
        }

        override fun toPeso(
            amount: Double,
            quotation: Quotation,
        ): Double {
            return dollarCalculator.toPesos(amount, quotation)
        }

        override fun toHistoricDollar(
            amount: Double,
            quotation: Quotation,
            date: LocalDate,
        ): Double {
            return dollarCalculator.toHistoricDollar(amount, quotation, date)
        }

        override fun toHistoricPeso(
            amount: Double,
            quotation: Quotation,
            date: LocalDate,
        ): Double {
            return dollarCalculator.toHistoricPesos(amount, quotation, date)
        }
    }
