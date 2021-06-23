package ru.tetraquark.kotlin.playground.macos.runtime

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlin.native.concurrent.WorkerBoundReference
import kotlin.native.concurrent.ensureNeverFrozen
import kotlin.native.concurrent.freeze

/**
 * See moko-utils
 */
fun <R> callOnDispatcherWithoutFreeze(
    dispatcher: CoroutineDispatcher,
    block: suspend () -> R
): suspend () -> R {
    // mark that block should not be frozen
    block.ensureNeverFrozen()
    // this reference will be frozen, but block not freeze
    val guardRef = WorkerBoundReference(block)
    val result: suspend () -> R = {
        withContext(dispatcher) {
            val guardedBlock = guardRef.value
            guardedBlock()
        }
    }
    // freeze lambda to check that all ok
    result.freeze()
    return result
}
