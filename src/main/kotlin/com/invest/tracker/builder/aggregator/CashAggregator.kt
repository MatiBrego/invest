package com.invest.tracker.builder.aggregator

import com.invest.currency.core.DollarCalculator
import com.invest.currency.core.Dollars
import com.invest.tracker.core.instrument.Cash
import com.invest.tracker.core.instrument.Instrument

class CashAggregator(
    val dollarCalculator: DollarCalculator,
) : InstrumentAggregator {
    override fun aggregate(instruments: List<Instrument>): List<Instrument> {
        val cash = instruments.filterIsInstance<Cash>()
        val other = instruments.filterNot { it is Cash }

        val totalDollars =
            cash.sumOf { it.getValuation(dollarCalculator).amount }

        val result = if (totalDollars > 0) listOf(Cash(Dollars(totalDollars))) else emptyList()

        return result + other
    }
}
