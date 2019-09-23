package edu.ius.c490.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

const val EXTRA_ANSWER_IS_TRUE = "edu.ius.c490.geoquiz.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "edu.ius.c490.geoquiz.answer_shown"

class CheatActivity : AppCompatActivity() {

    var answerIsTrue: Boolean = false

    private val answerText: TextView by lazy { findViewById<TextView>(R.id.answer_text) }
    private val showButton: Button by lazy { findViewById<Button>(R.id.show_button) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        showButton.setOnClickListener {
            answerText.text = "The answer is $answerIsTrue"
            setAnswerShownResult(true)
        }
    }

    fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}
