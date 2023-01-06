package com.example.cameraimageselection

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.cameraimageselection.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	private var binding: ActivityMainBinding? = null

	private val cameraLauncher: ActivityResultLauncher<Intent> =
		registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
				result ->
			if (result.resultCode == RESULT_OK && result.data!=null){
				val thumbNail : Bitmap = result.data?.extras!!.get("data") as Bitmap
				binding?.ivImage?.setImageBitmap(thumbNail) //이미지 연결
			}
		}


	private val requestPermission: ActivityResultLauncher<String> =
		registerForActivityResult(ActivityResultContracts.RequestPermission()){
				isGranted ->
			if (isGranted){ //권한 요청을 허락했으면 사진 찍기로 너어감
				val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) //이미지 사진 찍는 곳으로 넘어감
				cameraLauncher.launch(intent)
			}else{ //권한 요청을 거절했을 때
				Toast.makeText(this@MainActivity, "Oops, you just denied the permission.", Toast.LENGTH_LONG).show()
			}
		}


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding?.root)

		binding?.btnCamera?.setOnClickListener{
			requestCameraPermission()
		}
	}

	private fun requestCameraPermission(){ //카메라 버튼 눌렀을 때
		//거부했었으면 알림창 띄우기
		if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
			showRationalDialog("Demo App","To use this feature you need to allow the access to the camera")
		}else{
			//허락했으면 권한 요청
			requestPermission.launch(Manifest.permission.CAMERA)
		}
	}

	// Function for showing rational dialog
	private fun showRationalDialog(title: String, message: String){
		val builder: AlertDialog.Builder = AlertDialog.Builder(this)
		builder.setTitle(title).setMessage(message)
			.setPositiveButton("Cancel"){dialog, _->
			dialog.dismiss()
		}
		builder.create().show()
	}

	override fun onDestroy() {
		super.onDestroy()

		if (binding != null){
			binding = null
		}
	}

}