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
        background = MainWindow.panelColor
        g?.color = whiteColor
        g?.drawRect(0, 0, buttonWidth - 1, buttonHeight - 1)
        isOpaque = true
        if(text != "<" && text != ">")
            setText()
        else {
            g?.color = whiteColor
            if(text == "<") {
                g?.drawLine(27, 7, 13, 20)
                g?.drawLine(13, 20, 27, 33)
            }
            else {
                g?.drawLine(13, 7, 27, 20)
                g?.drawLine(27, 20, 13, 33)
            }
        }
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