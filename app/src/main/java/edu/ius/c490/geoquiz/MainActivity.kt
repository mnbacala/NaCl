package edu.ius.c490.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

const val REQUEST_CODE_CHEAT = 0
const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val trueButton: Button by lazy { findViewById<Button>(R.id.true_button) }
    private val falseButton: Button by lazy { findViewById<Button>(R.id.false_button) }
    private val cheatButton: Button by lazy { findViewById<Button>(R.id.cheat_button) }

    private val questionBank = listOf(
        Question(R.string.question_thailand, true),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_ocean, true)
    )

    private var currentIndex = 0
    private lateinit var nextButton: Button
    lateinit var questionView: TextView
    private var isCheater = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nextButton = findViewById(R.id.next_button)
        questionView = findViewById(R.id.question_view)

        val questionTextResId = questionBank[currentIndex].textResId
        questionView.setText(questionTextResId)

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            val questionTextResId = questionBank[currentIndex].textResId
            questionView.setText(questionTextResId)
        }

        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        cheatButton.setOnClickListener {
            val intent = CheatActivity.newIntent(this@MainActivity, questionBank[currentIndex].answer)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            Log.d(TAG, "Result code: $resultCode")
            return
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
            Log.d(TAG, "Cheater status: $isCheater")
        }
    }

    fun checkAnswer(userAnswer: Boolean) {
        if (isCheater)
            Toast.makeText(this@MainActivity, "Cheaters never win.", Toast.LENGTH_LONG).show()
        else if (userAnswer == questionBank[currentIndex].answer)
            Toast.makeText(this@MainActivity, "Correct", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this@MainActivity, "Incorrect", Toast.LENGTH_SHORT).show()
    }
}
