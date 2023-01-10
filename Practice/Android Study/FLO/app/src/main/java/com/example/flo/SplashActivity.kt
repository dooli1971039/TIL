package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySplashBinding

class SplashActivity:AppCompatActivity() { //메니페스트에서 제거해둠
	lateinit var binding: ActivitySplashBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding=ActivitySplashBinding.inflate(layoutInflater)
		setContentView(binding.root)

		val handler= Handler(Looper.getMainLooper())

		handler.postDelayed({
			startActivity(Intent(this,MainActivity::class.java))
		},1000)
	}
}