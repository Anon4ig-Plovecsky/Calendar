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
        val currentDayOutlineColor = Color(133, 173, 72)
        val currentDayAreaColor = Color(80, 94, 67)
        val enteredMouseAreaColor = Color(66, 70, 67)
        val enteredMouseOutlineColor = Color(81, 95, 69)
        val pressedMouseAreaColor = Color(72, 82, 68)
        val pressedMouseOutlineColor = Color(103, 129, 71)
        val nonCurrentDaysColor = Color(104, 105, 106)
        //-----------------------Dimensions---------------------//
        const val windowWidth = 420
        const val windowHeight = 460
        const val dayWidth = 60
        const val dayHeight = 50
        const val buttonWidth = 40
        const val buttonHeight = 40
    }
    private lateinit var days: Array<Day>
    private lateinit var monthNames: Array<String>
    //-----------------------Layouts------------------------//
    private lateinit var layoutFrame: BoxLayout
    private lateinit var gridLayout: GridLayout
    //------------------------Panels------------------------//
    private lateinit var buttonsPanel: JPanel
    private lateinit var totalCalendarPanel: JPanel
    private lateinit var calendarPanel: JPanel
    private lateinit var daysOfWeekPanel: JPanel
    private lateinit var monthPanel: MonthPanel
    //--------------------Required time---------------------//
    private var requireDay: Int = 0
    private var requireMonth: Int = 0
    private var requireYear: Int = 0
    //-----------------------Buttons------------------------//
    private lateinit var buttonDays: NavigationButton
    private lateinit var buttonMonths: NavigationButton
    private lateinit var buttonYears: NavigationButton
    private lateinit var buttonPreviousMonth: FunctionButton
    private lateinit var buttonFollowingMonth: FunctionButton
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
        setTotalCalendarPanel()
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
        buttonsPanel = object : JPanel() {
            override fun paintComponent(g: Graphics?) {
                super.paintComponent(g)
                background = panelColor
                g?.drawRect(-1, -1, windowWidth + 1, 50)
            }

            override fun getPreferredSize(): Dimension = Dimension(windowWidth, 50)
        }
        //---------------------------Days----------------------------//
        buttonDays = NavigationButton(90, 40, "Дни")
        buttonsPanel.add(buttonDays)
        buttonDays.setText()
        //--------------------------Months---------------------------//
        buttonMonths = NavigationButton(120, 40, "Месяцы")
        buttonsPanel.add(buttonMonths)
        buttonMonths.setText()
        //---------------------------Years---------------------------//
        buttonYears = NavigationButton(90, 40, "Годы")
        buttonsPanel.add(buttonYears)
        buttonYears.setText()
        //-----------------------Previous month----------------------//
        buttonPreviousMonth = FunctionButton("<")
        buttonsPanel.add(buttonPreviousMonth)
        //-----------------------Following month---------------------//
        buttonFollowingMonth = FunctionButton(">")
        buttonsPanel.add(buttonFollowingMonth)
        add(buttonsPanel)
    }
    private fun setTotalCalendarPanel() {
        totalCalendarPanel = JPanel()
        totalCalendarPanel.layout = BoxLayout(totalCalendarPanel, BoxLayout.Y_AXIS)
        totalCalendarPanel.size = Dimension(windowWidth, 350)
        setDaysOfMonth()
        setCalendar()
        add(totalCalendarPanel)
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
        totalCalendarPanel.add(daysOfWeekPanel)
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
        days = Array(42) { i -> object : Day(dateQualifier.getInfoAboutMonth()[i][keyCurrentDay] == 1) {
            override fun actionButton(g: Graphics?) {
                super.actionButton(g)

            }
        } }
        for(i in days.indices) {
            calendarPanel.add(days[i])
            days[i].setNumberOfDay(
                dateQualifier.getInfoAboutMonth()[i][keyDay].toString(),
                dateQualifier.getInfoAboutMonth()[i][keyCurrentMonth] == 1
            )
        }
        totalCalendarPanel.add(calendarPanel)
    }
    //----------------------------------------------------------------------------
}