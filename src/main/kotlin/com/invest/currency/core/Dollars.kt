package com.invest.currency.core

class Dollars(val amount: Double) {
    override fun equals(other: Any?): Boolean {
        return other is Dollars && other.amount == amount
    }

    override fun hashCode(): Int {
        return amount.hashCode()
    }
}
