package com.invest.tracker.core.instrument

import com.invest.currency.core.DollarCalculator
import com.invest.currency.core.Dollars

interface Instrument {
    fun getValuation(dollarCalculator: DollarCalculator): Dollars
}
