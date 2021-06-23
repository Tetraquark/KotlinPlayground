package ru.tetraquark.kotlin.playground.macos

fun main() {
    val branches = sequenceOf<ProgramBranch>(
        CinteropMultiply,
    )

    branches.forEach(ProgramBranch::execute)
}
