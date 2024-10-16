package lab_03

import lab_03.controller.ItemController
import lab_03.repository.ItemRepository

fun main(){
    var ok = true

    while (ok){
        ok = try {
            println("How many questions would you like to answer?");
            val numberOfItems = readln().toInt()
            val itemController = ItemController
            itemController.quiz(numberOfItems)
            false
        } catch (e: NumberFormatException ){
            println("Please enter a valid number")
            true
        }catch (e: IllegalArgumentException)
        {
            println("Number of questions must be less than the total number of questions")
            true
        }
    }

}