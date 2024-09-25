import java.util.*
/**
 * You can edit, run, and share this code.
 * play.kotlinlang.org
 */
fun main() {
    val a = 2;
    val b = 3;
    val sum = a+b;
//     1
    println("SUM: $sum");
    println("SUM: ${a+b}");
//     2
    var daysOfWeek = listOf("MONDAY",
            "TUESDAY",
            "WEDNESDAY",
            "THURSDAY",
            "FRIDAY",
            "SATURDAY",
            "SUNDAY")
    println("<----------------------------->")

    for(day:String in daysOfWeek)
    {
        println(day)
    }
    println("<----------------------------->")

    daysOfWeek.filter {it.startsWith("T")}.forEach {println(it)}
    println("<----------------------------->")
    daysOfWeek.filter{it.contains("E")}.forEach{println(it)}
    println("<----------------------------->")
    daysOfWeek.filter{ it.length==6}.forEach{println(it)}
//     3
    println("<----------------------------->")

    for(i in 1..100)
    {
        if(isPrime(i))
            println(i)
    }
//     5
    println("<----------------------------->")

    fun filterEven(numberList:List<Int>) = numberList.filter{ it%2 == 0}
    println(filterEven(listOf(1,2,3,4,5)));
// 4
    println("<----------------------------->")

    val encodedRandomString = messageCoding("HELLO WORLD!",::encodeBase64);
    println(encodedRandomString)
    println(messageCoding(encodedRandomString,::decodeBase64))
}

fun messageCoding(msg:String, func: (String)->String):String
{
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

fun printList(list:List<T>){
    list.forEach(println(it))
}

/*
 *Function that checks if a number is prime or not
*/
fun isPrime(number:Int):Boolean{
    if(number<2) return false;
    for(i in 2..number/2)
    {
        if(number%i==0)
            return false;
    }
    return true;
}
