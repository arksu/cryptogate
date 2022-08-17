package com.crypt.gate.util

import com.crypt.gate.exception.WrongCryptoCurrencyException
import com.crypt.gate.model.CryptoCurrency
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

const val ETH_MULTIPLIER = 1000000000000000000L

fun bigDecimalToBigInteger(currency: CryptoCurrency, value: BigDecimal): BigInteger {
    if (currency == CryptoCurrency.ETH) {
        return value.multiply(BigDecimal(ETH_MULTIPLIER)).toBigInteger()
    }
    throw WrongCryptoCurrencyException()
}

fun bigIntegerToBigDecimal(currency: CryptoCurrency, value: BigInteger): BigDecimal {
    if (currency == CryptoCurrency.ETH) {
        return BigDecimal(value).divide(BigDecimal(ETH_MULTIPLIER), 18, RoundingMode.HALF_UP)
    }
    throw WrongCryptoCurrencyException()
}