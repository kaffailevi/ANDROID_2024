package lab_01

import java.util.*
import kotlin.random.Random

/**
 * You can edit, run, and share this code.
 * play.kotlinlang.org
 */
fun main() {
    val a = 2;
    val b = 3;
    val sum = a + b;
//     1
    println("SUM: $sum");
    println("SUM: ${a + b}");
//     2
    var daysOfWeek = listOf(
        "MONDAY",
        "TUESDAY",
        "WEDNESDAY",
        "THURSDAY",
        "FRIDAY",
        "SATURDAY",
        "SUNDAY"
    )
    println("<----------------------------->")

    for (day: String in daysOfWeek) {
        println(day)
    }
    println("<----------------------------->")

    daysOfWeek.filter { it.startsWith("T") }.forEach { println(it) }
    println("<----------------------------->")
    daysOfWeek.filter { it.contains("E") }.forEach { println(it) }
    println("<----------------------------->")
    daysOfWeek.filter { it.length == 6 }.forEach { println(it) }
//     3
    println("<----------------------------->")

    for (i in 1..100) {
        if (isPrime(i))
            println(i)
    }
//     5
    println("<----------------------------->")

    fun filterEven(numberList: List<Int>) = numberList.filter { it % 2 == 0 }
    println(filterEven(listOf(1, 2, 3, 4, 5)));
// 4
    println("<----------------------------->")

    val encodedRandomString = messageCoding("HELLO WORLD!", ::encodeBase64);
    println(encodedRandomString)
    println(messageCoding(encodedRandomString, ::decodeBase64))

    println("<----------------------------->")
//    6
    val numbers = listOf(1, 2, 3, 4, 5)

    val doubledNumbers = numbers.map { it * 2 }
    println("Doubled numbers: $doubledNumbers")

    val capitalizedDays = daysOfWeek.map { it.uppercase() }
    println("Capitalized days: $capitalizedDays")

    val firstCharacter = daysOfWeek.map { it.first().lowercase() }
    println("First character of each day: $firstCharacter")

    val dayLengths = daysOfWeek.map { it.length }
    println("Length of each day: $dayLengths")

    val averageLength = dayLengths.average()
    println("Average length of days: $averageLength")

//    7
    println("<----------------------------->")
    val mutableDaysOfWeek = daysOfWeek.toMutableList()

    mutableDaysOfWeek.removeAll { it.contains('n', ignoreCase = true) }
    println("Days without 'n': $mutableDaysOfWeek")

    for ((index, day) in mutableDaysOfWeek.withIndex()) {
        println("Item at $index is $day")
    }

    mutableDaysOfWeek.sort()
    println("Sorted days: $mutableDaysOfWeek")

//    8
    println("<----------------------------->")
    val randomNumbers = Array(10) { Random.nextInt(0, 100) }
    randomNumbers.forEach { println(it) }
    randomNumbers.sort()
    println("Sorted random numbers: ${randomNumbers.joinToString()}")
    println("Has any even number: ${randomNumbers.any { it % 2 == 0 }}")
    println("Has all even numbers: ${randomNumbers.all { it % 2 == 0 }}")
    var sum_avg = 0;
    randomNumbers.forEach { sum_avg += it }
    println("Sum of random numbers: ${sum_avg/randomNumbers.size.toDouble()}")

}

fun messageCoding(msg: String, func: (String) -> String): String {
    return func(msg);
}

fun encodeBase64(input: String): String {
    val encoder = Base64.getEncoder()
    return encoder.encodeToString(input.toByteArray())
}

fun decodeBase64(encoded: String): String {
    val decoder = Base64.getDecoder()
    return String(decoder.decode(encoded))
}

/*
 *Function that checks if a number is prime or not
*/
fun isPrime(number: Int): Boolean {
    if (number < 2) return false;
    for (i in 2..number / 2) {
        if (number % i == 0)
            return false;
    }
    return true;
}

//     6

