package com.invest.currency.core

class Pesos(val amount: Double) {
    override fun equals(other: Any?): Boolean {
        return other is Pesos && other.amount == amount
    }

    override fun hashCode(): Int {
        return amount.hashCode()
    }
}
