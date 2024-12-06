package com.invest.tracker.core.instrument

class Stock(
    private val ticket: String,
    private val quantity: Int,
    private val price: Double,
) : Instrument {
    override fun getValuation(): Double {
        return quantity * price
    }
}
