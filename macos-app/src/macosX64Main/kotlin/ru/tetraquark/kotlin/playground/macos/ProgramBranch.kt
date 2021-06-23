package ru.tetraquark.kotlin.playground.macos

import ru.tetraquark.kotlin.playground.macos.cinterop.libMultiplyTest
import ru.tetraquark.kotlin.playground.macos.runtime.atomic.atomicTest

sealed class ProgramBranch {
    abstract fun execute()
}

object CinteropMultiply : ProgramBranch() {
    override fun execute() {
        libMultiplyTest()
    }
}

object AtomicTest : ProgramBranch() {
    override fun execute() {
        atomicTest()
    }
}
