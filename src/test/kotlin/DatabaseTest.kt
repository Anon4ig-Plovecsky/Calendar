import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class DatabaseTest {
    @ParameterizedTest
    @MethodSource("tasksToAdd")
    fun addOrUpdateAndFindTest(task: String, date: String, isDone: String) {
        database.addOrUpdate(date, task, "", isDone == "true") //Добавление в бд со значением taskIsDone = "true" по умолчанию
        database.addOrUpdate(date, task, task, isDone == "true") //Изменение значения taskIsDone
        var taskIsFound = false
        val findResult = database.find(date)
        for(result in findResult)
            if(result[MainWindow.keyTaskName] == task &&
                    result[MainWindow.keyIsDone] == isDone)
                taskIsFound = true
        assert(taskIsFound)
    }
    @ParameterizedTest
    @MethodSource("tasksToDelete")
    fun deleteTest(date: String, task: String) {
        database.delete(date, task)
        var taskIsFound = false
        val findResult = database.find(date)
        for(result in findResult)
            if(result[MainWindow.keyTaskName] == task)
                taskIsFound = true
        assert(!taskIsFound)
    }
    companion object {
        @JvmStatic val database: Database by lazy { setUpDatabase() }
        @JvmStatic fun setUpDatabase(): Database {
            val db = Database.getDatabase()
            return db ?: Database()
        }
        @JvmStatic fun tasksToAdd(): Stream<Arguments> = Stream.of(
            Arguments.of("Test1", "2022-9-12", "true"),
            Arguments.of("Test2", "1993-3-5", "false"),
            Arguments.of("Test3", "1991-11-22", "false")
        )
        @JvmStatic fun tasksToDelete(): Stream<Arguments> = Stream.of(
            Arguments.of("2022-9-12", "Test1"),
            Arguments.of("1993-3-5", "Test2"),
            Arguments.of("1991-11-22", "Test3")
        )
    }
}