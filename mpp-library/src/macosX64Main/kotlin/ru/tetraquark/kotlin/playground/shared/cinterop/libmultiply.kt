package ru.tetraquark.kotlin.playground.shared.cinterop

/**
 * Calls function from the static C library.
 */
actual fun intMultiply(n1: Int, n2: Int): Int {
    return multiplier.multiply(n1, n2)
}
