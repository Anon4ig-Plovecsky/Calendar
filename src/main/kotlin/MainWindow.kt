import javax.swing.*
import java.util.*
import java.awt.*

class MainWindow : JFrame() {
    companion object {
        //------------------------Keys--------------------------//
        @JvmStatic val keyDay = "NUMBER_OF_DAY"
        @JvmStatic val keyCurrentMonth = "CURRENT_MONTH"
        @JvmStatic val keyCurrentDay = "CURRENT_DAY"
        //------------------------Colors------------------------//
        val standardColor = Color(39, 39, 53)
        val panelColor = Color(28, 28, 36)
        val whiteColor = Color(197, 189, 180)
        val selectedOutlineColor = Color(133, 173, 72)
        val selectedAreaColor = Color(80, 94, 67)
        val enteredMouseAreaColor = Color(66, 70, 67)
        val enteredMouseOutlineColor = Color(81, 95, 69)
        val nonCurrentDaysColor = Color(104, 105, 106)
        //-----------------------Dimensions---------------------//
        const val windowWidth = 420
        const val windowHeight = 460
        const val dayWidth = 60
        const val dayHeight = 50
    }
    private lateinit var days: Array<Day>
    private lateinit var monthNames: Array<String>
    //-----------------------Layouts------------------------//
    private lateinit var layoutFrame: BoxLayout
    private lateinit var gridLayout: GridLayout
    //------------------------Panels------------------------//
    private lateinit var buttonsPanel: ButtonsPanel
    private lateinit var calendarPanel: JPanel
    private lateinit var daysOfWeekPanel: JPanel
    private lateinit var monthPanel: MonthPanel
    //---------------------Required time--------------------//
    private var requireDay: Int = 0
    private var requireMonth: Int = 0
    private var requireYear: Int = 0
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
        layoutFrame = BoxLayout(contentPane, BoxLayout.Y_AXIS)
        layout = layoutFrame
        setMonthPanel()
        setArrowButtonsPanel()
        setDaysOfMonth()
        setCalendar()
    }
    //------------------------------------Set Panels------------------------------------//
    private fun setMonthPanel() {
        monthNames = arrayOf("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь")
        monthPanel = MonthPanel()
        requireMonth = Calendar.getInstance().get(Calendar.MONTH)
        requireYear = Calendar.getInstance().get(Calendar.YEAR)
        monthPanel.setMonth("${monthNames[requireMonth]} $requireYear")
        add(monthPanel)
    }
    private fun setArrowButtonsPanel() {
        buttonsPanel = ButtonsPanel()
        add(buttonsPanel)
        buttonsPanel.setButtons()
    }
    private fun setDaysOfMonth() {
        gridLayout = GridLayout(0, 7)
        daysOfWeekPanel = object : JPanel() {
            init {
                background = standardColor
                layout = gridLayout
            }
            override fun getPreferredSize(): Dimension = Dimension(windowWidth, 50)
        }
        val daysOfMonthStrings = arrayOf("пн", "вт", "ср", "чт", "пт", "сб", "вс")
        val daysOfMonthObjects = Array(7) { i ->
            object : JPanel() {
                override fun paintComponent(g: Graphics?) {
                    super.paintComponent(g)
                    background = standardColor
                    isOpaque = true
                    g?.color = standardColor
                    g?.drawRect(0, 0, dayWidth - 1, dayHeight - 1)
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
            daysOfWeekPanel.add(daysOfMonthObjects[column])
            daysOfMonthObjects[column].setText()
        }
        add(daysOfWeekPanel)
    }
    private fun setCalendar() {
        calendarPanel = object : JPanel() {
            init {
                layout = GridLayout(6, 7)
            }
            override fun getPreferredSize(): Dimension = Dimension(windowWidth, dayHeight * 6)
        }
        val dateQualifier = DateQualifier()
        dateQualifier.setInfoAboutMonth()
        days = Array(42) { i -> Day(dateQualifier.getInfoAboutMonth()[i][keyCurrentDay] == 1) }
        for(i in days.indices) {
            calendarPanel.add(days[i])
            days[i].setNumberOfDay(
                dateQualifier.getInfoAboutMonth()[i][keyDay].toString(),
                dateQualifier.getInfoAboutMonth()[i][keyCurrentMonth] == 1
            )
        }
        add(calendarPanel)
    }
    //----------------------------------------------------------------------------
}