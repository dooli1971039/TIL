package com.example.a7minutesworkout

//변수는 비공개로, 메소드는 공개로 만들어 변수들을 제어하고 안전하게 보호,접근할 수 있다
class ExerciseModel(
	private var id:Int,
	private var name:String,
	private var image:Int,
	private var isCompleted:Boolean = false, // exercise가 끝났는지
	private var isSelected:Boolean = false // exercise 선택 여부에 따라
	){

	fun getId():Int{
		return id
	}
	fun setId(id:Int){
		this.id=id
	}

	fun getName(): String {
		return name
	}

	fun setName(name: String) {
		this.name = name
	}

	fun getImage(): Int {
		return image
	}

	fun setImage(image: Int) {
		this.image = image
	}

	fun getIsCompleted(): Boolean {
		return isCompleted
	}

	fun setIsCompleted(isCompleted: Boolean) {
		this.isCompleted = isCompleted
	}

	fun getIsSelected(): Boolean {
		return isSelected
	}

	fun setIsSelected(isSelected: Boolean) {
		this.isSelected = isSelected
	}
}