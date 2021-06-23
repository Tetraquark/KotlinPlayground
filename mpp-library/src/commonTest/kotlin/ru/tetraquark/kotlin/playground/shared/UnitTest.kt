package ru.tetraquark.kotlin.playground.shared

import dev.icerock.moko.test.cases.TestCases
import kotlin.test.Test

class UnitTest : TestCases() {
    override val rules: List<Rule>
        get() = emptyList()

    @Test
    fun test() {
        println("Test")
    }

}
