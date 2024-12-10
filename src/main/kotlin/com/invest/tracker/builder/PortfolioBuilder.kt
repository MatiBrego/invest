package com.invest.tracker.builder

import com.invest.currency.core.DollarCalculator
import com.invest.tracker.builder.aggregator.InstrumentAggregator
import com.invest.tracker.core.Portfolio
import com.invest.tracker.core.instrument.Instrument

class PortfolioBuilder(
    private val operations: List<Instrument>,
    private val dollarCalculator: DollarCalculator,
    private val aggregators: List<InstrumentAggregator>,
) {
    fun build(): Portfolio {
        return Portfolio(
            holdings = calculateHoldings(),
            operations = operations,
            dollarCalculator = dollarCalculator,
        )
    }

    private fun calculateHoldings(): List<Instrument> {
        var aggregatedOperations = operations
        for (aggregator in aggregators) {
            aggregatedOperations = aggregator.aggregate(aggregatedOperations)
        }
        return aggregatedOperations
    }
}
