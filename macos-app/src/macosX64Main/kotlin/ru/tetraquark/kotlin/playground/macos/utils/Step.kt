package ru.tetraquark.kotlin.playground.macos.utils

import kotlin.native.concurrent.AtomicInt

object Step : () -> Int {
    private val step = AtomicInt(0)

    override fun invoke(): Int {
        val currentStep = step.value
        step.increment()
        return currentStep
    }
}
