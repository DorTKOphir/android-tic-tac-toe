package com.example.tictactoe

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class GameResultDialog(
    context: Context,
    private val resultMessage: String,
    private val onPlayAgain: () -> Unit
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_game_result)

        val resultTextView: TextView = findViewById(R.id.result_text)
        val playAgainButton: Button = findViewById(R.id.play_again_button)

        resultTextView.text = resultMessage
        playAgainButton.setOnClickListener {
            dismiss()
            onPlayAgain()
        }
    }
}
