package com.example.customdialog

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		// 1. 스낵바 (위, 아래 동작은 똑같음)
		val imageButton:ImageButton = findViewById(R.id.image_button)
		imageButton.setOnClickListener { view ->
			Snackbar.make(view, "You have clicked image button.", Snackbar.LENGTH_LONG).show()
		}
		/*
		imageButton.setOnClickListener {
			Snackbar.make(it, "You have clicked image button.", Snackbar.LENGTH_LONG).show()
		}
		*/


		// 2. alert
		val btnAlertDialog: Button = findViewById(R.id.btn_alert_dialog)
		btnAlertDialog.setOnClickListener { view ->
			alertDialogFunction()
		}

		// 3. Custom Dialog
		val btnCustomDialog:Button = findViewById(R.id.btn_custom_dialog)
		btnCustomDialog.setOnClickListener {
			customDialogFunction()
		}

		// 4. Custom Progress Dialog - 진행상황을 보여주는 dialog
		// 예전에는 안드로이드에서 자동으로 사용할 수 있었지만 지금은 안 되기 때문에 직접 만듬
		val btnCustomProgress:Button = findViewById(R.id. btn_custom_progress_dialog)
		btnCustomProgress.setOnClickListener {
			customProgressDialogFunction()
		}
	}

	private fun alertDialogFunction() {
		// Builder 클래스로 dialog를 편하게 만들 수 있다.
		// builder는 alert dialog 타입으로 context를 이용해 실행할 상황을 this로 설정한다 (MainActivity 클래스에서 보여주려고)
		val builder = AlertDialog.Builder(this)
		builder.setIcon(android.R.drawable.ic_dialog_alert) // 아이콘 설정
		builder.setTitle("Alert") // title 설정
		builder.setMessage("This is Alert Dialog. Which is used to show alerts in our app.") //메세지 설정

		//performing positive action
		// which 는 사용하지 않는 매개변수이지만, 삭제하면 안 된다.
		builder.setPositiveButton("Yes") { dialogInterface, which ->
			Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()
			dialogInterface.dismiss() // Dialog will be dismissed
		}
		//performing cancel action
		builder.setNeutralButton("Cancel") { dialogInterface, which ->
			Toast.makeText(applicationContext, "clicked cancel\noperation cancel", Toast.LENGTH_LONG).show()
			dialogInterface.dismiss() // Dialog will be dismissed
		}
		//performing negative action
		builder.setNegativeButton("No") { dialogInterface, which ->
			Toast.makeText(applicationContext, "clicked No", Toast.LENGTH_LONG).show()
			dialogInterface.dismiss() // Dialog will be dismissed
		}

		// 위에서 세팅 다 했으니 alert 만듬
		val alertDialog: AlertDialog = builder.create()
		// Set other dialog properties
		alertDialog.setCancelable(false) // false: alert창 밖을 눌러도 창이 사라지지 않는다.
		//alertDialog.setCancelable(true) // true: alert창 밖을 누르면 창이 사라짐
		alertDialog.show()
	}

	private fun customDialogFunction() {
		val customDialog = Dialog(this)
		customDialog.setContentView(R.layout.dialog_custom) //직접 UI를 만든다
		// submit 버튼
		customDialog.findViewById<TextView>(R.id.tv_submit).setOnClickListener {
			//applicationContext 대신 this를 써도 상관 없음
			//그러나 applicationContext가 필요한 때도 있다. closure 안에서는 MainActivity의 context인 applicationContext가 더 안전해서 그렇다.
			Toast.makeText(applicationContext, "clicked submit", Toast.LENGTH_LONG).show()
			customDialog.dismiss() // Dialog will be dismissed
		}
		// cancle 버튼
		customDialog.findViewById<TextView>(R.id.tv_cancel).setOnClickListener {
			Toast.makeText(applicationContext, "clicked cancel", Toast.LENGTH_LONG).show()
			customDialog.dismiss()
		}
		customDialog.show()
	}

	private fun customProgressDialogFunction() {
		val customProgressDialog = Dialog(this)
		customProgressDialog.setContentView(R.layout.dialog_custom_progress)
		customProgressDialog.show()
	}
}