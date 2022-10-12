import java.awt.Graphics

interface ButtonsMouseAdapter {
    fun enteredMouse(g: Graphics?)
    fun exitedMouse(g: Graphics?)
    fun pressedMouse(g: Graphics?)
    fun releasedMouse(g: Graphics?)
    fun actionButton(g: Graphics?)
}