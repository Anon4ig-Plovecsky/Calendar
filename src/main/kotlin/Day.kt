import MainWindow.Companion.enteredMouseOutlineColor
import MainWindow.Companion.enteredMouseAreaColor
import MainWindow.Companion.pressedMouseAreaColor
import MainWindow.Companion.currentDayAreaColor
import MainWindow.Companion.standardColor
import MainWindow.Companion.dayHeight
import MainWindow.Companion.dayWidth
import javax.swing.JLabel
import java.awt.*

open class Day(
    private var currentDay: Boolean,
) : Button() {
    private val numberOfDayLabel = JLabel()
    var pressedDay = false
    //------------------------Initialize------------------------//
    init {
        callAddNumberOfDayLabel()
    }
    private fun callAddNumberOfDayLabel() {
        add(numberOfDayLabel)
    }
    //---------------------Override methods---------------------//
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        isOpaque = true
    }
    override fun getPreferredSize(): Dimension = Dimension(dayWidth, dayHeight)
    //--------------------Implemented methods-------------------//
    override fun enteredMouse(g: Graphics?) {
        if(!currentDay && !pressedDay) {
            background = enteredMouseAreaColor
            g?.color = enteredMouseOutlineColor
            g?.drawRect(1, 1, dayWidth - 2, dayHeight - 1)
            g?.drawLine(1, dayHeight - 1, dayWidth - 2, dayHeight - 1)
        } else exitedMouse(g)
    }
    override fun exitedMouse(g: Graphics?) {
        super.exitedMouse(g)
        if(currentDay) {
            background = currentDayAreaColor
            g?.color = MainWindow.currentDayOutlineColor
        } else if(pressedDay) {
            background = pressedMouseAreaColor
            g?.color = MainWindow.pressedMouseOutlineColor
        } else {
            background = standardColor
            g?.color = standardColor
        }
        g?.drawRect(1, 1, dayWidth - 2, dayHeight - 1)
        g?.drawLine(1, dayHeight - 1, dayWidth - 2, dayHeight - 1)
    }
    override fun releasedMouse(g: Graphics?) {
        super.releasedMouse(g)
        exitedMouse(g)
    }
    override fun actionButton(g: Graphics?) {
        pressedDay = true
        exitedMouse(g)
    }
    //------------------------------------------------------------
    fun setNumberOfDay(numberOfDayString: String, currentMonth: Boolean) {
        layout = GridBagLayout()
        numberOfDayLabel.text = numberOfDayString
        numberOfDayLabel.font = Font("Noto Sans", Font.PLAIN, 14)
        if(currentMonth)
            numberOfDayLabel.foreground = Color.WHITE
        else
            numberOfDayLabel.foreground = MainWindow.nonCurrentDaysColor
    }
    private fun setCurrentDay(currentDay: Boolean) {
        this.currentDay = currentDay
    }
}