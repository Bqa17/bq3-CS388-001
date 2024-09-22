package com.example.wordle

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var wordToGuess = FourLetterWordList.getRandomFourLetterWord()
    private var turnCount = 0
    private var gameWon = false

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val correctWordTextView = findViewById<TextView>(R.id.correctWord)
            correctWordTextView.text = wordToGuess
            correctWordTextView.visibility = View.INVISIBLE

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val guess: EditText = findViewById(R.id.word_guess)
        val guessButton: Button = findViewById(R.id.button)
        val correctWord = findViewById<TextView>(R.id.correctWord)

        guessButton.setOnClickListener {
            if (turnCount < 3 && !gameWon) {
                val wordGuess = guess.text.toString().uppercase()

                guess.text.clear()
                val result = checkGuess(wordGuess)

                when (turnCount) {
                    0 -> {
                        findViewById<TextView>(R.id.word1).text = wordGuess
                        findViewById<TextView>(R.id.result1).text = result
                    }

                    1 -> {
                        findViewById<TextView>(R.id.word2).text = wordGuess
                        findViewById<TextView>(R.id.result2).text = result
                    }

                    2 -> {
                        findViewById<TextView>(R.id.word3).text = wordGuess
                        findViewById<TextView>(R.id.result3).text = result
                    }
                }

                turnCount++

                if (result == "OOOO" || turnCount == 3) {
                    correctWord.visibility = View.VISIBLE
                    guessButton.isEnabled = false
                }
            }
        }
    }

    private fun checkGuess(guess: String): String {
        var result = ""
        for (i in 0..3) {
            result += if (guess[i] == wordToGuess[i]) {
                "O"
            } else if (guess[i] in wordToGuess) {
                "+"
            } else {
                "X"
            }
        }
        return result
    }
}
