package com.invest.tracker.core.instrument

import com.invest.currency.core.DollarCalculator
import com.invest.currency.core.Dollars
import com.invest.currency.core.Money
import com.invest.currency.core.Pesos
import com.invest.currency.core.Quotation

class Cash(
    private val money: Money,
) : Instrument {
    override fun getValuation(dollarCalculator: DollarCalculator): Dollars {
        return when (money) {
            is Dollars -> money
            is Pesos -> dollarCalculator.toDollars(money, Quotation.CCL)
        }
    }
}
