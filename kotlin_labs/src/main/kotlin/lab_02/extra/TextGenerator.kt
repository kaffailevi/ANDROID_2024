import kotlin.random.Random

class TextGenerator {
// TODO: Something is not working properly in this class. Find and fix the issue.
    private val prefixToPostfixMap = mutableMapOf<Pair<String, String>, MutableList<String>>()
    private lateinit var words: List<String> ;
    // Training the generator with text
    fun learnWords(text: String) {

        words = text.split("\\s+".toRegex()).filter { it.isNotEmpty() }
        for (i in 0 until words.size - 2) {
            val prefix = Pair(words[i], words[i + 1])
            val postfix = words[i + 2]
            if (!prefixToPostfixMap.containsKey(prefix)) {
                prefixToPostfixMap[prefix] = mutableListOf()
            }
            prefixToPostfixMap[prefix]?.add(postfix)
        }
    }

    // Generate text based on training
    fun generateText(): String {
        val result = mutableListOf(words[0], words[1])  // Start with the first two words
        // Continue generating words
        for (i in 0 until words.size - 2) {
            val currentPrefix = Pair(result[i], result[i + 1])
            val possiblePostfixes = prefixToPostfixMap[currentPrefix] ?: break

            // Randomly select one postfix if there are multiple choices
            val nextWord = possiblePostfixes[Random.nextInt(possiblePostfixes.size)]
            result.add(nextWord)
        }

        return result.joinToString(" ")
    }
}