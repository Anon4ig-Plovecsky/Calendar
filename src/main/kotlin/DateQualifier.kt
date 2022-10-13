import java.util.Calendar

class DateQualifier constructor () {
    private val infoDays: Array<MutableMap<String, Int>> = Array(42) { mutableMapOf() }
    private var calendarArray: Array<Calendar> = emptyArray()
    private val todayDay: Calendar = Calendar.getInstance()
    //--------------------Current day--------------------//
    private var currentDay: Int
    private var currentMonth: Int
    private var currentYear: Int
    //--------------------Selected day-------------------//
    private var day: Int
    private var month: Int
    private var year: Int
    private var dayOfWeek = 1
    //-----------------------------------------------------
    private var index = 0
    //-----------------Second constructor----------------//
    constructor(month: Int, year: Int) : this() {
        day = 1
        this.month = month
        this.year = year
        todayDay.set(this.year, this.month, 1)
    }
    init {
        currentDay = todayDay.get(Calendar.DAY_OF_MONTH)
        currentMonth = todayDay.get(Calendar.MONTH)
        currentYear = todayDay.get(Calendar.YEAR)
        day = currentDay
        month = currentMonth
        year = currentYear
        todayDay.set(year, month, 1)
    }
    fun setInfoAboutMonth(): Array<MutableMap<String, Int>> {
        val maxDayCurrentMonth = todayDay.getActualMaximum(Calendar.DAY_OF_MONTH)
        dayOfWeek = (7 + todayDay.get(Calendar.DAY_OF_WEEK) - 2) % 7
        //---------------Days before current month--------------//
        if(dayOfWeek > 0) {
            val previousMonth = (12 + month - 1) % 12
            val previousYear = if (previousMonth == 11) year - 1 else year
            todayDay.set(previousYear, previousMonth, 1)
            val previousDay = todayDay.getActualMaximum(Calendar.DAY_OF_MONTH) - dayOfWeek + 1
            for(day in previousDay .. todayDay.getActualMaximum(Calendar.DAY_OF_MONTH))
                feelDayOfWeek(day, previousMonth, previousYear, false)
            todayDay.set(year, month, 1)
        }
        //---------------------Current days---------------------//
        for(day in 1 .. maxDayCurrentMonth)
            feelDayOfWeek(day, month, year, true)
        dayOfWeek = (7 + todayDay.get(Calendar.DAY_OF_WEEK) - 2) % 7
        //---------------Days after current month---------------//
        if(dayOfWeek < 6 || index < 42) {
            val followingMonth = (month + 1) % 12
            val followingYear = if(month == 0) year + 1 else year
            for(day in 1..28) {
                if(index >= 42)
                    break
                feelDayOfWeek(day, followingMonth, followingYear, false)
            }
        }
        return infoDays
    }
    fun getInfoAboutMonth(): Array<MutableMap<String, Int>> = infoDays
    private fun feelDayOfWeek(day: Int, month: Int, year: Int, isCurrentMonth: Boolean) {
        todayDay.set(year, month, day)
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        calendarArray += calendar
        if(isCurrentMonth)
            infoDays[index][MainWindow.keyCurrentMonth] = 1 // true
        else infoDays[index][MainWindow.keyCurrentMonth] = 0 // false
        infoDays[index][MainWindow.keyDay] = day
        if(day == currentDay && month == currentMonth && year == currentYear)
            infoDays[index++][MainWindow.keyCurrentDay] = 1 //true
        else infoDays[index++][MainWindow.keyCurrentDay] = 0 //false
    }
    fun getCalendarArray(): Array<Calendar> = calendarArray
}