package lab_02.extra

import TextGenerator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TextGeneratorTest {

    @Test
    fun `test learnWords() populates prefixToPostfixMap correctly`() {
        val generator = TextGenerator()
        generator.learnWords("hello world test")

        // Check if map contains expected prefix and postfix
        val expectedPrefix = Pair("hello", "world")
        assertTrue(generator.prefixToPostfixMap.containsKey(expectedPrefix))
        assertEquals(setOf("test"), generator.prefixToPostfixMap[expectedPrefix])
    }

    @Test
    fun `test generateText() with empty input returns initial prefix`() {
        val generator = TextGenerator()
        generator.learnWords("hello world test")

        // Empty input should return the initialPrefix
        val generatedText = generator.generateText("")
        assertEquals("hello world", generatedText)
    }

    @Test
    fun `test generateText() with insufficient words returns initial prefix`() {
        val generator = TextGenerator()
        generator.learnWords("hello world test")

        // Input with less than 2 words should return the initialPrefix
        val generatedText = generator.generateText("hello")
        assertEquals("hello world", generatedText)
    }

    @Test
    fun `test generateText() with valid prefix returns correct postfix`() {
        val generator = TextGenerator()
        generator.learnWords("hello world test example world test again")

        // Input with valid prefix should return the correct postfix
        val generatedText = generator.generateText("world test")
        assertTrue(generatedText == "example" || generatedText == "again")
    }

    @Test
    fun `test generateText() with non-existing prefix returns empty string`() {
        val generator = TextGenerator()
        generator.learnWords("hello world test")

        // Input with a non-existing prefix should return an empty string
        val generatedText = generator.generateText("non existing")
        assertEquals("", generatedText)
    }

    @Test
    fun `test printAll() does not crash`() {
        val generator = TextGenerator()
        generator.learnWords("hello world test")

        // Should not crash when calling printAll
        generator.printAll()
    }

    @Test
    fun `test edge case empty text learning`() {
        val generator = TextGenerator()

        // Test with empty text
        generator.learnWords("")

        // The map should remain empty
        assertTrue(generator.prefixToPostfixMap.isEmpty())
    }
}
