package lab_02.data

import java.util.*


fun main() {
    val validDates = mutableListOf<Date>()
    val random = Random()

    while (validDates.size < 10) {
        val year = random.nextInt(2024 - 1900) + 1900 // Range: 1900 to 2023
        val month = random.nextInt(12) + 1            // Range: 1 to 12
        val day = random.nextInt(31) + 1              // Range: 1 to 31 (not all will be valid)

        val date = Date(year, month, day)

        // Check if the date is valid
        if (date.isValid()) {
            validDates.add(date)
        } else {
            println("Invalid date: $date")
        }
    }

    println("Valid dates:")
    validDates.forEach { println(it) }

    println("<----------------------->")

    validDates.sort()
    println("Sorted valid dates:")
    validDates.forEach { println(it) }

    validDates.reverse()

    println("<----------------------->")

    println("Reverse sorted valid dates:")
    validDates.forEach { println(it) }

    val customDateComparator = Comparator<Date> { date1, date2 ->
        when {
            date1.year != date2.year -> date1.year - date2.year
            date1.month != date2.month -> date1.month - date2.month
            else -> date1.day - date2.day
        }
    }

    validDates.sortWith(customDateComparator)
    println("<----------------------->")
    println("Custom sorted valid dates:")
    validDates.forEach { println(it) }

}