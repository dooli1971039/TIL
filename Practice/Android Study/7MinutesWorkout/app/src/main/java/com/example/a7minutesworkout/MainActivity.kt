package com.example.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a7minutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	private  var binding:ActivityMainBinding?=null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding=ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding?.root)

		binding?.flStart?.setOnClickListener{
			val intent=Intent(this, ExerciseActivity::class.java)
			startActivity(intent)
		}

		binding?.flBMI?.setOnClickListener {
			val intent=Intent(this, BMIActivity::class.java)
			startActivity(intent)
		}

		binding?.flHistory?.setOnClickListener {
			val intent = Intent(this, HistoryActivity::class.java)
			startActivity(intent)
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		binding=null // 꼭 null로 재설정 하여, 메모리 누출을 방지해야 한다.
	}
}