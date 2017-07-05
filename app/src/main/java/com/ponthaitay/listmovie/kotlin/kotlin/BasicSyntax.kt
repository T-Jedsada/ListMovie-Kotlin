package com.ponthaitay.listmovie.kotlin.kotlin

fun main(args: Array<String>) {

    val a: Int = 5
    val b = 4

    var firstName: String = "20scoops"
    var lastName = "CNX"

    // Mutable
    firstName = "Jedsada"

    //Immutable
//    a = 6

    println(firstName.trim { it == 's' })


    //Defining functions
    fun sum(a: Int, b: Int): Int {
        return a + b
    }

    fun sum1(a: Int, b: Int) = a + b

    fun maxOf(a: Int, b: Int): Int {
        if (a > b) {
            return a
        } else {
            return b
        }
    }

    fun maxOf2(a: Int, b: Int) = if (a > b) a else b

    val maxOf3 = maxOf(4, 6)


    //loop for
    for (i in 1..10) {
        println("index : $i")
    }

    val items = listOf("apple", "banana", "kiwi")

    for (item in items) {
        println(item)
    }

    for ((index, value) in items.withIndex()) {
        println("item at $index is $value")
    }

    items.forEach { println(it) }

    items.forEachIndexed { index, value -> println("item at $index is $value") }


    //loop while
    var index = 9
    while (index >= 0) {
        println(index)
        index--
    }

    //Spacial
    outer@ for (i in 1..10) {
        for (j in 1..10) {
            if (i - j == 5) break@outer
            println("$i - $j")
        }
    }

    val result = (1..10).sum()


}
