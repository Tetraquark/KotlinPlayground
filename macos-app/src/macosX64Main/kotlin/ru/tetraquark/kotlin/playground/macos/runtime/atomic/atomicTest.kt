package ru.tetraquark.kotlin.playground.macos.runtime.atomic

import ru.tetraquark.kotlin.playground.macos.logger.log
import kotlin.native.concurrent.AtomicInt
import kotlin.native.concurrent.TransferMode
import kotlin.native.concurrent.Worker

object AtomicCounter {
    val incorrectCounter = AtomicInt(0)
    val correctCounter = AtomicInt(0)
}

fun atomicTest() {
    val workers = List(500) {
        Worker.start()
    }

    val futures = workers.map {
        it.execute(TransferMode.SAFE, {}) {
            AtomicCounter.incorrectCounter.value++
            AtomicCounter.correctCounter.increment()
        }
    }

    futures.forEach {
        it.consume {  }
    }

    log("incorrect counter = ${AtomicCounter.incorrectCounter.value}", "AtomicTest")
    log("correct counter = ${AtomicCounter.correctCounter.value}", "AtomicTest")
}
