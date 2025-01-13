package com.example.tictactoe

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.GridLayout
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
                        setMargins(4, 4, 4, 4)
                    }
                    textSize = 32f
                    gravity = Gravity.CENTER
                    setBackgroundColor(getColor(android.R.color.holo_blue_light))
                    setOnClickListener { onCellClick(row, col) }
                }
                gridLayout.addView(button)
                button
            }
        }
    }

    private fun onCellClick(row: Int, col: Int) {
        val currentPlayer = game.currentPlayerSymbol.toString()
        if (game.makeMove(row, col)) {
            buttons[row][col].text = currentPlayer
            when {
                game.isDraw() -> {
                    showResultDialog("It's a draw!")
                }

                else -> {
                    val winner = game.checkWinner()
                    if (winner != null) {
                        showResultDialog("$winner Won!")
                    }
                }
            }
        }
    }

    private fun showResultDialog(message: String) {
        val dialog = GameResultDialog(this, message) {
            resetGame()
        }
        dialog.show()
    }

    private fun resetGame() {
        game.reset()
        buttons.forEach { row ->
            row.forEach { button ->
                button.text = ""
                button.setBackgroundColor(getColor(android.R.color.holo_blue_light))
            }
        }
    }
}
