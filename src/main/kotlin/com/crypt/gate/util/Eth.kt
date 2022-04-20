package com.crypt.gate.util

import com.crypt.gate.const.ETH_MULTIPLIER
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

object Eth {
    fun bigDecimalToBigInteger(amount: BigDecimal): BigInteger {
        return amount.multiply(BigDecimal(ETH_MULTIPLIER)).toBigInteger()
    }

    fun bigIntegerToBigDecimal(amount: BigInteger): BigDecimal {
        return BigDecimal(amount).divide(BigDecimal(ETH_MULTIPLIER), 18, RoundingMode.HALF_UP)
    }
}