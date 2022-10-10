import MainWindow.Companion.setGridBagConstraint
import MainWindow.Companion.standardColor
import MainWindow.Companion.dayHeight
import MainWindow.Companion.dayWidth
import javax.swing.JPanel
import javax.swing.JLabel
import java.awt.*

class Day(
    private val numberOfDay: Int?,
    private val currentDay: Boolean,
    private val currentMonth: Boolean
) : JPanel() {
    private var draw: Graphics? = graphics
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        background = standardColor
        draw = g!!
        isOpaque = true
        setDay(numberOfDay, currentDay, currentMonth)
    }
    override fun getPreferredSize(): Dimension {
        return Dimension(dayWidth, dayHeight)
    }
    fun setDay(numberOfDay: Int?, currentDay: Boolean, currentMonth: Boolean) {
        if(currentDay)
            draw?.color = MainWindow.highlightedAreaColor
        else draw?.color = standardColor
        draw?.drawRect(0, 0, dayWidth - 1, dayHeight - 1)
        setNumberOfDay(numberOfDay.toString(), currentDay, currentMonth)
    }
    fun setNumberOfDay(numberOfDayString: String, currentDay: Boolean, currentMonth: Boolean) {
        layout = GridBagLayout()
        val gridBagConstraints = GridBagConstraints()
        val numberOfDayLabel = JLabel(numberOfDayString)
        numberOfDayLabel.font = Font("Noto Sans", Font.PLAIN, 14)
        if(currentDay)
            numberOfDayLabel.foreground = MainWindow.highlightedAreaColor
        else if(currentMonth)
            numberOfDayLabel.foreground = Color.WHITE
        else
            numberOfDayLabel.foreground = MainWindow.nonCurrentDaysColor
        setGridBagConstraint(gridBagConstraints, 0, 0, 1, 1)
        add(numberOfDayLabel, gridBagConstraints)
    }
}