package ru.tetraquark.kotlin.playground.shared.lokator

import kotlin.properties.ReadOnlyProperty

interface Injector

inline fun <reified T : Any> Injector.inject(): ReadOnlyProperty<Any?, T> {
    return ReadOnlyProperty { thisRef, property -> Kontext.resolve() }
}

inline fun <reified T : Any> Injector.injectNow(): T {
    return Kontext.resolve()
}

inline fun <reified T : Any> Injector.injectLazy() = lazy<T> {
    Kontext.resolve()
}
