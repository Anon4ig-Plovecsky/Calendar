import javax.swing.JPanel
import java.awt.Graphics
import java.awt.Color
import java.awt.Dimension

class Calendar (
    private var xCoord: Int,
    private var yCoord: Int

        ) : JPanel() {
    private val daySize = 50
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        g?.color = Color(13, 13, 13)
        g?.drawRect(xCoord, yCoord, daySize, daySize)

    }

    override fun getPreferredSize(): Dimension {
        return Dimension(500, 600)
    }
}