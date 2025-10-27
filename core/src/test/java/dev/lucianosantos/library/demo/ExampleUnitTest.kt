package dev.lucianosantos.library.demo

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        // Arrange
        val a = 2
        val b = 2
        val myLibrary = MyLibrary()
        // Act
        val sum = myLibrary.add(a, b)
        // Assert
        assertEquals(4, sum)
    }
}