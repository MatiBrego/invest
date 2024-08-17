package com.invest.rest.route

import com.invest.currency.core.DollarCalculator
import com.invest.currency.core.Quotation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController

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
    }
