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
import EnumMouseEvent.*
import java.awt.*

class Day(
    private var currentDay: Boolean,
) : JPanel() {
    var mouseEvent: EnumMouseEvent = EXITED
    val numberOfDayLabel = JLabel()
    init {
        addMouseListener(object : MouseAdapter() {
            override fun mouseEntered(e: MouseEvent?) {
                super.mouseEntered(e)
                mouseEvent = ENTERED
                repaint()
            }
            override fun mouseExited(e: MouseEvent?) {
                super.mouseExited(e)
                mouseEvent = EXITED
                repaint()
            }
        })
        add(numberOfDayLabel)
    }
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        isOpaque = true
        when(mouseEvent) {
            ENTERED -> enteredMouse(g)
            EXITED -> setDay(g)
            PRESSED -> {

            }
            RELEASED -> {

            }

        }
    }
    override fun getPreferredSize(): Dimension = Dimension(dayWidth, dayHeight)
    fun setDay(g: Graphics?) {
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
    fun setNumberOfDay(numberOfDayString: String, currentMonth: Boolean) {
        layout = GridBagLayout()
        numberOfDayLabel.text = numberOfDayString
        numberOfDayLabel.font = Font("Noto Sans", Font.PLAIN, 14)
        if(currentMonth)
            numberOfDayLabel.foreground = Color.WHITE
        else
            numberOfDayLabel.foreground = MainWindow.nonCurrentDaysColor
    }
    private fun enteredMouse(g: Graphics?) {
        if(!currentDay) {
            background = enteredMouseAreaColor
            g?.color = enteredMouseOutlineColor
            g?.drawRect(1, 1, dayWidth - 2, dayHeight - 1)
            g?.drawLine(1, dayHeight - 1, dayWidth - 2, dayHeight - 1)
        } else setDay(g)
    }
    private fun setCurrentDay(currentDay: Boolean) {
        this.currentDay = currentDay
    }
}