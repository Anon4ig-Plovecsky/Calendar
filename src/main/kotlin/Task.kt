import java.awt.event.ActionEvent
import javax.swing.*
import java.awt.*

open class Task(
    private val date: String,
    private val taskName: String,
    private var isDone: Boolean
) : JPanel(), TaskActivity {
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
        jTextField.foreground = if(isDone) MainWindow.nonCurrentDaysColor else MainWindow.whiteColor
        jTextField.font = Font("Noto Sans", Font.PLAIN, 14)
        jTextField.border = BorderFactory.createEmptyBorder()
        jTextField.addActionListener(object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent?) {
                if(jTextField.text.isNotEmpty())
                    taskIsUpdated()
            }
        })
        add(jTextField)
        deleteButton = object : FunctionButton("-") {
            override fun actionButton(g: Graphics?) {
                super.actionButton(g)
                taskIsDeleted()
            }
        }
        add(deleteButton)
        taskDoneButton = object : FunctionButton("done") {
            override fun actionButton(g: Graphics?) {
                super.actionButton(g)
                isDone = !isDone
                taskIsUpdated()
            }
        }
        add(taskDoneButton)
    }
    //-------------------Implemented methods-------------------//
    override fun taskIsDeleted() {
        Database.getDatabase()?.delete(date, taskName)
    }
    override fun taskIsUpdated() {
        Database.getDatabase()?.addOrUpdate(date, jTextField.text, taskName, isDone)
    }
    //-----------------------------------------------------------
    fun getTaskName(): String = taskName
}