package com.invest.tracker.core

import com.invest.currency.core.DollarCalculator
import com.invest.currency.core.Dollars
import com.invest.tracker.core.instrument.Instrument

class Portfolio(
    private val holdings: List<Instrument>,
    private val operations: List<Instrument>,
    private val dollarCalculator: DollarCalculator,
) {
    fun getTotalValuation(): Dollars {
        val totalAmount = holdings.sumOf { it.getValuation(dollarCalculator).amount }
        return Dollars(totalAmount)
    }

    fun getROI(): Double {
        val result = (getTotalValuation().amount / getTotalCost().amount) - 1

        if (result.isNaN()) return 0.0
        return result
    }

    private fun getTotalCost(): Dollars {
        val totalCost = operations.sumOf { it.getValuation(dollarCalculator).amount }
        return Dollars(totalCost)
    }
}
