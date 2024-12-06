package com.invest.tracker.core

import com.invest.tracker.core.instrument.Instrument

class Holding(
    private val instrument: Instrument,
) {
    fun getValuation(): Double {
        return instrument.getValuation()
    }
}
