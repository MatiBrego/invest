package com.invest.tracker.core.instrument

class Cash(
    val amount: Double,
) : Instrument {
    override fun getValuation(): Double {
        return amount
    }
}
