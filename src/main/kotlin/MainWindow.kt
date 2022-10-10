import javax.swing.*
import java.awt.*

class MainWindow : JFrame() {
    companion object {
        @JvmStatic val keyDay = "NUMBER_OF_DAY"
        @JvmStatic val keyCurrentMonth = "CURRENT_MONTH"
        @JvmStatic val keyCurrentDay = "CURRENT_DAY"
        val standardColor = Color(39, 39, 53)
        val panelColor = Color(28, 28, 36)
        val whiteColor = Color(207, 199, 190)
        val highlightedAreaColor = Color(133, 173, 72)
        val nonCurrentDaysColor = Color(104, 105, 106)
        const val windowWidth = 420
        const val windowHeight = 400
        const val dayWidth = 60
        const val dayHeight = 50
        fun setGridBagConstraint(gridBagConstraints: GridBagConstraints, gridX: Int, gridY: Int, gridHeight: Int, gridWidth: Int) : GridBagConstraints {
            gridBagConstraints.gridx = gridX
            gridBagConstraints.gridy = gridY
            gridBagConstraints.gridheight = gridHeight
            gridBagConstraints.gridwidth = gridWidth
            return gridBagConstraints
        }
    }
    private lateinit var days: Array<Day>
    private lateinit var layoutFrame: GridBagLayout
    private lateinit var gridBagConstraints:GridBagConstraints
    private lateinit var buttonsPanel: ButtonsPanel
    fun startApplication() {
        preferredSize = Dimension(windowWidth, windowHeight)
        minimumSize = Dimension(windowWidth, windowHeight)
        createLayout()
        isVisible = true
        title = "Календарь"
        defaultCloseOperation = EXIT_ON_CLOSE
        contentPane.background = standardColor
        contentPane.foreground = standardColor
        isResizable = false
        setLocationRelativeTo(null)
        pack()
    }
    private fun createLayout() {
        layoutFrame = GridBagLayout()
        gridBagConstraints = GridBagConstraints()
        layout = layoutFrame
        setArrowButtonsPanel()
        setDaysOfMonth()
        setCalendar()
    }
    private fun setDaysOfMonth() {
        val daysOfMonthStrings = arrayOf("пн", "вт", "ср", "чт", "пт", "сб", "вс")
        val daysOfMonthObjects = Array(7) { i ->
            object : JPanel() {
                override fun paintComponent(g: Graphics?) {
                    super.paintComponent(g)
                    background = standardColor
                    isOpaque = true
                    g?.color = standardColor
                    g?.drawRect(0, 0, dayWidth - 1, dayHeight - 1)
                    setText()
                }
                override fun getPreferredSize(): Dimension = Dimension(dayWidth, dayHeight)
                fun setText() {
                    layout = GridBagLayout()
                    val dayOfWeek = JLabel(daysOfMonthStrings[i])
                    dayOfWeek.foreground = nonCurrentDaysColor
                    dayOfWeek.font = Font("Noto Sans", Font.PLAIN, 14)
                    add(dayOfWeek)
                }
            }
        }
        for(column in 0 until 7) {
            gridBagConstraints = setGridBagConstraint(gridBagConstraints,
                column * 3, 3, 3, 3)
            add(daysOfMonthObjects[column], gridBagConstraints)
        }
    }
    private fun setCalendar() {
        val dateQualifier = DateQualifier()
        dateQualifier.setInfoAboutMonth()
        days = Array(42) { i -> Day(
            dateQualifier.getInfoAboutMonth()[i][keyDay],
            dateQualifier.getInfoAboutMonth()[i][keyCurrentDay] == 1,
            dateQualifier.getInfoAboutMonth()[i][keyCurrentMonth] == 1
        ) }
        for(row in 0 until 6)
            for (column in 0 until 7) {
                gridBagConstraints = setGridBagConstraint(gridBagConstraints,
                    column * 3, row * 3 + 6, 3, 3)
                add(days[row * 7 + column], gridBagConstraints)
//                days[row * 7 + column].setDay(
//                    dateQualifier.getInfoAboutMonth()[row * 7 + column][keyDay],
//                    dateQualifier.getInfoAboutMonth()[row * 7 + column][keyCurrentDay] == 1,
//                    dateQualifier.getInfoAboutMonth()[row * 7 + column][keyCurrentMonth] == 1
//                )
            }
    }
    private fun setArrowButtonsPanel() {
        buttonsPanel = ButtonsPanel()
        gridBagConstraints = setGridBagConstraint(gridBagConstraints, 0, 0, 3, 21)
        add(buttonsPanel, gridBagConstraints)
        buttonsPanel.setButtons()
    }
}