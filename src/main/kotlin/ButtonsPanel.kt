import MainWindow.Companion.windowWidth
import MainWindow.Companion.panelColor
import javax.swing.JPanel
import java.awt.Dimension
import java.awt.Graphics

class ButtonsPanel : JPanel() {
    private lateinit var buttonDays: Button
    private lateinit var buttonMonths: Button
    private lateinit var buttonYears: Button
    private lateinit var buttonPreviousMonth: Button
    private lateinit var buttonFollowingMonth: Button
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        background = panelColor
        g?.drawRect(-1, -1, windowWidth + 1, 50)
    }
    override fun getPreferredSize(): Dimension = Dimension(windowWidth, 50)
    fun setButtons() {
        isOpaque = true
        //-------------------------Days-----------------------------//
        buttonDays = Button(90, 40, "Дни")
        add(buttonDays)
        buttonDays.setText()
        //--------------------------Months---------------------------//
        buttonMonths = Button(120, 40, "Месяцы")
        add(buttonMonths)
        buttonMonths.setText()
        //---------------------------Years---------------------------//
        buttonYears = Button(90, 40, "Годы")
        add(buttonYears)
        buttonYears.setText()
        //-----------------------Previous month----------------------//
        buttonPreviousMonth = Button(40, 40, "<")
        add(buttonPreviousMonth)
        //-----------------------Following month---------------------//
        buttonFollowingMonth = Button(40, 40, ">")
        add(buttonFollowingMonth)
    }
}