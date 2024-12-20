package lab_01.extra

import org.testng.Assert.assertEquals
import org.testng.Assert.assertTrue
import org.testng.annotations.Test

class AnagramsGrouperTest {
    @Test
    fun threeGroupsAllLowerCase() {
        val anagrams = groupAnagrams(listOf("eat", "tea", "tan", "ate", "nat",
            "bat").toTypedArray())
        assertEquals(3, anagrams.size)
        assertTrue(anagrams.contains(listOf("eat", "tea", "ate")))
        assertTrue(anagrams.contains(listOf("tan", "nat")))
        assertTrue(anagrams.contains(listOf("bat")))
    }



    @Test
    fun threeGroupsSomeUpperCase() {
        val anagrams = groupAnagrams(listOf("eat", "tEa", "Tan", "atE", "NAT",
            "bat").toTypedArray())
        assertEquals(3, anagrams.size)
        assertTrue(anagrams.contains(listOf("eat", "tea", "ate")))
        assertTrue(anagrams.contains(listOf("tan", "nat")))
        assertTrue(anagrams.contains(listOf("bat")))
    }
    @Test
    fun validOneGroup() {
        val anagrams = groupAnagrams(listOf("eat").toTypedArray())
        assertEquals(1, anagrams.size)
        assertTrue(anagrams.contains(listOf("eat")))
    }
    @Test
    fun noGroup() {
        val anagrams = groupAnagrams(emptyList<String>().toTypedArray())
        assertEquals(0, anagrams.size)
    }

//    I resolved it here
    private fun groupAnagrams(strs: Array<String>): List<List<String>> {
        val map = mutableMapOf<String, MutableList<String>>()
        for (str in strs) {
            val sortedStr = str.lowercase().toCharArray().sorted().joinToString("")
            if (map.containsKey(sortedStr)) {
                map[sortedStr]?.add(str.lowercase())
            } else {
                map[sortedStr] = mutableListOf(str.lowercase())
            }
        }

        return map.values.toList()
    }

}