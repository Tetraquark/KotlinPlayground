package ru.tetraquark.kotlin.playground.macos.logger

import kotlinx.atomicfu.atomic

typealias Logger = (String?, String) -> Unit

object MultithreadLogger {
    private val loggers = atomic(listOf<Logger>())

    fun addLogger(logger: Logger) {
        loggers.getAndSet(loggers.value + listOf(logger))
    }

    fun log(message: String, tag: String? = null) {
        loggers.value.forEach {
            it(tag, message)
        }
    }
}

fun log(message: String, tag: String? = null) = MultithreadLogger.log(message, tag)
