package lab_02.extra

import TextGenerator

fun main() {
    // Create a new TextGenerator instance
    val textGenerator = TextGenerator()

    // Sample input text
    val inputText = """
        Now is not the time for sleep, now is the time for party!
    """.trimIndent()

    // Learn the words from the input text
    textGenerator.learnWords(inputText)

    textGenerator.printAll()

    // Generate and print the output text
    var text = ""
    var next = ""
    do {
        next = textGenerator.generateText(text)
        text += " $next"
        println(text)
    } while (next != "")


}