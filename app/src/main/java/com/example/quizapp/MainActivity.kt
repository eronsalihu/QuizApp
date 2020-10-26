package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility =  View.SYSTEM_UI_FLAG_FULLSCREEN

        findViewById<Button>(R.id.btnStart).setOnClickListener {
            if (findViewById<EditText>(R.id.et_name).text.isEmpty()){
                Toast.makeText(this,"Please enter your name",Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(this,QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME,findViewById<TextView>(R.id.et_name).text.toString())
                startActivity(intent)
                finish()
            }
        }
    }
}