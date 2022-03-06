package geekbrains.ru.translator

import geekbrains.ru.model.data.userdata.Meaning
import geekbrains.ru.model.data.userdata.TranslatedMeaning
import geekbrains.ru.translator.utils.convertMeaningsToSingleString
import org.junit.Test
import org.junit.Assert.*

class MeaningsToStringTest {
    private val testFirst = meaningConstructor("экзамен")
    private val testSecond = meaningConstructor("тест")
    private val testThird = meaningConstructor("анализ")
    private val testFourth = meaningConstructor("испытание")
    private val testFifth = meaningConstructor("тестировать")
    private val testSixth = meaningConstructor("обследовать")

    private val testArray = listOf(testFirst, testSecond, testThird, testFourth,
                                        testFifth, testSixth)
    private val testString = "экзамен, тест, анализ, испытание, тестировать, обследовать"

    private fun meaningConstructor(meaning: String) : Meaning = Meaning(TranslatedMeaning(meaning))

    @Test
    fun meaningsToSingleStringIsCorrect() {
        assertEquals(testString, convertMeaningsToSingleString(testArray))
    }

    @Test
    fun meaningsToSingleStringIsFalse() {
        val testFalseArray = testArray.toMutableList()
        testFalseArray.add(meaningConstructor(""))
        assertNotEquals(testString, convertMeaningsToSingleString(testFalseArray))
    }
}
