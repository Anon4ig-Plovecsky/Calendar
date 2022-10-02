import javax.swing.*
import java.awt.*

class MainWindow : JFrame() {
    companion object {
        @JvmStatic
        val standardColor = Color(39, 39, 53)
        val panelColor = Color(28, 28, 36)
        val whiteColor = Color(207, 199, 190)
    }
    private val windowWidth = 420
    private val windowHeight = 350
    private val days = Array(42) { Day() }
    private lateinit var layoutFrame: GridBagLayout
    private lateinit var gridBagConstraints:GridBagConstraints
//    private lateinit var buttonDays: JButton
//    private lateinit var buttonMonths: JButton
//    private lateinit var buttonYears: JButton
    private lateinit var buttonDays: Button
    private lateinit var buttonMonths: Button
    private lateinit var buttonYears: Button
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
        for(row in 0 until 6)
            for (column in 0 until 7) {
                setGridBagConstraint(column * 2, row + 1, 1, 2)
                add(days[row * 7 + column], gridBagConstraints)
                days[row * 7 + column].repaint()
            }
    }
    private fun setArrowButtonsPanel() {
        val backgroundArrowButtonsPanel = object : JPanel() {
            override fun paintComponent(g: Graphics?) {
                super.paintComponent(g)
                background = panelColor
                g?.drawRect(-1, -1, windowWidth + 1, 50)
                isOpaque = true
            }
            override fun getPreferredSize(): Dimension {
                return Dimension(windowWidth, 50)
            }
        }
        setButtons()
//        buttonDays = JButton("Дни")
//        buttonMonths = JButton("Месяцы")
//        buttonYears = JButton("Годы")
//        buttonDays.background = Color(28, 28, 36)
//        buttonMonths.background = Color(28, 28, 36)
//        buttonYears.background = Color(28, 28, 36)
//        buttonDays.foreground = Color(255, 255, 255)
//        buttonMonths.foreground = Color(255, 255, 255)
//        buttonYears.foreground = Color(255, 255, 255)
//        buttonDays.font = Font("Noto Sans", Font.PLAIN, 13)
        //------------------Background-------------------//
        setGridBagConstraint(0, 0, 1, 14)
        add(backgroundArrowButtonsPanel, gridBagConstraints)
//        //----------Button days-----------//
//        setGridBagConstraint(0, 0, 1, 3)
//        add(buttonDays, gridBagConstraints)
//        //---------Button months----------//
//        setGridBagConstraint(3, 0, 1, 4)
//        add(buttonMonths, gridBagConstraints)
//        //----------Button years----------//
//        setGridBagConstraint(7, 0, 1, 3)
//        add(buttonYears, gridBagConstraints)
    }
    private fun setButtons() {
        buttonDays = Button(120, 40, "Дни")
        setGridBagConstraint(0, 0, 1, 5)
        add(buttonDays, gridBagConstraints)
//        buttonMonths = Button(80, 20, "Месяцы")
//        setGridBagConstraint(3, 0, 1, 4)
//        add(buttonMonths, gridBagConstraints)
//        buttonYears = Button(70, 20, "Годы")
//        setGridBagConstraint(7, 0, 1, 3)
//        add(buttonYears, gridBagConstraints)
    }
    private fun setGridBagConstraint(gridX: Int, gridY: Int, gridHeight: Int, gridWidth: Int) {
        gridBagConstraints.gridx = gridX
        gridBagConstraints.gridy = gridY
        gridBagConstraints.gridheight = gridHeight
        gridBagConstraints.gridwidth = gridWidth
    }
}