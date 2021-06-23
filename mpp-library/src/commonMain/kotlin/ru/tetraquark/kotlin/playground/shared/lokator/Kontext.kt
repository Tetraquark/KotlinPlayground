package ru.tetraquark.kotlin.playground.shared.lokator

import kotlin.reflect.KClass

typealias Provider = Kontext.() -> Any

/**
 * Simple service-locator implementation.
 * 1. Bind any dependency using [Kontext.bind]
 * 2. Use [Kontext.resolve] to find dependency in the static Map
 */
object Kontext {
    private val context = mutableMapOf<KClass<*>, Provider>()

    fun bind(key: KClass<*>, provider: Provider) {
        context[key] = provider
    }

    operator fun <T : Any> get(key: KClass<T>): T {
        return context[key]?.invoke(this) as? T ?: throw IllegalArgumentException("")
    }

    inline fun <reified T : Any> bind(noinline provider: Kontext.() -> T) {
        bind(T::class, provider)
    }

    inline fun <reified T : Any> bind(value: T) {
        bind(T::class) { value }
    }

    inline fun <reified T : Any> resolve(): T {
        return get(T::class)
    }
}
