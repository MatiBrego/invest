package com.invest.tracker.builder

import com.invest.currency.core.Dollars
import com.invest.tracker.core.instrument.Instrument
import com.invest.tracker.core.instrument.Stock

class StockAggregator(
    private val priceMap: Map<String, Dollars>,
) : InstrumentAggregator {
    override fun aggregate(instruments: List<Instrument>): List<Instrument> {
        val stocks = instruments.filterIsInstance<Stock>()
        val other = instruments.filterNot { it is Stock }

        val stockMap = mutableMapOf<String, Stock>()

        for (stock in stocks) {
            val existingStock = stockMap[stock.ticket]
            if (existingStock != null) {
                val newQuantity = existingStock.quantity + stock.quantity
                val newPrice =
                    this.priceMap.getOrElse(stock.ticket) {
                        throw IllegalArgumentException("Price for stock ${stock.ticket} not found")
                    }
                stockMap[stock.ticket] =
                    Stock(
                        ticket = stock.ticket,
                        quantity = newQuantity,
                        price = newPrice,
                    )
            } else {
                stockMap[stock.ticket] = stock
            }
        }

        return stockMap.values.toList() + other
    }
}
