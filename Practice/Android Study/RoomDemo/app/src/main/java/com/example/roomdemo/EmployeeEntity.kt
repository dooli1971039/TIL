package com.example.roomdemo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee-table")
data class EmployeeEntity(
	@PrimaryKey(autoGenerate = true) //테이블의 있는 모든 항목이 고유해진다.
	var id:Int=0,
	var name:String="",
	@ColumnInfo(name="email-id") // 열에 내부적으로 다른 이름을 부여할 때
	var email:String=""
)
