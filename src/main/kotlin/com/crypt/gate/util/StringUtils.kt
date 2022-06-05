package com.crypt.gate.util

import java.security.SecureRandom

object StringUtils {

    private const val ALPHA_NUMERIC_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789"

    fun generatePlainString(length: Int): String {
        val random = SecureRandom()
        val charsSize = ALPHA_NUMERIC_CHARS.length

        val result = StringBuilder()
        for (i in 1..length) {
            result.append(ALPHA_NUMERIC_CHARS[random.nextInt(charsSize)])
        }
        return result.toString()
    }
}