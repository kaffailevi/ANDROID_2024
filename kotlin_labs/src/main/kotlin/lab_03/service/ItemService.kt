package lab_03.service

import lab_03.model.Item
import lab_03.repository.ItemRepository

object ItemService {
    val itemRepository: ItemRepository = ItemRepository
    fun selectRandomItems(numberOfQuestions: Int): List<Item> {
        if (itemRepository.size() > numberOfQuestions) {
            val items = mutableListOf<Item>()
            while (items.size < numberOfQuestions) {
                val item = itemRepository.randomItem()
                if (!items.contains(item)) {
                    items.add(item)
                }
            }
            return items
        }
        throw IllegalArgumentException("Number of questions is greater than the number of items")
    }
}
