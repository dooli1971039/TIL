package com.example.myquizapp
//안드로이드에서는 이미지를 integer 속성으로 만들 수 있다.
data class Question(
	val id:Int,
	val question:String,
	val image:Int,
	val optionOne:String,
	val optionTwo:String,
	val optionThree:String,
	val optionFour:String,
	val correctAnswer:Int,
)
