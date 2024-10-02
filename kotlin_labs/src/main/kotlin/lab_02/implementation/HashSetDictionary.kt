package lab_02.implementation

import lab_02.interfaces.IDictionary
import java.io.File

object HashSetDictionary : IDictionary {

    var words: HashSet<String> = HashSet();

    init {
        File(IDictionary.DICTIONARY_PATH).forEachLine { add(it) }
    }

    override fun add(word: String): Boolean = words.add(word)
    override fun find(word: String): Boolean = words.contains(word)

    override fun size(): Int = words.size
}