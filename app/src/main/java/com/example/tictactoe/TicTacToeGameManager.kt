package com.example.tictactoe

class TicTacToeGameManager(boardSize: Int = 3) {
    private val board = Array(boardSize) { CharArray(boardSize) { ' ' } }
    private var currentPlayer = 'X'

    fun makeMove(row: Int, col: Int): Boolean {
        if (board[row][col] == ' ') {
            board[row][col] = currentPlayer
            currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
            return true
        }
        return false
    }

    fun checkWinner(): Char? {
        return checkRowsAndColsWinner() ?: checkMainDiagonalWinner() ?: checkAntiDiagonalWinner()
    }


    private fun checkMainDiagonalWinner(): Char? {
        val size = board.size

        if ((0 until size).all { board[it][it] == board[0][0] && board[0][0] != ' ' }) {
            return board[0][0]
        }

        return null
    }

    private fun checkAntiDiagonalWinner(): Char? {
        val size = board.size

        if ((0 until size).all { board[it][size - it - 1] == board[0][size - 1] && board[0][size - 1] != ' ' }) {
            return board[0][size - 1]
        }

        return null
    }

    private fun checkRowsAndColsWinner(): Char? {
        val size = board.size

        for (index in 0 until size) {
            val rowWinner = checkRowWinner(index)
            if (rowWinner != null) {
                return rowWinner
            }

            val colWinner = checkColWinner(index)
            if (colWinner != null) {
                return colWinner
            }
        }

        return null
    }

    private fun checkRowWinner(col: Int): Char? {
        val size = board.size

        if ((0 until size).all { board[it][col] == board[0][col] && board[0][col] != ' ' }) {
            return board[0][col]
        }

        return null
    }

    private fun checkColWinner(row: Int): Char? {
        val size = board.size

        if ((0 until size).all { board[row][it] == board[row][0] && board[row][0] != ' ' }) {
            return board[row][0]
        }

        return null
    }


    fun isDraw(): Boolean = board.all { row -> row.all { it != ' ' } }
}
