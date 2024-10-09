package lab_03.controller

import lab_03.model.Item
import lab_03.service.ItemService

object ItemController
{
    val itemService: ItemService = ItemService
    fun quiz(){

        println("How many questions would you like to answer?");
        var ok = true
        var items = listOf<Item>()
        while(ok) {
            try {
                val numberOfQuestions = readln().toInt()
                items = itemService.selectRandomItems(numberOfQuestions)
                ok = false
            } catch (e: NumberFormatException) {
                println("Please enter a number!")
            }
            catch (e:IllegalArgumentException)
            {
                println("Number of questions is greater than the number of items")
            }
        }
        var correctAnswers = 0;
        for (item in items)
        {
            println(item.question)
            for (i in item.answers.indices)
            {
                println("${i+1}. ${item.answers[i]}")
            }
            print("Your answer: ")
            val answer:Int = readln().toInt()-1
            if (answer == item.correct)
            {
                println("Correct!")
                correctAnswers++
            }
            else
            {
                println("Incorrect!")
            }
        }
        println("You answered $correctAnswers/${items.size} correctly")
    }
}