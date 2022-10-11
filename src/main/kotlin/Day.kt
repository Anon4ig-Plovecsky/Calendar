import MainWindow.Companion.enteredMouseOutlineColor
import MainWindow.Companion.enteredMouseAreaColor
import MainWindow.Companion.selectedAreaColor
import MainWindow.Companion.standardColor
import MainWindow.Companion.dayHeight
import MainWindow.Companion.dayWidth
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JPanel
import javax.swing.JLabel
import java.awt.*

class Day(
    private val currentDay: Boolean,
) : JPanel() {
    init {
        addMouseListener(object : MouseAdapter() {
            override fun mouseEntered(e: MouseEvent?) {
                super.mouseEntered(e)
                exited = true
                repaint()
            }
            override fun mouseExited(e: MouseEvent?) {
                super.mouseExited(e)
            }
        })
    }
    private var exited: Boolean = false
    private var draw: Graphics? = graphics
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        draw = g!!
        if(exited)
            enteredMouse()
        else {
            background = standardColor
            isOpaque = true
            setDay(currentDay)
        }
    }
    override fun getPreferredSize(): Dimension = Dimension(dayWidth, dayHeight)
    fun setDay(currentDay: Boolean) {

        if(currentDay) {
            background = selectedAreaColor
            draw?.color = MainWindow.selectedOutlineColor
        }
        else {
            background = standardColor
            draw?.color = standardColor
        }
        draw?.drawRect(1, 1, dayWidth - 2, dayHeight - 1)
        draw?.drawLine(1, dayHeight - 1, dayWidth - 2, dayHeight - 1)
    }
    fun setNumberOfDay(numberOfDayString: String, currentDay: Boolean, currentMonth: Boolean) {
        layout = GridBagLayout()
        val numberOfDayLabel = JLabel(numberOfDayString)
        numberOfDayLabel.font = Font("Noto Sans", Font.PLAIN, 14)
        if(currentMonth)
            numberOfDayLabel.foreground = Color.WHITE
        else
            numberOfDayLabel.foreground = MainWindow.nonCurrentDaysColor
        add(numberOfDayLabel)
    }
    private fun enteredMouse() {
        repaint()
        background = enteredMouseAreaColor
        draw?.color = enteredMouseOutlineColor
        draw?.drawRect(1, 1, dayWidth - 2, dayHeight - 1)
        draw?.drawLine(1, dayHeight - 1, dayWidth - 2, dayHeight - 1)
    }
}