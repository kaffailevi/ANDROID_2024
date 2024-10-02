package lab_02

import lab_02.providers.DictionaryType
import lab_02.interfaces.IDictionary
import lab_02.providers.DictionaryProvider

fun main() {
//    val dict: IDictionary = ListDictionary()
//    val dict: IDictionary = TreeSetDictionary()
    val dict: IDictionary = DictionaryProvider.createDictionary(DictionaryType.ARRAY_LIST)
    println("Number of words: ${dict.size()}")
    var word: String?
    while (true) {
        print("What to find? ")
        word = readLine()
        if (word.equals("quit")) {
            break
        }
        println("Result: ${word?.let { dict.find(it) }}")
    }

    val name = "Kaffai Levente";
    println(name.nameMonogram()) // prints "KL"
    val list = listOf("apple", "banana", "orange", "kiwi")
    println(list.joinElements("#")) // prints "apple, banana, orange, kiwi"
    println(list.longest()) // prints "banana"
}


fun String.nameMonogram(): String = this.split(" ").joinToString("") { it[0].uppercase() }

fun List<String>.joinElements(separator:String): String = this.joinToString(separator)

fun List<String>.longest():String = this.maxByOrNull { it.length } ?: ""


//TODO: 3. feladattol kezdve