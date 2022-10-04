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
        const val windowHeight = 350
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
        isVisible = true
        createLayout()
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
        setCalendar()
    }
    private fun setCalendar() {
        val dateQualifier = DateQualifier()
        dateQualifier.setInfoAboutMonth()
        days = Array(42) { i ->
            Day(
                dateQualifier.getInfoAboutMonth()[i][keyDay],
                dateQualifier.getInfoAboutMonth()[i][keyCurrentDay] == 1,
                dateQualifier.getInfoAboutMonth()[i][keyCurrentMonth] == 1
            )
        }
        for(row in 0 until 6)
            for (column in 0 until 7) {
                gridBagConstraints = setGridBagConstraint(gridBagConstraints, column * 3, row * 3 + 3, 3, 2)
                add(days[row * 7 + column], gridBagConstraints)
            }
    }
    private fun setArrowButtonsPanel() {
        buttonsPanel = ButtonsPanel()
        gridBagConstraints = setGridBagConstraint(gridBagConstraints, 0, 0, 3, 21)
        add(buttonsPanel, gridBagConstraints)
        buttonsPanel.setButtons()
    }
}