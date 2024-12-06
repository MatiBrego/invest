package com.invest.tracker.core

class Portfolio(private val list: List<Holding>) {
    fun getTotalValuation(): Double {
        return list.sumOf { it.getValuation() }
    }
}
