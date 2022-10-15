import MainWindow.Companion.buttonHeight
import MainWindow.Companion.buttonWidth
import java.awt.Dimension
import java.awt.Graphics

open class FunctionButton(
    private val text: String
) : Button() {
    private var isAction = false
    //----------------------Implemented methods---------------------//
    override fun enteredMouse(g: Graphics?) {
        g?.color = MainWindow.pressedMouseOutlineColor
    }
    override fun exitedMouse(g: Graphics?) {
        super.exitedMouse(g)
        g?.color = MainWindow.whiteColor
    }
    override fun releasedMouse(g: Graphics?) {
        super.releasedMouse(g)
        if(!isAction)
            exitedMouse(g)
        isAction = false
    }
    override fun pressedMouse(g: Graphics?) {
        super.pressedMouse(g)
        g?.color = MainWindow.enteredMouseOutlineColor
    }
    override fun actionButton(g: Graphics?) {
        isAction = true
        enteredMouse(g)
    }
    //-----------------------Override methods-----------------------//
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        background = MainWindow.panelColor
        g?.drawRect(0, 0, buttonWidth - 1, buttonHeight - 1)
        setSymbol(g)
    }
    override fun getPreferredSize(): Dimension = Dimension(buttonWidth, buttonHeight)
    override fun getMinimumSize(): Dimension = Dimension(buttonWidth, buttonHeight)
    //----------------------------------------------------------------
    private fun setSymbol(g: Graphics?) {
        g?.color = MainWindow.whiteColor
        when(text) {
            "<" -> {
                g?.drawLine(26, 7, 13, 20)
                g?.drawLine(13, 20, 26, 33)
            }
            ">" -> {
                g?.drawLine(13, 7, 26, 20)
                g?.drawLine(26, 20, 13, 33)
            }
            "-" -> {
                g?.drawLine(13, 20, 26, 20)
            }
            "done" -> {
                g?.drawLine(10, 20, 17, 27)
                g?.drawLine(17, 27, 30, 14)
            }
            "+" -> {
                g?.drawRect(12, 20, 15, 1)
                g?.drawRect(19, 13, 1, 15)
            }
        }
    }
}