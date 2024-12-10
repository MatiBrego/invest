package com.invest.tracker.builder

import com.invest.tracker.core.instrument.Instrument

interface InstrumentAggregator {
    fun aggregate(instruments: List<Instrument>): List<Instrument>
}
