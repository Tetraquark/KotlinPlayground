package ru.tetraquark.kotlin.playground.shared.stuff

typealias Factory = (String) -> String

object SingletonFactory : Factory {
    override fun invoke(arg: String): String {
        return arg
    }
}

private fun callFactoryExample() {
    listOf<String>(
        SingletonFactory("arg1"),
        SingletonFactory("arg2"),
        SingletonFactory("arg3")
    )
}
