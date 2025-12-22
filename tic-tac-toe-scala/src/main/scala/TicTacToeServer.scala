import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.{ServletHandler, ServletHolder}
import org.scalatra.ScalatraServlet

object TicTacToeServer {
  def main(args: Array[String]): Unit = {
    val server = new Server(8080)
    val handler = new ServletHandler()
    server.setHandler(handler)

    handler.addServletWithMapping(classOf[TicTacToeServlet], "/*")

    server.start()
    println("Tic Tac Toe server started on http://localhost:8080/")
    server.join()
  }
}