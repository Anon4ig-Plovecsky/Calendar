import javax.swing.plaf.basic.BasicScrollBarUI
import java.awt.*
import javax.swing.*
import javax.swing.border.LineBorder

class TodoList : JPanel() {
    private lateinit var jScrollBar: JScrollBar
    private lateinit var jScrollPane: JScrollPane
    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        background = MainWindow.panelColor
    }
    //-------------------------Override methods-------------------------//
    override fun getPreferredSize(): Dimension = Dimension(MainWindow.todoListWidth, MainWindow.todoListHeight)
    override fun getMinimumSize(): Dimension = Dimension(MainWindow.todoListWidth, MainWindow.todoListHeight)
    //--------------------------------------------------------------------
    private fun setJScrollPane(jPanel: JPanel) {
        //----------------------------Create scroll bar for JScrollPane----------------------------//
        jScrollBar = object : JScrollBar() {
            init {
                setUI(object : BasicScrollBarUI() {
                    val thumbSize = 80
                    override fun createDecreaseButton(orientation: Int): JButton = ScrollBarButton()
                    override fun createIncreaseButton(orientation: Int): JButton = ScrollBarButton()
                    override fun paintTrack(g: Graphics?, c: JComponent?, trackBounds: Rectangle?) {
                        val rectangle = trackBounds!!
                        val graphics2D: Graphics2D = g!! as Graphics2D
                        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
                        val size: Int
                        val x: Int
                        val y: Int
                        val width: Int
                        val height: Int
                        if(scrollbar.orientation == VERTICAL) {
                            size = rectangle.width.div(2)
                            x = rectangle.x + ((rectangle.width - size)  / 2)
                            y = rectangle.y
                            width = size
                            height = rectangle.height
                        } else {
                            size = rectangle.height.div(2)
                            x = 0
                            y = rectangle.y + ((rectangle.height - size)  / 2)
                            width = rectangle.width
                            height = size
                        }
                        graphics2D.color = MainWindow.panelColor
                        graphics2D.fillRect(x, y, width, height)
                    }
                    override fun paintThumb(g: Graphics?, c: JComponent?, thumbBounds: Rectangle?) {
                        val rectangle = thumbBounds!!
                        val graphics2D: Graphics2D = g!! as Graphics2D
                        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
                        var x = rectangle.x
                        var y = rectangle.y
                        var width = rectangle.width
                        var height = rectangle.height
                        if(scrollbar.orientation == VERTICAL) {
                            y += 8
                            height -= 16
                        } else {
                            x += 8
                            width -= 16
                        }
                        graphics2D.color = scrollbar.foreground
                        graphics2D.fillRoundRect(x, y, width, height, 10, 10)
                    }
                    override fun getMinimumThumbSize(): Dimension = if(scrollbar.orientation == JScrollBar.VERTICAL)
                        Dimension(0, thumbSize)
                    else
                        Dimension(thumbSize, 0)
                    override fun getMaximumThumbSize(): Dimension {
                        return if(scrollbar.orientation == JScrollBar.VERTICAL)
                            Dimension(0, thumbSize)
                        else Dimension(thumbSize, 0)
                    }
                    private inner class ScrollBarButton : JButton() {
                        init {
                            border = BorderFactory.createEmptyBorder()
                        }

                        override fun paint(g: Graphics?) {  }
                    }
                })
                preferredSize = Dimension(8, 8)
                foreground = MainWindow.pressedMouseAreaColor
                background = MainWindow.panelColor
            }
        }
        //--------------------------------JScrollPane configuration--------------------------------//
        jScrollPane = JScrollPane(jPanel)
        jScrollPane.verticalScrollBar = jScrollBar
        jScrollPane.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        jScrollPane.horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        jScrollPane.minimumSize = Dimension(MainWindow.todoListWidth, MainWindow.todoListHeight)
        jScrollPane.preferredSize = Dimension(MainWindow.todoListWidth, MainWindow.todoListHeight)
        jScrollPane.background = MainWindow.panelColor
        jScrollPane.foreground = MainWindow.panelColor
        jScrollPane.border = object : LineBorder(MainWindow.panelColor) {
            override fun paintBorder(c: Component?, g: Graphics?, x: Int, y: Int, width: Int, height: Int) {
                super.lineColor = MainWindow.panelColor
                super.paintBorder(c, g, x, y, width, height)
            }
        }
        add(jScrollPane)
    }
}