package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizapp.Constants
import com.example.quizapp.Question
import com.example.quizapp.R

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mUsername: String? = null
    private var mCorrectAnswers: Int = 0

    private var mSelectedOptionPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_quiz_questions)


        mUsername = intent.getStringExtra(Constants.USER_NAME)

        mQuestionsList = Constants.getQuestion()

        setQuestion()

        findViewById<TextView>(R.id.tv_optionOne).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_optionTwo).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_optionThree).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_optionFour).setOnClickListener(this)

        findViewById<Button>(R.id.btnSubmit).setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.tv_optionOne -> {

                selectedOptionView(findViewById(R.id.tv_optionOne), 1)
            }

            R.id.tv_optionTwo -> {

                selectedOptionView(findViewById(R.id.tv_optionTwo), 2)
            }

            R.id.tv_optionThree -> {

                selectedOptionView(findViewById(R.id.tv_optionThree), 3)
            }

            R.id.tv_optionFour -> {

                selectedOptionView(findViewById(R.id.tv_optionFour), 4)
            }


            R.id.btnSubmit -> {

                if (mSelectedOptionPosition == 0) {

                    mCurrentPosition++

                    when {

                        mCurrentPosition <= mQuestionsList!!.size -> {

                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this,ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME,mUsername)
                            intent.putExtra(Constants.CORRECT_ANSWERS,mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS,mQuestionsList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)

                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }
                    else{
                        mCorrectAnswers++
                    }

                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        findViewById<Button>(R.id.btnSubmit).text = "FINISH"
                    } else {
                        findViewById<Button>(R.id.btnSubmit).text = "GO TO NEXT QUESTION"
                    }

                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun setQuestion() {

        val question = mQuestionsList!!.get(mCurrentPosition - 1)

        defaultOptionsView()


        if (mCurrentPosition == mQuestionsList!!.size) {
            findViewById<Button>(R.id.btnSubmit).text = "FINISH"
        } else {
            findViewById<Button>(R.id.btnSubmit).text = "SUBMIT"
        }


        findViewById<ProgressBar>(R.id.progressBar).progress = mCurrentPosition
        findViewById<TextView>(R.id.tvProgress).text = "$mCurrentPosition" + "/" + findViewById<ProgressBar>(R.id.progressBar).getMax()

        findViewById<TextView>(R.id.tvQuestion).text = question.question
        findViewById<ImageView>(R.id.iv_image).setImageResource(question.image)
        findViewById<TextView>(R.id.tv_optionOne).text = question.optionOne
        findViewById<TextView>(R.id.tv_optionTwo).text = question.optionTwo
        findViewById<TextView>(R.id.tv_optionThree).text = question.optionThree
        findViewById<TextView>(R.id.tv_optionFour).text = question.optionFour
    }


    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {

        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(
            Color.parseColor("#363A43")
        )
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this@QuizQuestionsActivity,
            R.drawable.selected_option_border_bg
        )
    }

    private fun defaultOptionsView() {

        val options = ArrayList<TextView>()
        options.add(0, findViewById<TextView>(R.id.tv_optionOne))
        options.add(1, findViewById<TextView>(R.id.tv_optionTwo))
        options.add(2, findViewById<TextView>(R.id.tv_optionThree))
        options.add(3, findViewById<TextView>(R.id.tv_optionFour))

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this@QuizQuestionsActivity,
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {

        when (answer) {

            1 -> {
                findViewById<TextView>(R.id.tv_optionOne).background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }
            2 -> {
                findViewById<TextView>(R.id.tv_optionTwo).background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }
            3 -> {
                findViewById<TextView>(R.id.tv_optionThree).background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }
            4 -> {
                findViewById<TextView>(R.id.tv_optionFour).background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }
        }
    }

}