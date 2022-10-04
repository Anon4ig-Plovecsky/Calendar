import MainWindow.Companion.standardColor
import MainWindow.Companion.whiteColor
import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.*

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
        isOpaque = true
        setText()
    }
    override fun getPreferredSize(): Dimension {
        return Dimension(buttonWidth, buttonHeight)
    }
    private fun setText() {
        val buttonText = JLabel(text)
        buttonText.foreground = whiteColor
        buttonText.font = Font("Noto Sans", Font.PLAIN, 14)
        layout = GridBagLayout()
        buttonText.alignmentX = CENTER_ALIGNMENT
        buttonText.alignmentY = BOTTOM_ALIGNMENT
        add(buttonText)
    }
}