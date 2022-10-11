import MainWindow.Companion.whiteColor
import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.*

class Button (
    private val buttonWidth: Int,
    private val buttonHeight: Int,
    private val text: String
) : JPanel() {
    private var graphicsPanel: Graphics?
    init {
        graphicsPanel = graphics
    }
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        graphicsPanel = g
        background = MainWindow.panelColor
        graphicsPanel?.color = whiteColor
        graphicsPanel?.drawRect(0, 0, buttonWidth - 1, buttonHeight - 1)
        isOpaque = true
        if(text == "<" || text == ">")
            setText()
    }
    override fun getPreferredSize(): Dimension = Dimension(buttonWidth, buttonHeight)
    fun setText() {
        graphicsPanel?.color = whiteColor
        when(text) {
            "<" -> {
                graphicsPanel?.drawLine(26, 7, 13, 20)
                graphicsPanel?.drawLine(13, 20, 26, 33)
            }
            ">" -> {
                graphicsPanel?.drawLine(13, 7, 26, 20)
                graphicsPanel?.drawLine(26, 20, 13, 33)
            }
            else -> {
                val buttonText = JLabel(text)
                buttonText.foreground = whiteColor
                buttonText.font = Font("Noto Sans", Font.PLAIN, 14)
                layout = GridBagLayout()
                buttonText.alignmentX = CENTER_ALIGNMENT
                buttonText.alignmentY = BOTTOM_ALIGNMENT
                add(buttonText)
                buttonText.isVisible = true
            }
        }
    }
}