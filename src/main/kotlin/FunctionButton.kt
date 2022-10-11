import MainWindow.Companion.whiteColor
import javax.swing.JLabel
import java.awt.*

class FunctionButton (
    private val buttonWidth: Int,
    private val buttonHeight: Int,
    private val text: String
) : Button() {
    private var graph: Graphics? = graphics
    private val buttonText = JLabel()
    override fun enteredMouse(g: Graphics?) {
        println("ентеред")
    }
    override fun exitedMouse(g: Graphics?) {
        println("эксайтед")
        background = MainWindow.panelColor
        g?.color = whiteColor
        g?.drawRect(0, 0, buttonWidth - 1, buttonHeight - 1)
    }
    override fun pressedMouse(g: Graphics?) {
        println("прессед")
    }
    override fun releasedMouse(g: Graphics?) {
        println("релизд")
    }
    init {
        add(buttonText)
    }
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        isOpaque = true
        graph = g
        if(text == "<" || text == ">")
            setText()
    }
    override fun getPreferredSize(): Dimension = Dimension(buttonWidth, buttonHeight)
    fun setText() {
        graph?.color = whiteColor
        when(text) {
            "<" -> {
                graph?.drawLine(26, 7, 13, 20)
                graph?.drawLine(13, 20, 26, 33)
            }
            ">" -> {
                graph?.drawLine(13, 7, 26, 20)
                graph?.drawLine(26, 20, 13, 33)
            }
            else -> {
                buttonText.text = text
                buttonText.foreground = whiteColor
                buttonText.font = Font("Noto Sans", Font.PLAIN, 14)
                layout = GridBagLayout()
                buttonText.alignmentX = CENTER_ALIGNMENT
                buttonText.alignmentY = BOTTOM_ALIGNMENT
                buttonText.isVisible = true
            }
        }
    }
    private fun mouseExitedButton(g: Graphics?) {
        background = MainWindow.panelColor
        g?.color = whiteColor
        g?.drawRect(0, 0, buttonWidth - 1, buttonHeight - 1)
    }
}