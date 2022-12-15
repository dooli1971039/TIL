package com.example.myquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class FinishActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_finish)

		val tvName: TextView = findViewById(R.id.tv_name)
		val tvScore: TextView = findViewById(R.id.tv_score)
		val btnFinish: Button = findViewById(R.id.btn_finish)

		tvName.text = intent.getStringExtra(Constants.USER_NAME)
		tvScore.text = "Your Score is ${
			intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
		} of ${intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)}"

		btnFinish.setOnClickListener {
			startActivity(Intent(this, MainActivity::class.java)) //인텐트를 실행해야 함
		}
	}
}