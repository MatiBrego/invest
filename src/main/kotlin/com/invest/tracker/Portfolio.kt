package com.invest.tracker

class Portfolio(private val list: List<Holding>) {
    fun getTotalValuation(): Double {
        return list.sumOf { it.getValuation() }
    }
}
