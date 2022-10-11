import java.util.Calendar

class DateQualifier constructor () {
    private val infoDays: Array<MutableMap<String, Int>> = Array(42) { mutableMapOf() }
    private val todayDay: Calendar = Calendar.getInstance()
    //-------------------Current day---------------------//
    private var currentDay: Int
    private var currentMonth: Int
    private var currentYear: Int
    //------------------Selected day---------------------//
    private var day: Int
    private var month: Int
    private var year: Int
    private var dayOfWeek = 1
    //-----------------------------------------------------
    private var index = 0
    //---------------Second constructor----------------//
    constructor(month: String, year: String) : this() {
        day = 1
        this.month = month.toInt() - 1
        this.year = year.toInt()
        setDate(1, this.month, this.year)
    }
    init {
        currentDay = todayDay.get(Calendar.DAY_OF_MONTH)
        currentMonth = todayDay.get(Calendar.MONTH)
        currentYear = todayDay.get(Calendar.YEAR)
        day = currentDay
        month = currentMonth
        year = currentYear
        setDate(1, month, year)
    }
    private fun setDate(day: Int, month: Int, year: Int) {
        todayDay.set(Calendar.YEAR, year)
        todayDay.set(Calendar.MONTH, month)
        todayDay.set(Calendar.DAY_OF_MONTH, day)
    }
    fun setInfoAboutMonth(): Array<MutableMap<String, Int>> {
        val maxDayCurrentMonth = todayDay.getActualMaximum(Calendar.DAY_OF_MONTH)
        dayOfWeek = todayDay.get(Calendar.DAY_OF_WEEK_IN_MONTH)
        //---------------Days before current month--------------//
        if(dayOfWeek > 0) {
            val previousMonth = (12 + month - 1) % 12
            val previousYear = if (previousMonth == 11) year - 1 else year
            setDate(1, previousMonth, previousYear)
            val previousDay = todayDay.getActualMaximum(Calendar.DAY_OF_MONTH) - dayOfWeek - 3
            for(day in previousDay .. todayDay.getActualMaximum(Calendar.DAY_OF_MONTH))
                feelDayOfWeek(day, previousMonth, previousYear, false)
            setDate(1, month, year)
        }
        //---------------------Current days---------------------//
        for(day in 1 .. maxDayCurrentMonth)
            feelDayOfWeek(day, month, year, true)
        dayOfWeek = todayDay.get(Calendar.DAY_OF_WEEK_IN_MONTH)
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
        setDate(day, month, year)
        if(isCurrentMonth)
            infoDays[index][MainWindow.keyCurrentMonth] = 1 // true
        else infoDays[index][MainWindow.keyCurrentMonth] = 0 // false
        infoDays[index][MainWindow.keyDay] = day
        if(day == currentDay && month == currentMonth && year == currentYear)
            infoDays[index++][MainWindow.keyCurrentDay] = 1 //true
        else infoDays[index++][MainWindow.keyCurrentDay] = 0 //false
    }
}