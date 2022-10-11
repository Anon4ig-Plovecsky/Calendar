import MainWindow.Companion.enteredMouseOutlineColor
import MainWindow.Companion.enteredMouseAreaColor
import MainWindow.Companion.selectedAreaColor
import MainWindow.Companion.standardColor
import MainWindow.Companion.dayHeight
import MainWindow.Companion.dayWidth
import javax.swing.JLabel
import java.awt.*

class Day(
    private var currentDay: Boolean,
) : Button() {
    private val numberOfDayLabel = JLabel()
    init {
        add(numberOfDayLabel)
    }
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        isOpaque = true
    }
    override fun getPreferredSize(): Dimension = Dimension(dayWidth, dayHeight)
    fun setNumberOfDay(numberOfDayString: String, currentMonth: Boolean) {
        layout = GridBagLayout()
        numberOfDayLabel.text = numberOfDayString
        numberOfDayLabel.font = Font("Noto Sans", Font.PLAIN, 14)
        if(currentMonth)
            numberOfDayLabel.foreground = Color.WHITE
        else
            numberOfDayLabel.foreground = MainWindow.nonCurrentDaysColor
    }
    override fun enteredMouse(g: Graphics?) {
        if(!currentDay) {
            background = enteredMouseAreaColor
            g?.color = enteredMouseOutlineColor
            g?.drawRect(1, 1, dayWidth - 2, dayHeight - 1)
            g?.drawLine(1, dayHeight - 1, dayWidth - 2, dayHeight - 1)
        } else exitedMouse(g)
    }
    override fun exitedMouse(g: Graphics?) {
        if(currentDay) {
            background = selectedAreaColor
            g?.color = MainWindow.selectedOutlineColor
        }
        else {
            background = standardColor
            g?.color = standardColor
        }
        g?.drawRect(1, 1, dayWidth - 2, dayHeight - 1)
        g?.drawLine(1, dayHeight - 1, dayWidth - 2, dayHeight - 1)
    }
    override fun pressedMouse(g: Graphics?) {
        TODO("Not yet implemented")
    }
    override fun releasedMouse(g: Graphics?) {
        TODO("Not yet implemented")
    }
    private fun setCurrentDay(currentDay: Boolean) {
        this.currentDay = currentDay
    }
}