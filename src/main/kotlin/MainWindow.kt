import javax.swing.*
import java.util.*
import java.awt.*
import kotlin.collections.HashSet

class MainWindow : JFrame() {
    companion object {
        //------------------------Keys--------------------------//
        @JvmStatic val keyDay = "NUMBER_OF_DAY"
        @JvmStatic val keyCurrentMonth = "CURRENT_MONTH"
        @JvmStatic val keyCurrentDay = "CURRENT_DAY"
        @JvmStatic val keyTaskName = "TASK_NAME"
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
        const val buttonWidth = 40
        const val buttonHeight = 40
        const val windowWidth = 420
        const val windowHeight = 710
        const val todoListWidth = 420
        const val todoListHeight = 250
    }
    private lateinit var days: Array<Day>
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
        background = standardColor
        setMonthPanel()
        setArrowButtonsPanel()
        setTotalCalendarPanel()
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
        buttonDays = NavigationButton(90, 40, "Дни", true)
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
        buttonPreviousMonth = object : FunctionButton("<") {
            override fun actionButton(g: Graphics?) {
                super.actionButton(g)
                requireMonth = (12 + requireMonth - 1) % 12
                requireYear = if (requireMonth == 11) requireYear - 1 else requireYear
                changeCalendarDays(requireMonth, requireYear)
            }
        }
        buttonsPanel.add(buttonPreviousMonth)
        //----------------------Following month----------------------//
        buttonFollowingMonth = object : FunctionButton(">") {
            override fun actionButton(g: Graphics?) {
                super.actionButton(g)
                requireMonth = (requireMonth + 1) % 12
                requireYear = if(requireMonth == 0) requireYear + 1 else requireYear
                changeCalendarDays(requireMonth, requireYear)
            }
        }
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
        totalCalendarPanel.add(daysOfWeekPanel)
    }
    private fun setCalendar() {
        calendarPanel = object : JPanel() {
            init {
                layout = GridLayout(6, 7)
            }
            override fun getPreferredSize(): Dimension = Dimension(windowWidth, dayHeight * 6)
            override fun getMinimumSize(): Dimension = Dimension(windowWidth, dayHeight * 6)
        }
        val dateQualifier = DateQualifier()
        dateQualifier.setInfoAboutMonth()
        days = Array(42) { i -> object : Day(dateQualifier.getInfoAboutMonth()[i][keyCurrentDay] == 1) {
            override fun actionButton(g: Graphics?) {
                super.actionButton(g)
                selectedDay = getNumberOfDay().text.toInt()
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
                for(j in days.indices)
                    if(j != i) {
                        days[j].pressedDay = false
                        days[j].repaint()
                    }
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
    private fun setTodoList() {
        todoList = TodoList(HashSet<Map<String, String>>()) //TODO
        add(todoList)
    }
    //----------------------------------------------------------------------------
    private fun changeCalendarDays(month: Int, year: Int) {
        val dateQualifier = DateQualifier(month, year)
        dateQualifier.setInfoAboutMonth()
        for(i in days.indices) {
            days[i].setNumberOfDay(
                dateQualifier.getInfoAboutMonth()[i][keyDay].toString(),
                dateQualifier.getInfoAboutMonth()[i][keyCurrentMonth] == 1
            )
            days[i].pressedDay = (selectedDay == dateQualifier.getCalendarArray()[i][Calendar.DAY_OF_MONTH]
                    && selectedMonth == dateQualifier.getCalendarArray()[i][Calendar.MONTH]
                    && selectedYear == dateQualifier.getCalendarArray()[i][Calendar.YEAR])
            days[i].setCurrentDay(dateQualifier.getInfoAboutMonth()[i][keyCurrentDay] == 1)
        }
        monthPanel.setMonth("${monthNames[requireMonth]} $requireYear")
    }
}