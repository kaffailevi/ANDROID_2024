package lab_02.interfaces

interface IDictionary {
    fun add(word: String):Boolean
    fun find(word: String): Boolean
    fun size(): Int

    companion object{
        const val DICTIONARY_PATH = "E:\\Users\\Kaffai Levente\\Sapientia\\ANDROID_2024\\kotlin_labs\\src\\main\\resources\\dict"
    }
}