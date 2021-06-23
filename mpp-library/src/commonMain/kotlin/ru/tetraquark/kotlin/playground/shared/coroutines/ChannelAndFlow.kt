package ru.tetraquark.kotlin.playground.shared.coroutines

import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private inline fun startTest(name: String, test: () -> Unit) {
    println("Start $name")
    test()
    println("Stop $name")
    println("------------------------")
}

fun testFlow() {
    startTest("stateFlowTest") {
        stateFlowTest()
    }

    startTest("sharedFlowTest") {
        sharedFlowTest()
    }

    startTest("channelTest") {
        channelTest()
    }
}

internal fun stateFlowTest() = runBlocking {
    val stateFlow = MutableStateFlow(0)
    val mappedFlow = stateFlow.map { it.toString() }

    stateFlow.value = 1

    val collectorJob = launch {
        mappedFlow.collect {
            println("stateFlow value $it")
            delay(400)
        }
    }
    launch {
        delay(100)
        stateFlow.value = 2
        delay(200)
        stateFlow.value = 3
        delay(250)
        stateFlow.value = 4

        delay(1000)
        collectorJob.cancel()
    }.join()
}

internal fun sharedFlowTest() = runBlocking {
    val sharedFlow = MutableSharedFlow<Int>()
    val mappedFlow = sharedFlow.map { it.toString() }

    val collectors = mutableListOf<Job>()

    // Emit 0
    launch {
        sharedFlow.emit(0)
    }

    // collector 1
    launch {
        delay(20)
        mappedFlow.collect {
            println("collector 1: $it")
            delay(150)
        }
    }.run(collectors::add)

    // Emit 1
    launch {
        delay(30)
        sharedFlow.emit(1)
    }

    // collector 2
    launch {
        delay(50)
        mappedFlow.collect {
            println("collector 2: $it")
            delay(400)
        }
    }.run(collectors::add)

    // Emit 2, 3, 4
    launch {
        delay(100)
        sharedFlow.emit(2)
        delay(200)
        sharedFlow.emit(3)
        delay(250)
        sharedFlow.emit(4)

        delay(1000)
        collectors.forEach(Job::cancel)
    }.join()
}

internal fun channelTest() = runBlocking {
    val channel = ConflatedBroadcastChannel<Int>(5)
    val mappedChannel = channel.asFlow().map { it.toString() }

    val collectors = mutableListOf<Job>()

    // Emit 0
    launch {
        channel.send(0)
    }

    // collector 1
    launch {
        delay(20)
        mappedChannel.collect {
            println("collector 1: $it")
            delay(150)
        }
    }.run(collectors::add)

    // Emit 1
    launch {
        delay(30)
        channel.send(1)
    }

    // collector 2
    launch {
        delay(50)
        mappedChannel.collect {
            println("collector 2: $it")
            delay(400)
        }
    }.run(collectors::add)

    // Emit 2, 3, 4
    launch {
        delay(100)
        channel.send(2)
        delay(200)
        channel.send(3)
        delay(250)
        channel.send(4)

        delay(1000)
        collectors.forEach(Job::cancel)
    }.join()
}
