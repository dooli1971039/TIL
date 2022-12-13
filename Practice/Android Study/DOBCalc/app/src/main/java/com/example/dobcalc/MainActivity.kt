package com.example.dobcalc

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
	//private으로 선언해서, MainActivity의 다른 클래스에서 사용할 수 없게 함
	//그래야 뷰에 없는, 즉 볼 수 없는 액티비티의 UI 요소를 사용해서 앱이 오작동을 일으키는 불상사를 막을 수 있음
	private var tvSelectedDate:TextView?=null
	private var tvAgeMinutes:TextView?=null
	private var tvAgeHours:TextView?=null
	private var tvAgeDays:TextView?=null
	private val sdf=SimpleDateFormat("yyyy/MM/dd") //날짜 포맷

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val btnDatePicker:Button=findViewById(R.id.btnDatePicker)
		tvSelectedDate=findViewById(R.id.tvSelectedDate)
		tvAgeMinutes=findViewById(R.id.tvAgeInMinutes)
		tvAgeHours=findViewById(R.id.tvAgeInHours)
		tvAgeDays=findViewById(R.id.tvAgeInDays)

		//오늘의 날짜로 세팅해둠
		var todayDate=System.currentTimeMillis()
		tvSelectedDate?.text=sdf.format(todayDate)

		btnDatePicker.setOnClickListener {
			clickDatePicker()
		}

	}

	private fun clickDatePicker(){
		val myCalendar = Calendar.getInstance()
		val year=myCalendar.get(Calendar.YEAR)
		val month=myCalendar.get(Calendar.MONTH)
		val day=myCalendar.get(Calendar.DAY_OF_MONTH)

		val dpd = DatePickerDialog(this,
			DatePickerDialog.OnDateSetListener{ view, selectedYear, selectedMonth, selectedDayOfMonth ->
				//DatePickerDialog.OnDateSetListener 이 문구 삭제해도 돌아감, view는 쓰지 않아서 _로 바꿔도 된다.
				// 날짜 선택 후 ok를 눌러야 실행되는 부분

				//선택한 날짜 화면에 표시
				val selectedDate="$selectedYear/${selectedMonth+1}/$selectedDayOfMonth"
				//tvSelectedDate?.setText(selectedDate) //nullable 형식이니 물음표를 추가할 것
				tvSelectedDate?.text=selectedDate //위와 같음

				// 선택한 날짜 분으로 변환
				val theDate=sdf.parse(selectedDate)
				theDate?.let{ //날짜가 선택된 경우에만 코드가 실행되게 한다  <--- null safety 구현
					val selectedDateInMinutes=theDate.time/60000 // date속성은 ms단위로 나타내서, 분단위로 하려면 60*1000으로 나눠야 한다
					//그러면 1970년 1월 1일 0시부터 얼마나 지났는지를 알 수 있다. (.time말고 .getTime()으로 써도 된다)

					// 오늘 날짜 분으로 변환
					val currentDate=sdf.parse(sdf.format(System.currentTimeMillis())) // 1970년부터 오늘까지 얼마나 지났는지 알 수 있다.
					currentDate?.let {
						val currentDateInMinutes=currentDate.time/60000
						//차이 구하기기
						val difInMinutes=currentDateInMinutes-selectedDateInMinutes
						tvAgeMinutes?.text=difInMinutes.toString()
						tvAgeHours?.text=(difInMinutes/60).toString()
						tvAgeDays?.text=(difInMinutes/60/24).toString()
					}
				}

											  },
			year, //여기 이 변수는 위에서 내가 선언한 변수
			month,
			day
			)

			//미래 날짜를 고르지 못하게 maxDate 설정
			dpd.datePicker.maxDate=System.currentTimeMillis()-86400000 //한시간은 360만ms이니까 24를 곱함
			dpd.show()

	}
}

