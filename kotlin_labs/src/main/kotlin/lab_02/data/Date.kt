package lab_02.data

import java.time.LocalDate

data class Date(
    val year: Int = LocalDate.now().year,
    val month: Int = LocalDate.now().monthValue,
    val day: Int = LocalDate.now().dayOfMonth
) : Comparable<Date> {

    override fun compareTo(other: Date): Int {
        return when {
            this.year != other.year -> this.year - other.year
            this.month != other.month -> this.month - other.month
            else -> this.day - other.day
        }
    }

}
fun Date.isLeapYear(): Boolean = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))

fun Date.isValid(): Boolean {
    if (year < 0)
        return false
    if (month < 1 || month > 12)
        return false
    if (day < 1)
        return false
    if (month == 2) {
        return if (isLeapYear()) day <= 29 else day <= 28
    }
    if (month == 4 || month == 6 || month == 9 || month == 11)
        return day <= 30
    return day <= 31
}