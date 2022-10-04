import MainWindow.Companion.standardColor
import javax.swing.JPanel
import javax.swing.JLabel
import java.awt.*

class Day (
    private val numberOfDay: Int?,
    private val currentDay: Boolean,
    private val currentMonth: Boolean
    ) : JPanel() {
    private val dayWidth = 60
    private val dayHeight = 50
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        background = standardColor
        if(currentDay)
            g?.color = MainWindow.highlightedAreaColor
        else g?.color = standardColor
        g?.drawRect(0, 0, dayWidth - 1, dayHeight - 1)
        setNumberOfDay()
    }
    override fun getPreferredSize(): Dimension {
        return Dimension(dayWidth, dayHeight)
    }
    private fun setNumberOfDay() {
        layout = GridBagLayout()
        val numberOfDay = JLabel(numberOfDay.toString())
        numberOfDay.font = Font("Noto Sans", Font.PLAIN, 14)
        if(currentDay)
            numberOfDay.foreground = MainWindow.highlightedAreaColor
        else if(currentMonth)
            numberOfDay.foreground = Color.WHITE
        else
            numberOfDay.foreground = MainWindow.nonCurrentDaysColor
        add(numberOfDay)
    }
}