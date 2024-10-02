package lab_02.providers

import lab_02.implementation.HashSetDictionary
import lab_02.implementation.ListDictionary
import lab_02.implementation.TreeSetDictionary

class DictionaryProvider {

    companion object{
        fun createDictionary(dictionaryType: DictionaryType) = when (dictionaryType) {
            DictionaryType.ARRAY_LIST -> ListDictionary
            DictionaryType.TREE_SET -> TreeSetDictionary
            DictionaryType.HASH_SET -> HashSetDictionary
        }
    }

}