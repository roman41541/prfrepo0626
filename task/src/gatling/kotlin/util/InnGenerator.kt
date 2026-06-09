package perf.util

import kotlin.random.Random

object InnGenerator {

    private val COEFF_11 = intArrayOf(7, 2, 4, 10, 3, 5, 9, 4, 6, 8)
    private val COEFF_12 = intArrayOf(3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8)

    fun generate(): String {
        val digits = IntArray(12) { Random.nextInt(0, 10) }
        digits[10] = controlDigit(digits, COEFF_11)
        digits[11] = controlDigit(digits, COEFF_12)
        return digits.joinToString("")
    }

    fun isValid(inn: String): Boolean {
        if (inn.length != 12 || !inn.all { it.isDigit() }) {
            return false
        }
        val digits = inn.map { it.digitToInt() }.toIntArray()
        return digits[10] == controlDigit(digits, COEFF_11)
                && digits[11] == controlDigit(digits, COEFF_12)
    }

    private fun controlDigit(digits: IntArray, coeffs: IntArray): Int {
        var sum = 0
        for (i in coeffs.indices) {
            sum += digits[i] * coeffs[i]
        }
        return sum % 11 % 10
    }
}
