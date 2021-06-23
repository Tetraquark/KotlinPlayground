package ru.tetraquark.kotlin.playground.shared.leetcode

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.math.pow

fun listWork(list: List<*>) {
    val anyList = list as List<Any>
    println("listWork: ${anyList.joinToString()}")
}

fun mainTest() {
    //duplicateZerosTest()

    val listInt = listOf(1, 2, 3)
    val listString = listOf("1", "2")

    val anyList: List<Any> = listInt
    listWork(anyList)
    listWork(listString)

    val flow = flow<String> {
        emit("123")
    }

    runBlocking {
        println("1 ${flow.first()}")
        println("2 ${flow.first()}")
    }

    var i = 0
    val a = i++ + 1
    val b = ++i + 1
    println("a $a b $b i $i")


    listOf(
        "1 2 3", "4 5 6", "0 10"
    ).flatMap {
        it.split(" ")
    }


    val intList = listOf(2, 3, 4)

    val result = listOf(
        "1"
    ) + intList.map { it.toString() }

    println(result)
}

fun twoSum(nums: IntArray, target: Int): IntArray {
    var resArr: Array<Int>? = null

    var i = 0
    while (i < nums.size) {
        var j = i + 1
        while (j < nums.size) {
            if (nums[i] + nums[j] == target) {
                resArr = arrayOf(i, j)
                i = nums.size
                break
            }
            j++
        }
        i++
    }
    return resArr?.toIntArray() ?: IntArray(0)
}

fun twoSum2(nums: IntArray, target: Int): IntArray {
    var i = 0
    val hashMap = mutableMapOf<Int, Int>()
    while (i < nums.size) {
        val cmp = target - nums[i]
        if (hashMap.containsKey(cmp)) {
            val res = IntArray(2)
            res[0] = hashMap[cmp]!!
            res[1] = i
            return res
        }
        hashMap[nums[i]] = i
        i++
    }

    return IntArray(0)
}
fun findMaxConsecutiveOnes(nums: IntArray): Int {
    var consCount = 0
    var maxCons = 0

    nums.forEachIndexed { index, i ->
        if (i == 1) {
            consCount++
        } else {
            if (consCount > maxCons) maxCons = consCount
            consCount = 0
        }
    }
    if (consCount > maxCons) maxCons = consCount

    return maxCons
}

fun findNumbers_3(nums: IntArray): IntArray {
    return nums.map { it * it }.sorted().toIntArray()
    for (i in nums) {
        i * i
    }
}

fun findNumbers_1(nums: IntArray): Int {
    return nums.asSequence()
        .map { it.toString() }
        .filter { it.length % 2 == 0 }
        .count()
}

fun findNumbers_2(nums: IntArray): Int {
    var result = 0
    var i = 0
    while (i < nums.size) {
        var num = 0
        do {
            nums[i] /= 10
            num++
        } while (nums[i] > 1 || nums[i] == 1)

        if (num % 2 == 0) result++

        i++
    }

    return nums.filter{ "$it".length % 2 == 0 }.size
    return result

    return nums.asSequence()
        .map { it.toString() }
        .filter { it.length % 2 == 0 }
        .count()
}

fun sortedSquares(nums: IntArray): IntArray {
    var i = 0
    while (i < nums.size) {
        nums[i] = nums[i] * nums[i]
        i++
    }
    return nums.sortedArray()
    //return nums.map { it * it }.sorted().toIntArray()
}

internal class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

internal fun listNodeToInt(node: ListNode?): Int {
    if (node == null) return 0

    var result = 0

    var depthLvl = 0
    var next: ListNode? = node
    while (next != null) {
        result += next.`val` * 10f.pow(depthLvl).toInt()
        next = next.next
        depthLvl++
    }
    return result
}

internal fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {

    val l1Res = listNodeToInt(l1)
    val l2Res = listNodeToInt(l2)

    val strRes = (l1Res + l2Res).toString()
    var i = strRes.length - 1
    var prev: ListNode? = null
    var result: ListNode? = null
    while (i >= 0) {
        val node = ListNode(strRes[i].toString().toInt())
        if (i == strRes.length - 1) result = node
        prev?.next = node
        prev = node
        i--
    }

    return result
}

fun duplicateZerosTest() {
    val arr = arrayOf(0, 4, 1, 0, 0, 8, 0, 0, 3).toIntArray()
    println("IN: ${arr.asList()}")
    duplicateZeros(arr)
    println("OUT: ${arr.asList()}")
}

/**
 * in:  [1,0,2,3,0,4,5,0]
 * out: [1,0,0,2,3,0,0,4]
 */
fun duplicateZeros(arr: IntArray): Unit {
    var i = 0
    while (i < arr.size) {
        if (arr[i] == 0 && i + 1 < arr.size) {
            var j = i + 1
            var tmp1 = 0
            var tmp2 = arr[j]
            while (j < arr.size) {
                arr[j] = tmp1
                tmp1 = tmp2
                j++
                if (j < arr.size) tmp2 = arr[j]
            }
            i++
        }
        i++
    }
}
