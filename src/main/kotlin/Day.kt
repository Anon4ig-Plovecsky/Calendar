import MainWindow.Companion.standardColor
import javax.swing.JPanel
import java.awt.Dimension
import java.awt.Graphics

class Day (

        ) : JPanel() {
    private val dayWidth = 60
    private val dayHeight = 50
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        background = standardColor
        g?.color = standardColor
        g?.drawRect(0, 0, dayWidth, dayHeight)
        repaint()
    }

    override fun getPreferredSize(): Dimension {
        return Dimension(dayWidth, dayHeight)
    }
}