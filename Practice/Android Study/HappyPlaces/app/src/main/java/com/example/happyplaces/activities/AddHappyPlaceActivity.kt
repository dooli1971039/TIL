package com.example.happyplaces.activities

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.happyplaces.R
import com.example.happyplaces.database.HappyPlaceApp
import com.example.happyplaces.database.HappyPlaceDao
import com.example.happyplaces.databinding.ActivityAddHappyPlaceBinding
import com.example.happyplaces.models.HappyPlaceModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddHappyPlaceActivity : AppCompatActivity(), View.OnClickListener {
	private var binding : ActivityAddHappyPlaceBinding?=null
	private var cal=Calendar.getInstance()
	private lateinit var dateSetListener: OnDateSetListener
	private var saveImageToInternalStorage:Uri?=null
	private var mLatitude:Double=0.0
	private var mLongitude:Double=0.0

	private var hpDao:HappyPlaceDao?=null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding= ActivityAddHappyPlaceBinding.inflate(layoutInflater)
		setContentView(binding?.root)

		/* 툴바 */
		setSupportActionBar(binding?.toolbarAddPlace)
		supportActionBar?.setDisplayHomeAsUpEnabled(true) // 윗줄에 뒤로가기 버튼 추가
		binding?.toolbarAddPlace?.setNavigationOnClickListener {
			onBackPressed()
		}

		dateSetListener=DatePickerDialog.OnDateSetListener {
			view, year, month, dayOfMonth ->
			cal.set(Calendar.YEAR,year)
			cal.set(Calendar.MONTH,month)
			cal.set(Calendar.DAY_OF_MONTH,dayOfMonth)
			updateDateInView() //화면에 사용자의 입력을 표시하자
		}

		hpDao = (application as HappyPlaceApp).db.hpDao()

		updateDateInView()
		binding?.etDate?.setOnClickListener(this)
		binding?.tvAddImage?.setOnClickListener(this)
		binding?.btnSave?.setOnClickListener(this)
		binding?.btn2?.setOnClickListener(this)


	}

	override fun onClick(v: View?) {
		when(v!!.id){
			R.id.et_date -> {
				DatePickerDialog(this@AddHappyPlaceActivity,
					dateSetListener,
					cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)) //사용자의 입력을 가져오자
					.show()
			}

			R.id.tv_add_image -> {
				val pictureDialog=AlertDialog.Builder(this)
				pictureDialog.setTitle("Select Action")
				val pictureDialogItems= arrayOf("Sellect photo from Gallery", "Capture photo from camera")
				pictureDialog.setItems(pictureDialogItems){
					dialog,which ->
					when(which){
						0 -> choosePhotoFromGallery()
						1 -> takePhotoFromCamera()
					}
				}.show()
			}

			R.id.btn_save -> {
				//데이터 모델을 저장함
				saveImage()
			}
		}
	}



	private fun saveImage(){
		if(hpDao!=null){
			when {
				binding?.etTitle?.text.isNullOrEmpty()-> {
					Toast.makeText(this, "Please enter title", Toast.LENGTH_SHORT).show()
				}
				binding?.etDescription?.text.isNullOrEmpty() -> {
					Toast.makeText(this, "Please enter description", Toast.LENGTH_SHORT).show()
				}
				binding?.etLocation?.text.isNullOrEmpty() -> {
					Toast.makeText(this, "Please select location", Toast.LENGTH_SHORT).show()
				}
				saveImageToInternalStorage == null -> {
					Toast.makeText(this, "Please add image", Toast.LENGTH_SHORT).show()
				}else -> {
					val title=binding?.etTitle?.text.toString()
					val descript=binding?.etDescription?.text.toString()
					val date=binding?.etDate?.text.toString()
					val location=binding?.etLocation?.text.toString()

					lifecycleScope.launch{
						hpDao!!.insert(HappyPlaceModel(0,title,saveImageToInternalStorage.toString(),descript,date,location,mLatitude,mLongitude))
						Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_LONG).show()
					}

				}
			}
		}
	}

	private val cameraLauncher: ActivityResultLauncher<Intent> =
		registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
				result ->
			if (result.resultCode == RESULT_OK && result.data!=null){
				try {
					val thumbNail: Bitmap = result!!.data!!.extras?.get("data") as Bitmap
					saveImageToInternalStorage=saveImageToInternalStorage(thumbNail)

					binding?.ivPlaceImage?.setImageBitmap(thumbNail) // 이미지 연결
				} catch (e: IOException) {
					e.printStackTrace()
					Toast.makeText(this, "Failed to take photo from Camera", Toast.LENGTH_SHORT).show()
				}
			}
		}

	private fun takePhotoFromCamera() {
		Dexter.withContext(this).withPermissions(
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.CAMERA
		).withListener(object : MultiplePermissionsListener {
			override fun onPermissionsChecked(report: MultiplePermissionsReport) {
				if(report!!.areAllPermissionsGranted()){ //단언연산자 사용해야 함
					//권한 승인 했을 때 => 사진을 찍으러 가야함
					val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) //이미지 사진 찍는 곳으로 넘어감
					cameraLauncher.launch(intent)
				} else showRationaleDialogForPermission()
			}

			override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>, token: PermissionToken, ) {
				// 유저에게 이 권한이 왜 필요한지 보여줘야 한다. (유저가 '아니오'를 선택했을 때 보여진다)
				showRationaleDialogForPermission()
			}
		}).onSameThread().check() //onSameThread() 추가하기
	}

	private val galleryLauncher: ActivityResultLauncher<Intent> =
		registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
				result ->
			if (result.resultCode == RESULT_OK && result.data!=null){
				val contentURI= result.data!!.data

				try{
					val selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,contentURI )  //아직까진 굴러감
					saveImageToInternalStorage=saveImageToInternalStorage(selectedImageBitmap)

					//binding?.ivPlaceImage?.setImageURI(contentURI)
					binding?.ivPlaceImage?.setImageBitmap(selectedImageBitmap) //아직까진 굴러감. 그냥 윗줄 쓰는게 맘 편할듯듯
			}catch (e:IOException){
					e.printStackTrace()
					Toast.makeText(this, "Failed to load image from gallery", Toast.LENGTH_SHORT).show()
				}
			}
		}

	private fun choosePhotoFromGallery() {
		Dexter.withContext(this).withPermissions(
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE
		).withListener(object : MultiplePermissionsListener {
			override fun onPermissionsChecked(report: MultiplePermissionsReport) {
				if(report!!.areAllPermissionsGranted()){ //단언연산자 사용해야 함
					//권한 승인 했을 때 => 사진을 가져와야함
					val galleryIntent=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
					galleryLauncher.launch(galleryIntent)
				} else showRationaleDialogForPermission()
			}

			override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>, token: PermissionToken, ) {
				// 유저에게 이 권한이 왜 필요한지 보여줘야 한다. (유저가 '아니오'를 선택했을 때 보여진다)
				showRationaleDialogForPermission()
			}
		}).onSameThread().check() //onSameThread() 추가하기
	}

	private fun showRationaleDialogForPermission() {
		AlertDialog.Builder(this)
			.setMessage("It looks like you have turned off permission required for this feature. It can be enabled under the Applications Settings.")
			.setPositiveButton("GO TO SETTINGS") { _, _ ->
				try {
					val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
					val uri = Uri.fromParts("package", packageName, null)
					intent.data = uri
					startActivity(intent)
				} catch (e: ActivityNotFoundException) {
					e.printStackTrace()
				}
			}.setNegativeButton("Cancel") { dialog, which ->
				dialog.dismiss()
			}.show()
	}


	private fun updateDateInView(){
		val myFormat="dd.MM.yyyy"
		val sdf=SimpleDateFormat(myFormat, Locale.getDefault())
		binding?.etDate?.setText(sdf.format(cal.time).toString())
	}

	private fun saveImageToInternalStorage(bitmap: Bitmap) : Uri{
		val wrapper=ContextWrapper(applicationContext)
		var file=wrapper.getDir("HappyPlacesImage", Context.MODE_PRIVATE)
		file= File(file, "${UUID.randomUUID()}.jpg") //저장될 경로, 고유한 ID

		try{
			val stream :OutputStream = FileOutputStream(file)
			bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
			stream.flush()
			stream.close()
		}catch (e:IOException){
			e.printStackTrace()
		}

		return Uri.parse(file.absolutePath)
	}


}