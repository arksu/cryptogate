package com.crypt.gate.util

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

const val ETH_MULTIPLIER = 1000000000000000000L

object Eth {
    fun bigDecimalToBigInteger(amount: BigDecimal): BigInteger {
        return amount.multiply(BigDecimal(ETH_MULTIPLIER)).toBigInteger()
    }

    fun bigIntegerToBigDecimal(amount: BigInteger): BigDecimal {
        return BigDecimal(amount).divide(BigDecimal(ETH_MULTIPLIER), 18, RoundingMode.HALF_UP)
    }
}