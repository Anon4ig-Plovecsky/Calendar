import MainWindow.Companion.whiteColor
import javax.swing.JLabel
import java.awt.*

open class NavigationButton (
    private val buttonWidth: Int,
    private val buttonHeight: Int,
    private val text: String,
    var isActiveButton: Boolean = false
) : Button() {
    private var graph: Graphics? = graphics
    private val buttonText = JLabel()
    //--------------------Implemented Methods--------------------//
    override fun enteredMouse(g: Graphics?) {
        if(!isActiveButton)
            g?.color = MainWindow.pressedMouseOutlineColor
        else exitedMouse(g)
    }
    override fun exitedMouse(g: Graphics?) {
        super.exitedMouse(g)
        if(isActiveButton)
            g?.color = MainWindow.currentDayOutlineColor
        else
            g?.color = whiteColor
    }
    override fun pressedMouse(g: Graphics?) {
        super.pressedMouse(g)
        g?.color = MainWindow.enteredMouseOutlineColor
    }
    override fun releasedMouse(g: Graphics?) {
        super.releasedMouse(g)
        exitedMouse(g)
    }
    override fun actionButton(g: Graphics?) {
        isActiveButton = true
        exitedMouse(g)
    }
    //-------------------------------------------------------------
    init {
        callAddButtonText()
    }
    private fun callAddButtonText() {
        add(buttonText)
    }
    //---------------------Override methods----------------------//
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        isOpaque = true
        graph = g
        background = MainWindow.panelColor
        g?.drawRect(0, 0, buttonWidth - 1, buttonHeight - 1)
    }
    override fun getPreferredSize(): Dimension = Dimension(buttonWidth, buttonHeight)
    fun setText() {
        graph?.color = whiteColor
        buttonText.text = text
        buttonText.foreground = whiteColor
        buttonText.font = Font("Noto Sans", Font.PLAIN, 14)
        layout = GridBagLayout()
        buttonText.alignmentX = CENTER_ALIGNMENT
        buttonText.alignmentY = BOTTOM_ALIGNMENT
        buttonText.isVisible = true
    }
    //-------------------------------------------------------------
}