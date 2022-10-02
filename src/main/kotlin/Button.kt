import MainWindow.Companion.standardColor
import MainWindow.Companion.whiteColor
import java.awt.Color
import java.awt.Dimension
import javax.swing.JPanel
import java.awt.Graphics
import javax.swing.JLabel

class Button (
    private val buttonWidth: Int,
    private val buttonHeight: Int,
    private val text: String
) : JPanel() {
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        background = standardColor
        g?.color = whiteColor
        g?.drawRect(0, 0, buttonWidth - 1, buttonHeight - 1)
        setText()
    }
    override fun getPreferredSize(): Dimension {
        return Dimension(buttonWidth, buttonHeight)
    }
    private fun setText() {
        val buttonText = JLabel("\n$text")
        buttonText.foreground = whiteColor
        buttonText.alignmentX = CENTER_ALIGNMENT
        buttonText.alignmentY = CENTER_ALIGNMENT
        add(buttonText)
    }
}