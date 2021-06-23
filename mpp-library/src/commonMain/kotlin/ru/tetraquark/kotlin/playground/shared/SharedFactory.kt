package ru.tetraquark.kotlin.playground.shared

import ru.tetraquark.kotlin.playground.shared.cinterop.intMultiply

class SharedFactory() {
    init {
    }

    fun mlt(n1: Int, n2: Int): Int = intMultiply(n1, n2)

}
