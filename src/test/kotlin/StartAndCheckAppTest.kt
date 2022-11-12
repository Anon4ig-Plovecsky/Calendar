import org.netbeans.jemmy.operators.ComponentOperator
import org.netbeans.jemmy.operators.JFrameOperator
import org.junit.jupiter.api.BeforeAll
import org.netbeans.jemmy.QueueTool
import org.junit.jupiter.api.Test
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.awt.Robot

//----------Enable "US" keyboard layout before testing!!!----------//
class StartAndCheckAppTest {
    companion object {
        private lateinit var mainFrame: JFrameOperator
        private lateinit var queueTool: QueueTool
        private lateinit var robot: Robot
        @BeforeAll
        @JvmStatic fun startApplication() {
            main()
            queueTool = QueueTool()
            mainFrame = JFrameOperator("Календарь")
        }
    }
    @Test
    fun checkAddAndUpdateTaskTest() {
        val expectedTask = "Test"
        val expectedTaskIsDone = "true"
        var testCompleted = false
        val buttonYearsPanel = ComponentOperator(mainFrame, 10)
        val buttonPreviousYears = ComponentOperator(mainFrame, 12)
        pressButtonByRobot(buttonYearsPanel)
        pressButtonByRobot(buttonPreviousYears)
        pressButtonByRobot(buttonPreviousYears)
        val buttonYear = ComponentOperator(mainFrame, 22)
        pressButtonByRobot(buttonYear)
        queueTool.waitEmpty(500)
        val buttonMonth = ComponentOperator(mainFrame, 26)
        pressButtonByRobot(buttonMonth)
        val buttonDay = ComponentOperator(mainFrame, 46)
        pressButtonByRobot(buttonDay)
        val buttonAddNewTask = ComponentOperator(mainFrame, 121)
        pressButtonByRobot(buttonAddNewTask)
        queueTool.waitEmpty(500)
        val taskJLabel = ComponentOperator(mainFrame, 122)
        val location = taskJLabel.locationOnScreen
        robot.mouseMove(location.x - 50, location.y + 15)
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
        //-----------------Entering "Test" in task panel-----------------//
        robot.keyPress(KeyEvent.VK_CONTROL)
        robot.keyPress(KeyEvent.VK_BACK_SPACE)
        robot.keyRelease(KeyEvent.VK_BACK_SPACE)
        robot.keyPress(KeyEvent.VK_BACK_SPACE)
        robot.keyRelease(KeyEvent.VK_BACK_SPACE)
        robot.keyPress(KeyEvent.VK_BACK_SPACE)
        robot.keyRelease(KeyEvent.VK_BACK_SPACE)
        robot.keyRelease(KeyEvent.VK_CONTROL)
        robot.keyPress(KeyEvent.VK_SHIFT)
        robot.keyPress(KeyEvent.VK_T)
        robot.keyRelease(KeyEvent.VK_T)
        robot.keyRelease(KeyEvent.VK_SHIFT)
        robot.keyPress(KeyEvent.VK_E)
        robot.keyRelease(KeyEvent.VK_E)
        robot.keyPress(KeyEvent.VK_S)
        robot.keyRelease(KeyEvent.VK_S)
        robot.keyPress(KeyEvent.VK_T)
        robot.keyRelease(KeyEvent.VK_T)
        robot.keyPress(KeyEvent.VK_ENTER)
        robot.keyRelease(KeyEvent.VK_ENTER)
        //-----------------------------------------------------------------
        queueTool.waitEmpty(1000)
        val buttonTaskIsDone = ComponentOperator(mainFrame, 123)
        pressButtonByRobot(buttonTaskIsDone)
        val database = Database.getDatabase()
        val results = database!!.find("2002-5-3")
        for(result in results)
            if(result[MainWindow.keyTaskName] == expectedTask &&
                    result[MainWindow.keyIsDone] == expectedTaskIsDone)
                testCompleted = true
        queueTool.waitEmpty(2000)
        assert(testCompleted)
    }
    //--------------------Run after the first test!------------------//
    @Test
    fun deleteTaskTest() {
        val taskName = "Test"
        var taskIsFound = false
        val buttonDeleteTask = ComponentOperator(mainFrame, 122)
        pressButtonByRobot(buttonDeleteTask)
        queueTool.waitEmpty(1000)
        val database = Database.getDatabase()
        val results = database!!.find("2002-5-3")
        for(result in results)
            if(result[MainWindow.keyTaskName] == taskName)
                taskIsFound = true
        assert(!taskIsFound)
    }
    //-----------------------------------------------------------------
    private fun pressButtonByRobot(component: ComponentOperator) {
        val locationComponent = component.locationOnScreen
        robot = Robot(MainWindow.graphicsDevice)
        robot.mouseMove(locationComponent.x + 15, locationComponent.y + 15)
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
        queueTool.waitEmpty(300)
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
        queueTool.waitEmpty(800)
    }
}