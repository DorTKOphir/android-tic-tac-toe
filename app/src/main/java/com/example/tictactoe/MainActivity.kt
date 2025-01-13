// MainActivity.kt
package tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val game = TicTacToeGameManager()
    private lateinit var buttons: Array<Array<Button>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridLayout: GridLayout = findViewById(R.id.game_grid)

        val size = 3 // Define board size
        buttons = Array(size) { row ->
            Array(size) { col ->
                val button = Button(this).apply {
                    layoutParams = GridLayout.LayoutParams().apply {
                        rowSpec = GridLayout.spec(row, 1f)
                        columnSpec = GridLayout.spec(col, 1f)
                        width = 0
                        height = 0
                    }
                    textSize = 24f
                    setOnClickListener { onCellClick(row, col) }
                }
                gridLayout.addView(button)
                button
            }
        }
    }

    private fun onCellClick(row: Int, col: Int) {
        if (game.makeMove(row, col)) {
            buttons[row][col].text = game.currentPlayerSymbol
            val winner = game.checkWinner()
            when {
                winner != null -> {
                    Toast.makeText(this, "Player $winner wins!", Toast.LENGTH_LONG).show()
                    resetGame()
                }
                game.isDraw() -> {
                    Toast.makeText(this, "It's a draw!", Toast.LENGTH_LONG).show()
                    resetGame()
                }
            }
        }
    }

    private fun resetGame() {
        game.reset()
        buttons.forEach { row ->
            row.forEach { button ->
                button.text = ""
            }
        }
    }
}

class TicTacToeGame {
    private val size = 3
    val board = Array(size) { CharArray(size) { ' ' } }
    private var currentPlayer = 'X'
    val currentPlayerSymbol get() = currentPlayer

    fun makeMove(row: Int, col: Int): Boolean {
        if (board[row][col] == ' ') {
            board[row][col] = currentPlayer
            currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
            return true
        }
        return false
    }

    fun checkWinner(): Char? {
        // Check rows and columns
        for (i in 0 until size) {
            if ((0 until size).all { board[i][it] == board[i][0] && board[i][0] != ' ' }) {
                return board[i][0]
            }
            if ((0 until size).all { board[it][i] == board[0][i] && board[0][i] != ' ' }) {
                return board[0][i]
            }
        }

        // Check main diagonal
        if ((0 until size).all { board[it][it] == board[0][0] && board[0][0] != ' ' }) {
            return board[0][0]
        }

        // Check anti-diagonal
        if ((0 until size).all { board[it][size - it - 1] == board[0][size - 1] && board[0][size - 1] != ' ' }) {
            return board[0][size - 1]
        }

        return null // No winner yet
    }

    fun isDraw(): Boolean = board.all { row -> row.all { it != ' ' } }

    fun reset() {
        for (row in board) {
            row.fill(' ')
        }
        currentPlayer = 'X'
    }
}
