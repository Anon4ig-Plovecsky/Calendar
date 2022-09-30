import java.awt.EventQueue

fun main() {
    EventQueue.invokeLater {
        val application = MainWindow()
        application.startApplication()
    }
}