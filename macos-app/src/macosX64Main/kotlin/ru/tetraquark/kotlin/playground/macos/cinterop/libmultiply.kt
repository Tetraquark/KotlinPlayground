package ru.tetraquark.kotlin.playground.macos.cinterop

import ru.tetraquark.kotlin.playground.shared.cinterop.intMultiply

fun libMultiplyTest() {
    val res = intMultiply(10, 2)
    println(res)
}
