import javax.swing.plaf.basic.BasicScrollBarUI
import javax.swing.border.LineBorder
import javax.swing.*
import java.awt.*
import java.time.LocalDateTime

class TodoList(
    private var taskList: MutableSet<Map<String, String>>
) : JPanel() {
    private lateinit var addButton: FunctionButton
    private lateinit var jScrollPane: JScrollPane
    private lateinit var jScrollBar: JScrollBar
    private lateinit var finalJPanel: JPanel
    private var jScrollPanel: JPanel? = null
    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        background = MainWindow.panelColor
        setScrollBar()
        setAddButton()
        setJScrollPane()
        fillScrollPane(HashSet(taskList))
    }
    //-------------------------Override methods-------------------------//
    override fun getPreferredSize(): Dimension = Dimension(MainWindow.todoListWidth, MainWindow.todoListHeight)
    override fun getMinimumSize(): Dimension = Dimension(MainWindow.todoListWidth, MainWindow.todoListHeight)
    //--------------------------------------------------------------------
    private fun setJScrollPane() {
        //--------------------------------JScrollPane configuration--------------------------------//
        jScrollPane = JScrollPane()
        jScrollPane.verticalScrollBar = jScrollBar
        jScrollPane.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        jScrollPane.horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        jScrollPane.minimumSize = Dimension(MainWindow.todoListWidth, MainWindow.todoListHeight)
        jScrollPane.preferredSize = Dimension(MainWindow.todoListWidth, MainWindow.todoListHeight)
        jScrollPane.background = MainWindow.panelColor
        jScrollPane.foreground = MainWindow.panelColor
        jScrollPane.verticalScrollBar.unitIncrement = 10
        jScrollPane.border = object : LineBorder(MainWindow.panelColor) {
            override fun paintBorder(c: Component?, g: Graphics?, x: Int, y: Int, width: Int, height: Int) {
                super.lineColor = MainWindow.panelColor
                super.paintBorder(c, g, x, y, width, height)
            }
        }
        add(jScrollPane)
    }
    private fun setScrollBar() {
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
                            y += 4
                            height -= 8
                        } else {
                            x += 4
                            width -= 8
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
    }
    //------------------------Filling JScrollPane-----------------------//
    private fun fillScrollPane(taskList: MutableSet<Map<String, String>>) {
        val jScrollPanelHeight = MainWindow.taskHeight * (taskList.size + 1) + taskList.size
        if(jScrollPanel != null)
            jScrollPane.viewport.remove(jScrollPanel)
        jScrollPanel = object : JPanel() {
            init {
                layout = BoxLayout(this, BoxLayout.Y_AXIS)
                background = MainWindow.panelColor
            }
            override fun getPreferredSize(): Dimension = Dimension(MainWindow.taskWidth, jScrollPanelHeight)
            override fun getMinimumSize(): Dimension = Dimension(MainWindow.taskWidth, jScrollPanelHeight)
        }
        var taskArray: Array<Task> = emptyArray()
        for(task in taskList) {
            taskArray += Task(
                task[MainWindow.keyTaskName]!!,
                task[MainWindow.keyIsDone]!! == "true"
            )
        }
        for(task in taskArray)
            jScrollPanel?.add(task)
        jScrollPanel?.add(finalJPanel)
        jScrollPane.size = if(jScrollPanelHeight + 5 > MainWindow.todoListHeight)
            Dimension(MainWindow.todoListWidth, MainWindow.todoListHeight)
        else Dimension(MainWindow.todoListWidth, jScrollPanelHeight + 5)
        jScrollPane.viewport.add(jScrollPanel)
    }
    private fun setAddButton() {
        finalJPanel = object : JPanel() {
            init {
                background = MainWindow.panelColor
            }
            override fun getPreferredSize(): Dimension = Dimension(MainWindow.taskWidth, MainWindow.taskHeight)
            override fun getMinimumSize(): Dimension = Dimension(MainWindow.taskWidth, MainWindow.taskHeight)
        }
        addButton = object : FunctionButton("+") {
            override fun actionButton(g: Graphics?) {
                super.actionButton(g)
                val newTask = HashMap<String, String>()
                newTask[MainWindow.keyTaskName] = LocalDateTime.now().toString()
                newTask[MainWindow.keyIsDone] = "false"
                taskList.add(newTask)
                fillScrollPane(HashSet(taskList))
            }
        }
        finalJPanel.add(addButton)
    }
    //--------------------------------------------------------------------
}