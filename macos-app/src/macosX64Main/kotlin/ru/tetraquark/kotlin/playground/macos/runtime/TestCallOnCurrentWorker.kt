package ru.tetraquark.kotlin.playground.macos.runtime

import ru.tetraquark.kotlin.playground.macos.logger.MultithreadLogger
import ru.tetraquark.kotlin.playground.macos.logger.log
import ru.tetraquark.kotlin.playground.macos.utils.Step
import kotlin.native.concurrent.TransferMode
import kotlin.native.concurrent.Worker
import kotlin.native.concurrent.isFrozen

fun testCallOnCurrentWorker() {
    addPrintLogger()
    addSilentLogger()

    val worker = Worker.start()

    val future = worker.execute(
        mode = TransferMode.SAFE,
        producer = { mutableListOf(0) },
        job = {
            log("(${Step()}) ; arg isFrozen = ${it.isFrozen} ; it = $it", "WID:${Worker.current.id}")

            callOnCurrentWorkerWithoutFreeze {
                log("(${Step()}) WorkerId inside call: ${Worker.current.id}", "WID:${Worker.current.id}")

                it.add(1)
                it.add(2)
                it.toMutableList() // make copy
            }
        }
    )

    future.consume { callback ->
        log("(${Step()}) Result workerId ${Worker.current.id}", "WID:${Worker.current.id}")
        val res = callback() // start call on other worker
        log("(${Step()}) Result: $res", "WID:${Worker.current.id}")
    }

    worker.requestTermination().result
}

fun addPrintLogger() {
    MultithreadLogger.addLogger { tag, message ->
        println("[$tag] $message")
    }
}

fun addSilentLogger() {
    MultithreadLogger.addLogger { tag, message -> }
}
