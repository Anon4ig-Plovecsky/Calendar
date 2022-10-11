import MainWindow.Companion.windowWidth
import MainWindow.Companion.panelColor
import MainWindow.Companion.whiteColor
import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.Dimension
import java.awt.Font

class MonthPanel : JPanel() {
    private val month = JLabel()
    init {
        background = panelColor
        add(month)
    }
    override fun getPreferredSize(): Dimension = Dimension(windowWidth, 30)
    fun setMonth(nameMonth: String) {
        month.text = nameMonth
        month.font = Font("Noto Sans", Font.PLAIN, 16)
        month.foreground = whiteColor
    }
}