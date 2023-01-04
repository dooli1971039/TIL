package com.example.roomdemo

import android.app.Application

class EmployeeApp:Application() {
	val db by lazy { //lazy: 필요할 때만 변수를 전달함 (직접적 X, 필요할 때 O)
		EmployeeDatabase.getInstance(this)
	}
}