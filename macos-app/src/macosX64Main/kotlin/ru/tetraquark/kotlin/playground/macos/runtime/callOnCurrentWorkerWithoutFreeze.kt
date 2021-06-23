package ru.tetraquark.kotlin.playground.macos.runtime

import kotlin.native.concurrent.*

/**
 * See moko-utils
 */
fun <R> callOnCurrentWorkerWithoutFreeze(block: () -> R): () -> R {
    // mark that block should not be frozen
    block.ensureNeverFrozen()
    // this reference will be frozen, but block not freeze
    val guardRef = WorkerBoundReference(block)
    val current = Worker.current
    val result: () -> R = {
        val feature = current.execute(
            mode = TransferMode.SAFE,
            producer = {
                guardRef
            },
            job = {
                val guardedBlock = it.value
                guardedBlock()
            }
        )
        feature.consume {
            it
        }
    }
    // freeze lambda to check that all ok
    result.freeze()
    return result
}
