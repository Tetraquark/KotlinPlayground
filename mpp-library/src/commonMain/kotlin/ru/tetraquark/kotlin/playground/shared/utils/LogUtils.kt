package ru.tetraquark.kotlin.playground.shared.utils

fun <T> T.println(): T = also(::println)

fun printlnExtUserExample() {
    val number = 10.println() + 20.println()
}
