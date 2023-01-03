package com.example.viewbindingrv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viewbindingrv.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	var binding:ActivityMainBinding?=null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		binding=ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding?.root)
		val adapter=MainAdapter(TaskList.taskList)
		//binding?.taskRv?.layoutManager=LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false) //xml에서 하던가 여기서 하던가
		binding?.taskRv?.adapter=adapter

	}

	override fun onDestroy() {
		super.onDestroy()
		binding=null
	}
}