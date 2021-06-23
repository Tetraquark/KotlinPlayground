package ru.tetraquark.kotlin.playground.macos.runtime.atomic

import kotlin.native.concurrent.AtomicReference

internal inline fun <T> AtomicReference<T>.change(block: (T) -> T) {
    var new: T
    var last: T
    do {
        last = value
        new = block(last)
    } while (!compareAndSet(last, new))
}
