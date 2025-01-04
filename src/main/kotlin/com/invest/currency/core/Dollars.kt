package com.invest.currency.core

import kotlin.math.roundToInt

class Dollars(val amount: Double) : Money {
    operator fun plus(other: Dollars): Dollars {
        return Dollars(amount + other.amount)
    }

    operator fun minus(other: Dollars): Dollars {
        return Dollars(amount - other.amount)
    }

    operator fun times(multiplier: Double): Dollars {
        return Dollars(amount * multiplier)
    }

    operator fun times(multiplier: Int): Dollars {
        return Dollars(amount * multiplier)
    }

    operator fun div(divisor: Double): Dollars {
        require(divisor != 0.0) { "Division by zero is not allowed." }
        return Dollars(amount / divisor)
    }

    operator fun div(divisor: Int): Dollars {
        require(divisor != 0) { "Division by zero is not allowed." }
        return Dollars(amount / divisor)
    }

    override fun equals(other: Any?): Boolean {
        return other is Dollars && other.amount == amount
    }

    override fun hashCode(): Int {
        return amount.hashCode()
    }

    override fun toString(): String {
        return amount.roundToInt().toString() + " USD"
    }
}
