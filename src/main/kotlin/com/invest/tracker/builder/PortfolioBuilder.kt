package com.invest.tracker.builder

import com.invest.currency.core.DollarCalculator
import com.invest.tracker.core.Portfolio
import com.invest.tracker.core.instrument.Instrument

class PortfolioBuilder(
    private val operations: List<Instrument>,
    private val dollarCalculator: DollarCalculator,
) {
    fun build(): Portfolio {
        return Portfolio(
            holdings = operations,
            operations = operations,
            dollarCalculator = dollarCalculator,
        )
    }
}
