import javax.swing.*
import java.util.*
import java.awt.*

class MainWindow : JFrame() {
    companion object {
        //------------------------Keys--------------------------//
        @JvmStatic val keyCurrentMonth = "CURRENT_MONTH"
        @JvmStatic val keyCurrentDay = "CURRENT_DAY"
        @JvmStatic val keyTaskName = "TASK_NAME"
        @JvmStatic val keyDay = "NUMBER_OF_DAY"
        @JvmStatic val keyIsDone = "IS_DONE"
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
        //----------------------Dimensions----------------------//
        const val dayWidth = 60
        const val dayHeight = 50
        const val taskWidth = 409
        const val taskHeight = 45
        const val monthWidth = 140
        const val monthHeight = 87
        const val buttonWidth = 40
        const val buttonHeight = 40
        const val windowWidth = 420
        const val windowHeight = 710
        const val todoListWidth = 420
        const val todoListHeight = 250
    }
    private lateinit var daysArray: Array<CalendarButton>
    private lateinit var monthsArray: Array<CalendarButton>
    private lateinit var monthNames: Array<String>
    //-----------------------Layouts------------------------//
    private lateinit var layoutFrame: BoxLayout
    private lateinit var gridLayout: GridLayout
    //------------------------Panels------------------------//
    private lateinit var todoList: TodoList
    private lateinit var buttonsPanel: JPanel
    private lateinit var calendarPanel: JPanel
    private lateinit var monthPanel: MonthPanel
    private lateinit var daysOfWeekPanel: JPanel
    private lateinit var totalCalendarPanel: JPanel
    private lateinit var totalCalendarPanelDays: JPanel
    private lateinit var totalCalendarPanelMonths: JPanel
    private lateinit var totalCalendarPanelYears: JPanel
    //--------------------Required date---------------------//
    private var requireMonth: Int = 0
    private var requireYear: Int = 0
    //--------------------Selected date---------------------//
    private var selectedDay: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    private var selectedMonth: Int = Calendar.getInstance().get(Calendar.MONTH)
    private var selectedYear: Int = Calendar.getInstance().get(Calendar.YEAR)
    //-----------------------Buttons------------------------//
    private lateinit var buttonDays: NavigationButton
    private lateinit var buttonMonths: NavigationButton
    private lateinit var buttonYears: NavigationButton
    private lateinit var buttonPreviousMonth: FunctionButton
    private lateinit var buttonFollowingMonth: FunctionButton
    //--------------------------------------------------------
    private var enumCalendarPanels = CalendarPanels.DAYS_PANEL
    private lateinit var database: Database
    fun startApplication() {
        preferredSize = Dimension(windowWidth, windowHeight)
        minimumSize = Dimension(windowWidth, windowHeight)
        createDatabase()
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
        background = standardColor
        setMonthPanel()
        setArrowButtonsPanel()
        setTotalCalendarPanelDays()
        setTotalCalendarPanelMonths()
        setTodoList()
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
            }
            override fun getMinimumSize(): Dimension = Dimension(windowWidth, 50)
            override fun getPreferredSize(): Dimension = Dimension(windowWidth, 50)
        }
        //---------------------------Days----------------------------//
        buttonDays = object : NavigationButton(90, 40, "Дни", true) {
            override fun actionButton(g: Graphics?) {
                super.actionButton(g)
                openDaysPanel()
            }
        }
        buttonsPanel.add(buttonDays)
        buttonDays.setText()
        //--------------------------Months---------------------------//
        buttonMonths = object : NavigationButton(120, 40, "Месяцы") {
            override fun actionButton(g: Graphics?) {
                super.actionButton(g)
                openMonthsPanel()
            }
        }
        buttonsPanel.add(buttonMonths)
        buttonMonths.setText()
        //---------------------------Years---------------------------//
        buttonYears = NavigationButton(90, 40, "Годы")
        buttonsPanel.add(buttonYears)
        buttonYears.setText()
        //-----------------------Previous month----------------------//
        buttonPreviousMonth = object : FunctionButton("<") {
            override fun actionButton(g: Graphics?) {
                super.actionButton(g)
                when(enumCalendarPanels) {
                    CalendarPanels.DAYS_PANEL -> {
                        requireMonth = (12 + requireMonth - 1) % 12
                        requireYear = if (requireMonth == 11) requireYear - 1 else requireYear
                        changeCalendarDays(requireMonth, requireYear)
                    }
                    CalendarPanels.MONTHS_PANEL -> {
                        requireYear--
                        changeCalendarDays(requireMonth, requireYear)
                        changeCalendarMonth(requireYear)
                    }
                    CalendarPanels.YEARS_PANEL -> TODO()
                }
            }
        }
        buttonsPanel.add(buttonPreviousMonth)
        //----------------------Following month----------------------//
        buttonFollowingMonth = object : FunctionButton(">") {
            override fun actionButton(g: Graphics?) {
                super.actionButton(g)
                when(enumCalendarPanels) {
                    CalendarPanels.DAYS_PANEL -> {
                        requireMonth = (requireMonth + 1) % 12
                        requireYear = if (requireMonth == 0) requireYear + 1 else requireYear
                        changeCalendarDays(requireMonth, requireYear)
                    }
                    CalendarPanels.MONTHS_PANEL -> {
                        requireYear++
                        changeCalendarDays(requireMonth, requireYear)
                        changeCalendarMonth(requireYear)
                    }
                    CalendarPanels.YEARS_PANEL -> TODO()
                }
            }
        }
        buttonsPanel.add(buttonFollowingMonth)
        add(buttonsPanel)
    }
    private fun setTotalCalendarPanelDays() {
        totalCalendarPanelDays = JPanel()
        totalCalendarPanelDays.layout = BoxLayout(totalCalendarPanelDays, BoxLayout.Y_AXIS)
        totalCalendarPanelDays.size = Dimension(windowWidth, 350)
        totalCalendarPanelDays.background = standardColor
        setDaysOfMonth()
        setCalendar()
        setTotalCalendarPanel()
        totalCalendarPanel.add(totalCalendarPanelDays) //TODO
    }
    private fun setTotalCalendarPanel() {
        totalCalendarPanel = JPanel()
        totalCalendarPanel.layout = BoxLayout(totalCalendarPanel, BoxLayout.Y_AXIS)
        totalCalendarPanel.size = Dimension(windowWidth, 350)
        totalCalendarPanel.minimumSize = Dimension(windowWidth, 350)
        totalCalendarPanel.preferredSize = Dimension(windowWidth, 350)
        totalCalendarPanel.maximumSize = Dimension(windowWidth, 350)
        totalCalendarPanel.background = standardColor
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
                override fun getMinimumSize(): Dimension = Dimension(dayWidth, dayHeight)
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
        totalCalendarPanelDays.add(daysOfWeekPanel)
    }
    private fun setCalendar() {
        calendarPanel = object : JPanel() {
            init {
                layout = GridLayout(6, 7)
                background = standardColor
            }
            override fun getPreferredSize(): Dimension = Dimension(windowWidth, dayHeight * 6)
            override fun getMinimumSize(): Dimension = Dimension(windowWidth, dayHeight * 6)
        }
        val dateQualifier = DateQualifier()
        dateQualifier.setInfoAboutMonth()
        daysArray = Array(42) { i -> object : CalendarButton(dateQualifier.getInfoAboutMonth()[i][keyCurrentDay] == 1,
            dayWidth, dayHeight) {
            override fun actionButton(g: Graphics?) {
                super.actionButton(g)
                selectedDay = getJLabelInCalendarButton().text.toInt()
                if(i < 7 && selectedDay > 20) {
                    selectedMonth = (12 + requireMonth - 1) % 12
                    selectedYear = if(selectedMonth == 11) requireYear - 1 else requireYear
                }
                else if(i > 27 && selectedDay < 15) {
                    selectedMonth = (requireMonth + 1) % 12
                    selectedYear = if(selectedMonth == 0) requireYear + 1 else requireYear
                }
                else {
                    selectedMonth = requireMonth
                    selectedYear = requireYear
                }
                for(j in daysArray.indices)
                    if(j != i) {
                        daysArray[j].pressedCalendarButton = false
                        daysArray[j].repaint()
                    }
                todoList.fillScrollPane(database.find("$selectedYear-$selectedMonth-$selectedDay"),
                    "$selectedYear-$selectedMonth-$selectedDay")
            }
        } }
        for(i in daysArray.indices) {
            calendarPanel.add(daysArray[i])
            daysArray[i].setJLabelInCalendarButton(
                dateQualifier.getInfoAboutMonth()[i][keyDay].toString(),
                dateQualifier.getInfoAboutMonth()[i][keyCurrentMonth] == 1
            )
        }
        totalCalendarPanelDays.add(calendarPanel)
    }
    private fun setTotalCalendarPanelMonths() {
        totalCalendarPanelMonths = JPanel()
        totalCalendarPanelMonths.layout = GridLayout(4, 3)
        totalCalendarPanelMonths.background = standardColor
        totalCalendarPanelMonths.minimumSize = Dimension(windowWidth, 348)
        totalCalendarPanelMonths.preferredSize = Dimension(windowWidth, 348)
        totalCalendarPanelMonths.size = Dimension(windowWidth, 348)
        monthsArray = Array(12) { i -> object : CalendarButton(
            (requireMonth == i && requireYear == selectedYear),
            monthWidth, monthHeight
        ) {
            override fun actionButton(g: Graphics?) {
                super.actionButton(g)
                requireMonth = monthNames.indexOfFirst { i -> i == getJLabelInCalendarButton().text }
                for(j in monthsArray.indices) {
                    monthsArray[j].pressedCalendarButton = false
                    monthsArray[j].repaint()
                }
                openDaysPanel()
            }
        }}
        for(i in monthsArray.indices) {
            totalCalendarPanelMonths.add(monthsArray[i])
            monthsArray[i].setJLabelInCalendarButton(monthNames[i])
        }
//        totalCalendarPanel.add(totalCalendarPanelMonths) //TODO
    }
    private fun setTodoList() {
        val taskList: MutableSet<Map<String, String>>
        try {
            taskList = database.find("$selectedYear-$selectedMonth-$selectedDay")
        } catch(e: Exception) {
            throw Exception("Failed to open mongodb: $e")
        }
        todoList = TodoList("$selectedYear-$selectedMonth-$selectedDay", taskList)
        add(todoList)
    }
    //-------------------------Changing calendar panels-------------------------//
    private fun changeCalendarDays(month: Int, year: Int) {
        val dateQualifier = DateQualifier(month, year)
        dateQualifier.setInfoAboutMonth()
        for(i in daysArray.indices) {
            daysArray[i].setJLabelInCalendarButton(
                dateQualifier.getInfoAboutMonth()[i][keyDay].toString(),
                dateQualifier.getInfoAboutMonth()[i][keyCurrentMonth] == 1
            )
            daysArray[i].pressedCalendarButton = (selectedDay == dateQualifier.getCalendarArray()[i][Calendar.DAY_OF_MONTH]
                    && selectedMonth == dateQualifier.getCalendarArray()[i][Calendar.MONTH]
                    && selectedYear == dateQualifier.getCalendarArray()[i][Calendar.YEAR])
            daysArray[i].setIsCurrent(dateQualifier.getInfoAboutMonth()[i][keyCurrentDay] == 1)
        }
        monthPanel.setMonth("${monthNames[requireMonth]} $requireYear")
    }
    private fun changeCalendarMonth(year: Int) {
        for(i in monthsArray.indices) {
            monthsArray[i].setIsCurrent((Calendar.getInstance().get(Calendar.MONTH) == i
                    && year == Calendar.getInstance().get(1)))
            monthsArray[i].pressedCalendarButton = (selectedMonth == i && year == selectedYear)
            monthsArray[i].repaint()
        }
    }
    //----------------------------------------------------------------------------
    private fun createDatabase() {
        database = Database()
    }
    //----------------------------------------------------------------------------
    private fun openDaysPanel() {
        buttonYears.isActiveButton = false
        buttonMonths.isActiveButton = false
        buttonDays.isActiveButton = true
        removeFromTotalCalendarPanel()
        totalCalendarPanel.repaint()
        totalCalendarPanel.add(totalCalendarPanelDays)
        enumCalendarPanels = CalendarPanels.DAYS_PANEL
        changeCalendarDays(requireMonth, requireYear)
    }
    private fun openMonthsPanel() {
        buttonDays.isActiveButton = false
        buttonYears.isActiveButton = false
        buttonMonths.isActiveButton = true
        removeFromTotalCalendarPanel()
        totalCalendarPanel.repaint()
        totalCalendarPanel.add(totalCalendarPanelMonths)
        enumCalendarPanels = CalendarPanels.MONTHS_PANEL
        changeCalendarDays(requireMonth, requireYear)
        changeCalendarMonth(requireYear)
    }
    private fun openYearsPanel() {

    }
    private fun removeFromTotalCalendarPanel() {
        buttonYears.repaint()
        buttonMonths.repaint()
        buttonDays.repaint()
        when(enumCalendarPanels) {
            CalendarPanels.DAYS_PANEL -> totalCalendarPanel.remove(totalCalendarPanelDays)
            CalendarPanels.MONTHS_PANEL -> totalCalendarPanel.remove(totalCalendarPanelMonths)
            CalendarPanels.YEARS_PANEL -> totalCalendarPanel.remove(totalCalendarPanelYears)
        }
    }
}