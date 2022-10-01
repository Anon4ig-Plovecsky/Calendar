import javax.swing.*
import java.awt.*

class MainWindow : JFrame() {
    private val windowWidth = 420
    private val windowHeight = 350
    private val calendarWidth = 420
    private val calendarHeight = 300
    lateinit var calendar: JPanel
    lateinit var arrowButtonsPanel: JPanel
    private val days = Array(42) { Day() }
    fun startApplication() {
        preferredSize = Dimension(windowWidth, windowHeight)
        createLayout()
        title = "Календарь"
        defaultCloseOperation = EXIT_ON_CLOSE
        background = Color(28, 28, 36)
        isResizable = false
        pack()
        setLocationRelativeTo(null)
        isVisible = true
        for(day in days.iterator())
            calendar.add(day)
        //----------------------
        val day = object : JPanel() {
            override fun paintComponents(g: Graphics?) {
                super.paintComponents(g)
                g?.color = Color(123, 13, 201)
                g?.drawRect(1, 1, 49, 49)
            }

            override fun getPreferredSize(): Dimension {
                return Dimension(50, 50)
            }
        }
        arrowButtonsPanel.add(day)
        arrowButtonsPanel.add(day)
        arrowButtonsPanel.add(day)
        arrowButtonsPanel.add(day)

        day.repaint()
    }
    private fun createLayout() {
//        val layoutFrame = SpringLayout()
        val layoutFrame = BoxLayout(contentPane, BoxLayout.Y_AXIS)
        layout = layoutFrame
        setArrowButtonsPanel()
        setCalendar()
//        add(arrowButtonsPanel)
//        add(calendar)
//        layoutFrame.putConstraint(SpringLayout.WEST, arrowButtonsPanel, 0, SpringLayout.WEST, contentPane)
//        layoutFrame.putConstraint(SpringLayout.NORTH, arrowButtonsPanel, 50, SpringLayout.NORTH, contentPane)
//        layoutFrame.putConstraint(SpringLayout.WEST, calendar, 0, SpringLayout.WEST, contentPane)
//        layoutFrame.putConstraint(SpringLayout.SOUTH, calendar, 300, SpringLayout.NORTH, arrowButtonsPanel)
    }
    private fun setCalendar() {
        calendar = JPanel()
        calendar.preferredSize = Dimension(calendarWidth, calendarHeight)
        calendar.background = Color(28, 28, 36)
        calendar.layout = GridLayout(0, 7)
    }
    private fun setArrowButtonsPanel() {
        arrowButtonsPanel = JPanel()
        arrowButtonsPanel.layout = GridLayout(0, 7)
        arrowButtonsPanel.preferredSize = Dimension(windowWidth, 55)

    }
}