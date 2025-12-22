import org.scalatra._
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization.write
import scala.sys.process._

class TicTacToeServlet extends ScalatraServlet {

  implicit val jsonFormats: DefaultFormats.type = DefaultFormats

  var gameState = Map[String, Any](
    "board" -> Array.fill(3)(Array.fill(3)("")),
    "currentPlayer" -> "X",
    "gameOver" -> false,
    "winner" -> null
  )

  get("/") {
    contentType = "text/html"
    scala.io.Source.fromInputStream(this.getClass().getClassLoader().getResourceAsStream("index.html")).mkString
  }

  get("/css/*") {
    val resourcePath = "css/" + request.getPathInfo.substring(5)
    contentType = "text/css"
    response.getOutputStream.write(scala.io.Source.fromInputStream(
      this.getClass().getClassLoader().getResourceAsStream(resourcePath)
    ).mkString.getBytes)
  }

  get("/js/*") {
    val resourcePath = "js/" + request.getPathInfo.substring(4)
    contentType = "application/javascript"
    response.getOutputStream.write(scala.io.Source.fromInputStream(
      this.getClass().getClassLoader().getResourceAsStream(resourcePath)
    ).mkString.getBytes)
  }

  get("/api/game") {
    contentType = "application/json"
    write(gameState)
  }

  post("/api/move") {
    contentType = "application/json"

    val row = params.getOrElse("row", "0").toInt
    val col = params.getOrElse("col", "0").toInt

    val board = gameState("board").asInstanceOf[Array[Array[String]]]
    val currentPlayer = gameState("currentPlayer").asInstanceOf[String]

    if (!gameState("gameOver").asInstanceOf[Boolean] && board(row)(col) == "") {
      board(row)(col) = currentPlayer

      val won = checkWin(board, currentPlayer)
      val gameOver = won || isBoardFull(board)

      gameState += (
        "currentPlayer" -> (if (currentPlayer == "X") "O" else "X"),
        "winner" -> (if (won) currentPlayer else gameState("winner")),
        "gameOver" -> gameOver
      )

      if (!gameOver && gameState("currentPlayer") == "O" && params.get("isBotMove").isEmpty) {
        makeBotMove()
      }
    }

    write(gameState)
  }

  def makeBotMove(): Unit = {
    val board = gameState("board").asInstanceOf[Array[Array[String]]]

    val prologQuery = createPrologQuery(board)

    val botMove = getBestMoveFromProlog(prologQuery)

    if (botMove != (-1, -1)) {
      val (row, col) = botMove
      board(row)(col) = "O"

      val won = checkWin(board, "O")
      val gameOver = won || isBoardFull(board)

      gameState += (
        "currentPlayer" -> "X",
        "winner" -> (if (won) "O" else gameState("winner")),
        "gameOver" -> gameOver
      )
    }
  }

  def createPrologQuery(board: Array[Array[String]]): String = {
    val boardStr = board.map(row => "[" + row.map(cell =>
      if (cell == "") "e"
      else if (cell == "X") "x"
      else "o"
    ).mkString(",") + "]").mkString(",")

    s"[${boardStr}]"
  }

  def getBestMoveFromProlog(boardStr: String): (Int, Int) = {
    val prologFile = "/tmp/ttt_query.pl"
    val query = s"""
      ${scala.io.Source.fromInputStream(this.getClass().getClassLoader().getResourceAsStream("tictactoe.pl")).mkString}

      % Query for the best move
      :- get_best_move_2d(${boardStr}, Row, Col),
         write(Row), write(','), write(Col), nl,
         halt.
    """.stripMargin

    import java.nio.file.{Files, Paths, StandardOpenOption}
    Files.write(Paths.get(prologFile), query.getBytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)

    try {
      val result = s"swipl ${prologFile}" !!
      val coords = result.trim.split(",").map(_.trim.toInt)
      if (coords.length == 2) (coords(0), coords(1))
      else (-1, -1)
    } catch {
      case _: Exception => (-1, -1)
    } finally {
      new java.io.File(prologFile).delete()
    }
  }

  post("/api/reset") {
    contentType = "application/json"

    gameState = Map[String, Any](
      "board" -> Array.fill(3)(Array.fill(3)("")),
      "currentPlayer" -> "X",
      "gameOver" -> false,
      "winner" -> null
    )

    write(gameState)
  }

  def checkWin(board: Array[Array[String]], player: String): Boolean = {
    // Check rows
    for (i <- 0 until 3) {
      if (board(i)(0) == player && board(i)(1) == player && board(i)(2) == player) {
        return true
      }
    }

    // Check columns
    for (j <- 0 until 3) {
      if (board(0)(j) == player && board(1)(j) == player && board(2)(j) == player) {
        return true
      }
    }

    // Check diagonals
    if (board(0)(0) == player && board(1)(1) == player && board(2)(2) == player) {
      return true
    }
    if (board(0)(2) == player && board(1)(1) == player && board(2)(0) == player) {
      return true
    }

    false
  }

  def isBoardFull(board: Array[Array[String]]): Boolean = {
    for (i <- 0 until 3; j <- 0 until 3) {
      if (board(i)(j) == "") return false
    }
    true
  }
}