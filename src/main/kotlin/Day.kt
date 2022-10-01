import javax.swing.JPanel
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Color

class Day (

        ) : JPanel() {
    private val dayWidth = 60
    private val dayHeight = 50
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        g?.color = Color(13, 13, 13)
        g?.drawRect(0, 0, dayWidth, dayHeight)
        repaint()
    }

    override fun getPreferredSize(): Dimension {
        return Dimension(dayWidth, dayHeight)
    }
}