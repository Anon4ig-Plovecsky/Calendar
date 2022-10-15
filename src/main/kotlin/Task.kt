import javax.swing.*
import java.awt.*

class Task(
    private val taskName: String,
    private val isDone: Boolean
) : JPanel() {
    private lateinit var jTextField: JTextField
    //-------------------------Buttons-------------------------//
    private lateinit var deleteButton: FunctionButton
    private lateinit var taskDoneButton: FunctionButton
    //------------------------Initialize-----------------------//
    init {
        background = MainWindow.panelColor
        setTask()
    }
    //---------------------Override methods--------------------//
    override fun getPreferredSize(): Dimension = Dimension(MainWindow.taskWidth, MainWindow.taskHeight)
    override fun getMinimumSize(): Dimension = Dimension(MainWindow.taskWidth, MainWindow.taskHeight)
    //-----------------------------------------------------------
    private fun setTask() {
        jTextField = JTextField(taskName)
        jTextField.minimumSize = Dimension(315, 30)
        jTextField.preferredSize = Dimension(315, 30)
        jTextField.background = MainWindow.panelColor
        jTextField.foreground = MainWindow.whiteColor
        jTextField.font = Font("Noto Sans", Font.PLAIN, 14)
        jTextField.border = BorderFactory.createEmptyBorder()
        add(jTextField)
        deleteButton = FunctionButton("-")
        add(deleteButton)
        taskDoneButton = FunctionButton("done")
        add(taskDoneButton)
    }
}