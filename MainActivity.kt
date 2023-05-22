package com.example.wordle_burwell_tibbs

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {
    var cnt = 0
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val wordToGuess = FourLetterWordList.getRandomFourLetterWord()
        val guessBox = findViewById<TextView>(R.id.guesses_shown)
        val editText = findViewById<EditText>(R.id.four_letter)  //allows user to guess
        val displayAnswer = findViewById<TextView>(R.id.show_answer)
        val failView = findViewById<TextView>(R.id.status)  //displays status of the input (i.e. Invalid)
        val dBug = findViewById<TextView>(R.id.guessed_word)  //displays guessed word

        val button = findViewById<Button>(R.id.button)
            var guessMax = 3 //Max number of attempts
            var checkGuessDisplay = "" //displays result of the checkGuess
            var userInput = "" // Shows what guess the user entered

            button.setOnClickListener {
                if (guessMax == 0){
                    finish()
                    overridePendingTransition(0, 0 )
                    startActivity(intent)
                    overridePendingTransition(0,0)
                }

                //Informs user of their last available guess
                cnt++
                if(cnt >=3){
                    Toast.makeText(
                        it.context,
                        "That was your third & last attempt!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                //Determines how many guesses the user has left
                if (guessMax != 0){

                    var isRight = false
                    val editTextString = editText.text.toString().uppercase(Locale.getDefault())
                    if(editTextString.length == 4){
                        failView.text = ""
                        val outcome = checkGuess(editTextString, wordToGuess)
                        checkGuess(editTextString, wordToGuess)

                        if(checkGuessDisplay == ""){
                            checkGuessDisplay = outcome
                            userInput = editTextString
                        }

                        else{
                            checkGuessDisplay = checkGuessDisplay + "\n" + outcome
                            userInput = userInput + "\n" + outcome
                        }

                        guessBox.text = checkGuessDisplay
                        dBug.text = userInput

                        //How app will respond if the guesses correctly
                        if (outcome == "0000") {
                            failView.text = "Your guessed Correctly! \nGuess: $wordToGuess"
                            button.text = "Reset"
                            isRight = true
                            guessMax = 0
                        } else {
                            guessMax--


                            //How app will respond if the guesses incorrectly and runs out of guesses
                            if(guessMax == 0 && !isRight){
                                failView.text = "You have no attempts remaining.\nGuess: $wordToGuess"
                                button.text = "Reset"
                            }
                        }
                    }
                else
                    //How app will respond if the guesses incorrectly or input is invalid
                    failView.text = "Input Invalid, Guess must ne a 4-Letter Word"
                }
    }
    }

    //Function used to check the accuracy of the user's guess
    private fun checkGuess(guess: String, wordToGuess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
}