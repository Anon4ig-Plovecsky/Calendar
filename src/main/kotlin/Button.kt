import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JPanel
import java.awt.Graphics
import EnumMouseEvent.*

abstract class Button : JPanel(), ButtonsMouseAdapter {
    private var mouseEvent: EnumMouseEvent = EXITED
    private var pressedButton = false
    init {
        callAddMouseListener()
    }
    private fun callAddMouseListener() {
        addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent?) {
                super.mousePressed(e)
                mouseEvent = PRESSED
                repaint()
            }
            override fun mouseReleased(e: MouseEvent?) {
                super.mouseReleased(e)
                mouseEvent = RELEASED
                repaint()
            }
            override fun mouseEntered(e: MouseEvent?) {
                super.mouseEntered(e)
                mouseEvent = ENTERED
                repaint()
            }
            override fun mouseExited(e: MouseEvent?) {
                super.mouseExited(e)
                mouseEvent = EXITED
                repaint()
            }
        })
    }
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        when(mouseEvent) {
            EXITED -> exitedMouse(g)
            ENTERED -> enteredMouse(g)
            PRESSED -> pressedMouse(g)
            RELEASED -> releasedMouse(g)
        }
    }
    override fun exitedMouse(g: Graphics?) {
        pressedButton = false
    }
    override fun releasedMouse(g: Graphics?) {
        if(pressedButton)
            actionButton(g)
        pressedButton = false
    }

    override fun pressedMouse(g: Graphics?) {
        pressedButton = true
    }
}