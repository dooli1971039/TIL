package com.example.happyplaces.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "happyPlace-table")
data class HappyPlaceModel(
	@PrimaryKey(autoGenerate = true) //테이블의 있는 모든 항목이 고유해진다.
	var id: Int,
	var title: String,
	var image:String,
	var description: String,
	var date:String,
	var location:String,
	var latitude: Double,
	var longitude: Double
)
