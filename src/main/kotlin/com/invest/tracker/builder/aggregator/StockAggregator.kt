package com.invest.tracker.builder.aggregator

import com.invest.currency.core.Dollars
import com.invest.tracker.core.instrument.Instrument
import com.invest.tracker.core.instrument.Stock

class StockAggregator(
    private val priceMap: Map<String, Dollars>,
) : InstrumentAggregator {
    override fun aggregate(instruments: List<Instrument>): List<Instrument> {
        val stocks = instruments.filterIsInstance<Stock>()
        val other = instruments.filterNot { it is Stock }

        val aggregatedStocks = aggregateStocks(stocks)

        return aggregatedStocks + other
    }

    private fun aggregateStocks(stocks: List<Stock>): List<Stock>  {
        val stockMap = mutableMapOf<String, Stock>()

        for (stock in stocks) {
            val existingStock = stockMap[stock.ticket]
            if (existingStock != null) {
                stockMap[stock.ticket] = createAggregatedStock(existingStock, stock)
            } else {
                stockMap[stock.ticket] = stock
            }
        }

        return stockMap.values.toList()
    }

    private fun createAggregatedStock(
        existingStock: Stock,
        newStock: Stock,
    ): Stock {
        val newQuantity = existingStock.quantity + newStock.quantity
        val newPrice =
            this.priceMap.getOrElse(newStock.ticket) {
                throw IllegalArgumentException("Price for stock ${newStock.ticket} not found")
            }
        return Stock(
            ticket = newStock.ticket,
            quantity = newQuantity,
            price = newPrice,
        )
    }
}
