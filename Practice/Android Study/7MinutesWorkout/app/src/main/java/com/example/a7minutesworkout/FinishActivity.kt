package com.example.a7minutesworkout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.a7minutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
	private  var binding: ActivityFinishBinding?=null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding= ActivityFinishBinding.inflate(layoutInflater)
		setContentView(binding?.root)

		/* 툴바 세팅 */
		setSupportActionBar(binding?.toolbarFinishActivity)
		if(supportActionBar!=null){
			supportActionBar?.setDisplayHomeAsUpEnabled(true) //뒤로가기 버튼 활성화
		}
		binding?.toolbarFinishActivity?.setNavigationOnClickListener {
			onBackPressed() //뒤로 가기 기능 추가
		}

		/* 뷰 바인딩 */
		binding?.btnFinish?.setOnClickListener{
			finish()
		}

		/* Dao */
		val dao = (application as WorkOutApp).db.historyDao()
		addDateToDatabase(dao)
	}

	private fun addDateToDatabase(historyDao: HistoryDao){
		val c= Calendar.getInstance()
		val dateTime=c.time
		val sdf=SimpleDateFormat("dd MMM yyyy HH:mm:ss",Locale.getDefault())
		val date=sdf.format(dateTime)

		lifecycleScope.launch{
			historyDao.insert(HistoryEntity(date)) //데이터 추가
		}
	}
}