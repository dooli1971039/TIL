package com.example.roomdemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EmployeeEntity::class],version = 2) //데이터베이스에 대한 몇가지 추가 정보 벙의
abstract class EmployeeDatabase: RoomDatabase() {

	abstract fun employeeDao():EmployeeDao

	companion object {
		@Volatile //휘발성 변수가 된다.
		private var INSTANCE: EmployeeDatabase? = null //getInstance가 반환하는 모든 데이터베이스에 레퍼런스를 유지함

		fun getInstance(context: Context): EmployeeDatabase {
			synchronized(this) {
				var instance = INSTANCE

				if (instance == null) { //null이면 새 항복을 생성하자
					instance = Room.databaseBuilder(context.applicationContext, EmployeeDatabase::class.java, "employee_database")
						// https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
						.fallbackToDestructiveMigration()
						.build()
					INSTANCE = instance
				}

				return instance
			}
		}
	}

}
