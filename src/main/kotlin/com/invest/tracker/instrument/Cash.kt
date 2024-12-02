package com.invest.tracker.instrument

class Cash(
    val amount: Double,
) : Instrument {
    override fun getValuation(): Double {
        return amount
    }
}
