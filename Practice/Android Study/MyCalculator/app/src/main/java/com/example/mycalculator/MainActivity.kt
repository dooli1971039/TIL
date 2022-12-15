package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
	private var tvInput: TextView? = null
	var lastNumeric: Boolean = false
	var lastDot: Boolean = false
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		tvInput = findViewById(R.id.tvInput)
	}

	fun onDigit(view: View) { //이 view가 실제 버튼을 의미한다.
		//onDigit이 호출될 때, view 부분이 메소드를 불러온다. (버튼으니 ID, 텍스트 등 모든 속성의 정보)
		// view.text라고 하면 안된다. view에는 text 속성이 없기 때문. 그래서 버튼으로 바꾸는 것
		tvInput?.append((view as Button).text) //tvInput이 nullable이라 물음표를 넣어야 한다.(null이 아닌 경우에만 실행되게)
		lastNumeric = true
		lastDot = false
	}

	fun onClear(view: View) {
		tvInput?.text = ""
	}

	fun onDecimalPoint(view: View) {
		if (lastNumeric && !lastDot) {
			tvInput?.append(".")
			lastNumeric = false
			lastDot = true
		}
	}

	fun onOperator(view: View) {
		//text가 비어있는지 아닌지를 확인하고, 안 비어있으면 실행
		tvInput?.text?.let {
			//tvInput.text를 it으로 받음음
			if (lastNumeric && !isOperatorAdded(it.toString())) {
				tvInput?.append((view as Button).text)
				lastNumeric = false
				lastDot = false
			}
		}

	}

	fun onEqual(view: View) {
		if (lastNumeric) {
			var tvValue = tvInput?.text.toString()
			var prefix = ""
			try {
				if (tvValue.startsWith("-")) {
					prefix = "-"
					tvValue = tvValue.substring(1)
				}

				if (tvValue.contains("-")) {
					val splitValue = tvValue.split("-")
					var one = splitValue[0]
					var two = splitValue[1]

					if (prefix.isNotEmpty()) {
						one = prefix + one
					}

					var result = one.toDouble() - two.toDouble()
					tvInput?.text = removeZeroAfterDot(result.toString())
				} else if (tvValue.contains("+")) {
					val splitValue = tvValue.split("+")
					var one = splitValue[0]
					var two = splitValue[1]

					if (prefix.isNotEmpty()) {
						one = prefix + one
					}

					var result = one.toDouble() + two.toDouble()
					tvInput?.text = removeZeroAfterDot(result.toString())
				} else if (tvValue.contains("*")) {
					val splitValue = tvValue.split("*")
					var one = splitValue[0]
					var two = splitValue[1]

					if (prefix.isNotEmpty()) {
						one = prefix + one
					}

					var result = one.toDouble() * two.toDouble()
					tvInput?.text = removeZeroAfterDot(result.toString())
				} else if (tvValue.contains("/")) {
					val splitValue = tvValue.split("/")
					var one = splitValue[0]
					var two = splitValue[1]

					if (prefix.isNotEmpty()) {
						one = prefix + one
					}

					var result = one.toDouble() / two.toDouble()
					tvInput?.text = removeZeroAfterDot(result.toString())
				}

			} catch (e: ArithmeticException) {
				e.printStackTrace()
			}
		}
	}

	private fun removeZeroAfterDot(result: String): String {
		if (result.contains(".0")) return result.substring(0, result.length - 2)
		else return result
	}

	private fun isOperatorAdded(value: String): Boolean {
		return if (value.startsWith("-")) { //음수를 계산하기 위함
			false
		} else { //기호가 하나라도 있으면 true
			value.contains("/") || value.contains("*") || value.contains("-") || value.contains("+")
		}
	}
}