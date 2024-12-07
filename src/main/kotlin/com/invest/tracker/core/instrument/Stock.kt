package com.invest.tracker.core.instrument

import com.invest.currency.core.DollarCalculator
import com.invest.currency.core.Dollars
import com.invest.currency.core.Money
import com.invest.currency.core.Pesos
import com.invest.currency.core.Quotation

class Stock(
    private val ticket: String,
    private val quantity: Int,
    private val price: Money,
) : Instrument {
    override fun getValuation(dollarCalculator: DollarCalculator): Dollars {
        return when (price) {
            is Dollars -> price * quantity.toDouble()
            is Pesos -> dollarCalculator.toDollars(price * quantity.toDouble(), Quotation.CCL)
        }
    }
}
