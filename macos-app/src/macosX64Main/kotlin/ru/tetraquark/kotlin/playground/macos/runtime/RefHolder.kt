package ru.tetraquark.kotlin.playground.macos.runtime

/**
 * See articles about Kotlin Native runtime by @kpgalligan
 *
 * https://medium.com/@kpgalligan/kotlin-native-stranger-threads-ep-1-1ccccdfe0c99
 */
class RefHolder<T>(ref: T?) {
    var ref: T? = ref
        get() {
            val tmp = field
            ref = null
            return tmp
        }
}
