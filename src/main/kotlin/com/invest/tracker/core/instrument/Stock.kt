package com.invest.tracker.core.instrument

import com.invest.currency.core.DollarCalculator
import com.invest.currency.core.Dollars
import com.invest.currency.core.Money
import com.invest.currency.core.Pesos
import com.invest.currency.core.Quotation
import java.time.LocalDate

class Stock(
    val ticket: String,
    val quantity: Int,
    val price: Money,
    val date: LocalDate? = null,
) : Instrument {
    override fun getValuation(dollarCalculator: DollarCalculator): Dollars {
        when (price) {
            is Dollars -> return price * quantity.toDouble()
            is Pesos -> {
                if (date != null) {
                    return dollarCalculator.toHistoricDollar(
                        pesos = price * quantity.toDouble(),
                        quotation = Quotation.CCL,
                        date = date,
                    )
                }
                return dollarCalculator.toDollars(
                    pesos = price * quantity.toDouble(),
                    quotation = Quotation.CCL,
                )
            }
        }
    }
}
