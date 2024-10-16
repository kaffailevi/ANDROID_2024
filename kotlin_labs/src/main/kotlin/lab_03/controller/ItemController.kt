package lab_03.controller

import lab_03.model.Item
import lab_03.service.ItemService

object ItemController {
    val itemService: ItemService = ItemService
    fun quiz(numberOfItems: Int) {

        var ok = true
        var items = listOf<Item>()
        while (ok) {
            ok = try {
                items = itemService.selectRandomItems(numberOfItems)
                false
            } catch (e: NumberFormatException) {
                println("Please enter a valid number")
                true
            }
        }
        var correctAnswers = 0;
        ok = true
        var index = 0
        while (ok || (index < items.size)) {
            ok = try {
                val item = items[index]
                println(item.question)
                for (i in item.answers.indices) {
                    println("${i + 1}. ${item.answers[i]}")
                }
                print("Your answer: ")
                val answer: Int = readln().toInt() - 1
                if (answer == item.correct) {
                    println("Correct!")
                    correctAnswers++
                } else {
                    println("Incorrect!")
                }
                index++
                false
            }
            catch (e: NumberFormatException) {
                println("Please enter a valid number")
                false
            }
        }
        println("You answered $correctAnswers/${items.size} correctly")
    }
}