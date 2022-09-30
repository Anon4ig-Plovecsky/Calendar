import javax.swing.*
import java.awt.*

class MainWindow : JFrame() {
    private val calendarWidth = 420
    private val calendarHeight = 300
    lateinit var calendar: JPanel
    private val days = Array(42) { Day() }
    fun startApplication() {
        layout = SpringLayout()
        setCalendar()
        add(calendar)
        title = "Календарь"
        defaultCloseOperation = EXIT_ON_CLOSE
        background = Color(28, 28, 36)
        isResizable = false
        pack()
        setLocationRelativeTo(null)
        for(day in days.iterator())
            calendar.add(day)
        isVisible = true
    }
    private fun setCalendar() {
        calendar = JPanel()
        calendar.preferredSize = Dimension(calendarWidth, calendarHeight)
        calendar.background = Color(28, 28, 36)
        calendar.layout = GridLayout(0, 7)
    }
}