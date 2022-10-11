import MainWindow.Companion.windowWidth
import MainWindow.Companion.panelColor
import javax.swing.JPanel
import java.awt.Dimension
import java.awt.Graphics

class ButtonsPanel : JPanel() {
    private lateinit var functionButtonDays: FunctionButton
    private lateinit var functionButtonMonths: FunctionButton
    private lateinit var functionButtonYears: FunctionButton
    private lateinit var functionButtonPreviousMonth: FunctionButton
    private lateinit var functionButtonFollowingMonth: FunctionButton
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        background = panelColor
        g?.drawRect(-1, -1, windowWidth + 1, 50)
    }
    override fun getPreferredSize(): Dimension = Dimension(windowWidth, 50)
    fun setButtons() {
        isOpaque = true
        //-------------------------Days-----------------------------//
        functionButtonDays = FunctionButton(90, 40, "Дни")
        add(functionButtonDays)
        functionButtonDays.setText()
        //--------------------------Months---------------------------//
        functionButtonMonths = FunctionButton(120, 40, "Месяцы")
        add(functionButtonMonths)
        functionButtonMonths.setText()
        //---------------------------Years---------------------------//
        functionButtonYears = FunctionButton(90, 40, "Годы")
        add(functionButtonYears)
        functionButtonYears.setText()
        //-----------------------Previous month----------------------//
        functionButtonPreviousMonth = FunctionButton(40, 40, "<")
        add(functionButtonPreviousMonth)
        //-----------------------Following month---------------------//
        functionButtonFollowingMonth = FunctionButton(40, 40, ">")
        add(functionButtonFollowingMonth)
    }
}