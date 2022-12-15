package com.example.myquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val btn_start: Button =findViewById(R.id.btn_start)
		val et_name:EditText=findViewById(R.id.et_name)
		btn_start.setOnClickListener {
			if(et_name.text.isEmpty()){
				Toast.makeText(this,"Please enter your name",Toast.LENGTH_LONG).show()
			}else{
				// intent를 사용해서 현재 액티비티에서 다른 액티비티로 이동하게 함
				val intent=Intent(this, QuizQuestionsActivity::class.java) //인텐트를 만들고 어디로 가는지 알려줘야 함 (여기서, quiz로)
				// 자바 파일이기 때문에 뒤에  :class.java 추가
				intent.putExtra(Constants.USER_NAME,et_name.text.toString()) // intent로 추가적인 정보값을 한 액티비티에서 다른 액티비티로 옮기고 난 뒤에 다른 액티비티에서 정보값을 회수할 수 있다.
				startActivity(intent) //인텐트를 실행해야 함
				finish() // 지금 위치인 MainActivity를 닫는다 (없으면 Quiz화면에서 뒤로가기로 돌아올 수 있기 때문)

			}
		}

	}
}