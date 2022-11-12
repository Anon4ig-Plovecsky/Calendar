import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.ParameterizedTest
import java.util.stream.Stream

internal class DatabaseTest {
    @ParameterizedTest
    @MethodSource("tasksToAdd")
    fun addOrUpdateAndFindTest(task: String, date: String, isDone: String) {
        mongodbDatabase.addOrUpdate(date, task, "", isDone == "true") //Добавление в бд со значением taskIsDone = "true" по умолчанию
        mongodbDatabase.addOrUpdate(date, task, task, isDone == "true") //Изменение значения taskIsDone
        var taskIsFound = false
        val findResult = mongodbDatabase.find(date)
        for(result in findResult)
            if(result[MainWindow.keyTaskName] == task &&
                    result[MainWindow.keyIsDone] == isDone)
                taskIsFound = true
        assert(taskIsFound)
    }
    @ParameterizedTest
    @MethodSource("tasksToDelete")
    fun deleteTest(date: String, task: String) {
        mongodbDatabase.delete(date, task)
        var taskIsFound = false
        val findResult = mongodbDatabase.find(date)
        for(result in findResult)
            if(result[MainWindow.keyTaskName] == task)
                taskIsFound = true
        assert(!taskIsFound)
    }
    companion object {
        @JvmStatic val mongodbDatabase: Database by lazy { setUpDatabase() }
        @JvmStatic fun setUpDatabase(): Database {
            val database = Database.getDatabase()
            return database ?: Database()
        }
        @JvmStatic fun tasksToAdd(): Stream<Arguments> = Stream.of(
            Arguments.of("Test№1", "2022-9-12", "true"),
            Arguments.of("Test№2", "1993-3-5", "false"),
            Arguments.of("Test№3", "1991-11-22", "false")
        )
        @JvmStatic fun tasksToDelete(): Stream<Arguments> = Stream.of(
            Arguments.of("2022-9-12", "Test№1"),
            Arguments.of("1993-3-5", "Test№2"),
            Arguments.of("1991-11-22", "Test№3")
        )
    }
}