package com.invest.tracker

import com.invest.tracker.instrument.Instrument

class Holding(
    private val instrument: Instrument,
) {
    fun getValuation(): Double {
        return instrument.getValuation()
    }
}
