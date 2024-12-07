package com.invest.currency.core

class Pesos(val amount: Double) : Money {
    operator fun plus(other: Pesos): Pesos {
        return Pesos(amount + other.amount)
    }

    operator fun minus(other: Pesos): Pesos {
        return Pesos(amount - other.amount)
    }

    operator fun times(multiplier: Double): Pesos {
        return Pesos(amount * multiplier)
    }

    operator fun times(multiplier: Int): Pesos {
        return Pesos(amount * multiplier)
    }

    operator fun div(divisor: Double): Pesos {
        require(divisor != 0.0) { "Division by zero is not allowed." }
        return Pesos(amount / divisor)
    }

    operator fun div(divisor: Int): Pesos {
        require(divisor != 0) { "Division by zero is not allowed." }
        return Pesos(amount / divisor)
    }

    override fun equals(other: Any?): Boolean {
        return other is Pesos && other.amount == amount
    }

    override fun hashCode(): Int {
        return amount.hashCode()
    }
}
