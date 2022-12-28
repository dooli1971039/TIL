package com.example.permissiondemo

import android.Manifest //이걸 임포트해야 permission을 사용함
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
	//Todo 1: This time we creat the Activity result launcher of type Array<String>
	//	카메라 권한을 요청하려면, activity result launcher가 필요함함
	//카메라랑 위치 모두를 요청하기 때문에, 그냥 string이 아니라 Array<string>을 넣는다
	private val cameraAndLocationResultLauncher: ActivityResultLauncher<Array<String>> =
		registerForActivityResult( //이걸로 액티비티 결과를 기억하게 만든다.
			ActivityResultContracts.RequestMultiplePermissions() // 권한 요청함 (multiple)
		) { permissions ->
			/**
			Here it returns a Map of permission name as key with boolean as value
			Todo 2: We loop through the map to get the value we need which is the boolean
			value
			 */
			Log.d("MainActivity", "Permissions $permissions")
			permissions.entries.forEach {
				val permissionName = it.key
				//Todo 3: if it is granted then we show its granted
				val isGranted = it.value
				if (isGranted) { // 권한 요청이 허용되었을 때
					if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION) { //위치
						Toast.makeText(this, "Permission granted for FINE location", Toast.LENGTH_LONG).show()
					} else if (permissionName == Manifest.permission.ACCESS_COARSE_LOCATION) { //위치
						android.widget.Toast.makeText(this, "Permission granted for COARSE location", android.widget.Toast.LENGTH_LONG).show()
					} else { //카메라
						Toast.makeText(this, "Permission granted for Camera", Toast.LENGTH_LONG).show()
					}
				} else { // 권한 요청이 거부되었을 때
					if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION) { //위치
						Toast.makeText(this, "Permission denied for FINE location", Toast.LENGTH_LONG).show()
					} else if (permissionName == Manifest.permission.ACCESS_COARSE_LOCATION) { //위치
						Toast.makeText(this, "Permission denied for COARSE location", Toast.LENGTH_LONG).show()
					} else{ // 카메라
						Toast.makeText(this, "Permission denied for Camera", Toast.LENGTH_LONG).show()
					}
				}
			}
		}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val btnCameraPermission: Button = findViewById(R.id.btnCameraPermission)

		btnCameraPermission.setOnClickListener {
			//안드로이드 버전 체크 (낮으면 다른 방식을 쓰려고), 안드로이드 M 버전을 포함한 최신버전인지 체크
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
				//요청이 거부되었으면, 왜 승인이 필요한지를 보여준다.
				showRationaleDialog(
					" Permission Demo requires camera access",
					"Camera cannot be used because Camera access is denied"
				)
			} else {
				// You can directly ask for the permission.
				// The registered ActivityResultCallback gets the result of this request.
				cameraAndLocationResultLauncher.launch(arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION))
			}
		}
	}

	/**
	 * Shows rationale dialog for displaying why the app needs permission
	 * Only shown if the user has denied the permission request previously
	 */
	private fun showRationaleDialog(title: String, message: String) {
		val builder: AlertDialog.Builder = AlertDialog.Builder(this)
		builder.setTitle(title)
			.setMessage(message)
			.setPositiveButton("Cancel") { dialog, _ ->
				dialog.dismiss()
			}
		builder.create().show()
	}
}