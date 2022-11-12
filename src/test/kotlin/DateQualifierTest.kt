import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.api.Assertions.*
import java.util.stream.Stream

internal class DateQualifierTest {
    @ParameterizedTest
    @MethodSource("receivedMonth")
    fun checkGettingDaysOfMonthTest(dateQualifier: DateQualifier, expected: Array<Int>) {
        dateQualifier.setInfoAboutMonth()
        val actual = Array(42) { i -> dateQualifier.getInfoAboutMonth()[i][MainWindow.keyDay] }
        assertArrayEquals(expected, actual)
    }
    @ParameterizedTest
    @MethodSource("receivedDay")
    fun checkInfoDayTest(dateQualifier: DateQualifier, dayIndex: Int, expected: Boolean) {
        dateQualifier.setInfoAboutMonth()
        val actual = dateQualifier.getInfoAboutMonth()[dayIndex][MainWindow.keyCurrentMonth] == 1
        assertEquals(expected, actual)
    }
    companion object {
        @JvmStatic fun receivedMonth(): Stream<Arguments> = Stream.of(
            Arguments.of(DateQualifier(10, 2022), arrayOf(31, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
                15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)),
            Arguments.of(DateQualifier(5, 2002), arrayOf(27, 28, 29, 30, 31, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 1, 2, 3, 4, 5, 6, 7)),
            Arguments.of(DateQualifier(4, 2003), arrayOf(28, 29, 30, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 1, 2, 3, 4, 5, 6, 7, 8))
        )
        @JvmStatic fun receivedDay(): Stream<Arguments> = Stream.of(
            Arguments.of(DateQualifier(10, 2022), 31, false),
            Arguments.of(DateQualifier(1, 2023), 30, false),
            Arguments.of(DateQualifier(1, 2024), 31, true)
        )
    }
}