
class TextGenerator {

    internal val prefixToPostfixMap = mutableMapOf<Pair<String, String>, MutableSet<String>>()
    private lateinit var initialPrefix : Pair<String,String>

    // Training the generator with text
    fun learnWords(text: String) {

        var words = text.split("\\s+".toRegex())
        for (i in 0 until words.size - 2) {
            val prefix = Pair(words[i], words[i + 1])
            val postfix = words[i + 2]

            if (i == 0) {
                initialPrefix = prefix
            }
            if (prefixToPostfixMap.containsKey(prefix)) {
                prefixToPostfixMap[prefix]?.add(postfix)
            } else {
                prefixToPostfixMap[prefix] = mutableSetOf(postfix)
            }
        }

    }

    private fun Pair<String,String>.joinToString(): String = "${this.first} ${this.second}"

    // Generate text based on training
    fun generateText(generateFrom: String): String {

        if (generateFrom.isEmpty()) {
            return initialPrefix.joinToString();
        }
        val words = generateFrom.split("\\s+".toRegex())
        if (words.size < 2) {
            return initialPrefix.joinToString()
        }
        val prefix = Pair(words[words.size - 2], words[words.size - 1])

        return if ((prefixToPostfixMap[prefix]?.size ?: 0) > 1) {
            prefixToPostfixMap[prefix]?.random() ?: ""
        } else prefixToPostfixMap[prefix]?.first() ?: ""
    }

    //    print every postfix to every prefix
    fun printAll() {
        for ((key, value) in prefixToPostfixMap) {
            println("Prefix: $key, Postfixes: $value")
        }
    }
}