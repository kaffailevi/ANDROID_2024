package lab_02.implementation

import lab_02.interfaces.IDictionary
import java.io.File

object ListDictionary : IDictionary {

    private val words = mutableListOf<String>()

    init{
        File(IDictionary.DICTIONARY_PATH).forEachLine { add(it) }
//        mockDictionaryData()
    }

    private fun mockDictionaryData() {
        words.add("apple")
        words.add("banana")
        words.add("orange")
        words.add("kiwi")
    }

    override fun add(word: String): Boolean = words.add(word)
    override fun find(word: String): Boolean = words.contains(word)

    override fun size(): Int = words.size

}