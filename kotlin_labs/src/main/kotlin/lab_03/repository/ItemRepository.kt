package lab_03.repository

import lab_03.model.Item
import java.io.File
import com.google.gson.Gson

object ItemRepository {
    val items: MutableList<Item> = mutableListOf();

    init {
//        Read from json file
        val file = File("E:\\Users\\Kaffai Levente\\Sapientia\\ANDROID_2024\\kotlin_labs\\src\\main\\resources\\quiz.json")
        val text = file.readText()
        val gson = Gson()
        val fromFile = gson.fromJson(text, Array<Item>::class.java)
        items.addAll(fromFile);
    }

    fun randomItem(): Item {
        return items.random()
    }

    fun save(item: Item) {
        items.add(item)
    }

    fun size(): Int {
        return items.size
    }
}