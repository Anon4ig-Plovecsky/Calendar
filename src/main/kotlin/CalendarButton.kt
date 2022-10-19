import MainWindow.Companion.enteredMouseOutlineColor
import MainWindow.Companion.enteredMouseAreaColor
import MainWindow.Companion.pressedMouseAreaColor
import MainWindow.Companion.currentDayAreaColor
import MainWindow.Companion.standardColor
import javax.swing.JLabel
import java.awt.*

open class CalendarButton(
    private var isCurrent: Boolean,
    private val buttonWidth: Int,
    private val buttonHeight: Int
) : Button() {
    private val jLabelInCalendarButton = JLabel()
    var pressedCalendarButton = false
    //------------------------Initialize------------------------//
    init {
        callAddNumberOfDayLabel()
    }
    private fun callAddNumberOfDayLabel() {
        add(jLabelInCalendarButton)
    }
    //---------------------Override methods---------------------//
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        isOpaque = true
    }
    override fun getPreferredSize(): Dimension = Dimension(buttonWidth, buttonHeight)
    //--------------------Implemented methods-------------------//
    override fun enteredMouse(g: Graphics?) {
        if(!isCurrent && !pressedCalendarButton) {
            background = enteredMouseAreaColor
            g?.color = enteredMouseOutlineColor
            g?.drawRect(1, 1, buttonWidth - 2, buttonHeight - 1)
            g?.drawLine(1, buttonHeight - 1, buttonWidth - 2, buttonHeight - 1)
        } else exitedMouse(g)
    }
    override fun exitedMouse(g: Graphics?) {
        super.exitedMouse(g)
        if(isCurrent) {
            background = currentDayAreaColor
            g?.color = MainWindow.currentDayOutlineColor
        } else if(pressedCalendarButton) {
            background = pressedMouseAreaColor
            g?.color = MainWindow.pressedMouseOutlineColor
        } else {
            background = standardColor
            g?.color = standardColor
        }
        g?.drawRect(1, 1, buttonWidth - 2, buttonHeight - 1)
        g?.drawLine(1, buttonHeight - 1, buttonWidth - 2, buttonHeight - 1)
    }
    override fun releasedMouse(g: Graphics?) {
        super.releasedMouse(g)
        exitedMouse(g)
    }
    override fun actionButton(g: Graphics?) {
        pressedCalendarButton = true
        exitedMouse(g)
    }
    //------------------------------------------------------------
    fun setJLabelInCalendarButton(jLabelInCalendarButtonString: String, isCurrent: Boolean = true) {
        layout = GridBagLayout()
        jLabelInCalendarButton.text = jLabelInCalendarButtonString
        jLabelInCalendarButton.font = Font("Noto Sans", Font.PLAIN, 14)
        if(isCurrent)
            jLabelInCalendarButton.foreground = Color.WHITE
        else
            jLabelInCalendarButton.foreground = MainWindow.nonCurrentDaysColor
        repaint()
    }
    fun getJLabelInCalendarButton(): JLabel = jLabelInCalendarButton
    fun setIsCurrent(isCurrent: Boolean) {
        this.isCurrent = isCurrent
    }
}