import MainWindow.Companion.setGridBagConstraint
import MainWindow.Companion.windowWidth
import MainWindow.Companion.panelColor
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JPanel
import java.awt.Dimension
import java.awt.Graphics

class ButtonsPanel : JPanel() {
    private lateinit var buttonDays: Button
    private lateinit var buttonMonths: Button
    private lateinit var buttonYears: Button
    private lateinit var gridBagConstraints: GridBagConstraints
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        background = panelColor
        g?.drawRect(-1, -1, windowWidth + 1, 50)
    }
    override fun getPreferredSize(): Dimension {
        return Dimension(windowWidth, 50)
    }
    fun setButtons() {
        layout = GridBagLayout()
        isOpaque = true
        gridBagConstraints = GridBagConstraints()
        //-------------------------Days-----------------------------//
        buttonDays = Button(90, 40, "Дни")
        gridBagConstraints = setGridBagConstraint(gridBagConstraints, 0, 0, 4, 1)
        //--------------------------Months---------------------------//
        add(buttonDays, gridBagConstraints)
        buttonDays = Button(120, 40, "Месяцы")
        gridBagConstraints = setGridBagConstraint(gridBagConstraints, 5, 0, 5, 1)
        add(buttonDays, gridBagConstraints)
        //---------------------------Years---------------------------//
        buttonDays = Button(90, 40, "Годы")
        gridBagConstraints = setGridBagConstraint(gridBagConstraints, 10, 0, 4, 1)
        add(buttonDays, gridBagConstraints)
    }
}