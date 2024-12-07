package com.invest.tracker.core

import com.invest.currency.core.DollarCalculator
import com.invest.currency.core.Dollars
import com.invest.tracker.core.instrument.Instrument

class Portfolio(private val instruments: List<Instrument>, private val dollarCalculator: DollarCalculator) {
    fun getTotalValuation(): Dollars {
        val totalAmount = instruments.sumOf { it.getValuation(dollarCalculator).amount }
        return Dollars(totalAmount)
    }
}
