package com.example.kidsdrawingapp

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaScannerConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.get //선형 레이아웃 안에 있는 뷰를 사용할 수 있게 임포트
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
//import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {
	private var drawingView:DrawingView?=null
	private var mImageButtonCurrentPaint : ImageButton?=null
	var customProgressDialog:Dialog?=null

	val openGalleryLauncher : ActivityResultLauncher<Intent> =
		registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
				result->

			if(result.resultCode == RESULT_OK && result.data!=null){
				val imageBackground:ImageView=findViewById(R.id.iv_background)
				imageBackground.setImageURI(result.data?.data)
			}
		}

	val requestPermission :ActivityResultLauncher<Array<String>> =
		registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
			permissions ->
			permissions.entries.forEach{
				val permissionName=it.key
				val isGranted=it.value

				if(isGranted){ //권한을 허용받았을 때
					Toast.makeText(this@MainActivity,"Permission granted now you can read the Storage files.",Toast.LENGTH_LONG).show()

					val pickIntent= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
					openGalleryLauncher.launch(pickIntent)
				}else{
					if(permissionName== Manifest.permission.READ_EXTERNAL_STORAGE){
						Toast.makeText(this@MainActivity,"Oops you just denied the permission.",Toast.LENGTH_LONG).show()
					}
				}
			}
		}


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		drawingView=findViewById(R.id.drawing_view)
		drawingView?.setSizeForBrush(20.toFloat())

		val linearLayoutPaintColors = findViewById<LinearLayout>(R.id.ll_paint_colors)

		mImageButtonCurrentPaint= linearLayoutPaintColors[1] as ImageButton // linearLayoutPaintColors에서 위치가 1인 아이템을 가져옴. 근데 이미지 버튼으로 취급할 것
		mImageButtonCurrentPaint!!.setImageDrawable(
			ContextCompat.getDrawable(this,R.drawable.palette_pressed)
		)

		//브러시 세팅
		val ib_brush: ImageButton = findViewById(R.id.ib_brush)
		ib_brush.setOnClickListener{
			showBrushSizeChooserDialog()
		}

		//undo 세팅
		val ib_undo: ImageButton = findViewById(R.id.ib_undo)
		ib_undo.setOnClickListener{
			drawingView?.onClickUndo()
		}

		//redo 세팅
		val ib_redo: ImageButton = findViewById(R.id.ib_redo)
		ib_redo.setOnClickListener{
			drawingView?.onClickRedo()
		}

		//이미지 세팅
		val ib_gallery:ImageButton = findViewById(R.id.ib_gallery)
		ib_gallery.setOnClickListener {
			requestStoragePermission()
		}

		//이미지 저장
		val ib_save:ImageButton = findViewById(R.id.ib_save)
		ib_save.setOnClickListener {
			if(isReadStorageAllowed()){
				showProgressDialog() //progress 띄우기
				CoroutineScope(Dispatchers.Main).launch {
				//lifecycleScope.launch{
					val flDrawingView:FrameLayout=findViewById(R.id.fl_drawing_view_container)
					saveBitmapFile(getBitmapFromView(flDrawingView))
				}
			}
		}
	}

	//dialog : 화면에 뜨는 선택 가능한 팝업창
	private fun showBrushSizeChooserDialog(){
		val brushDialog=Dialog(this) // context: dialog가 실행될 상황
		brushDialog.setContentView(R.layout.dialog_brush_size)
		brushDialog.setTitle("Brush size: ") // 우리 앱에선 보이진 않음

		//val smallBtn=brushDialog.ib_small_brush
		val smallBtn: ImageButton = brushDialog.findViewById(R.id.ib_small_brush)
		smallBtn.setOnClickListener{
			drawingView?.setSizeForBrush(10.toFloat())
			brushDialog.dismiss() //선택하면 dialog 내림
		}

		val mediumBtn: ImageButton = brushDialog.findViewById(R.id.ib_medium_brush)
		mediumBtn.setOnClickListener{
			drawingView?.setSizeForBrush(20.toFloat())
			brushDialog.dismiss() //선택하면 dialog 내림
		}

		val largeBtn: ImageButton = brushDialog.findViewById(R.id.ib_large_brush)
		largeBtn.setOnClickListener{
			drawingView?.setSizeForBrush(30.toFloat())
			brushDialog.dismiss() //선택하면 dialog 내림
		}

		brushDialog.show() //dialog를 보여줌
	}

	fun paintClicked(view: View){
		if(view!=mImageButtonCurrentPaint){
			val imageButton=view as ImageButton
			val colorTag=imageButton.tag.toString()
			drawingView?.setColor(colorTag)

			imageButton!!.setImageDrawable(
				ContextCompat.getDrawable(this,R.drawable.palette_pressed)
			)
			mImageButtonCurrentPaint!!.setImageDrawable(
				ContextCompat.getDrawable(this,R.drawable.palette_normal)
			)
			mImageButtonCurrentPaint=view
		}
	}

	private fun isReadStorageAllowed():Boolean{
		val result=ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
		return result == PackageManager.PERMISSION_GRANTED
	}

	private fun requestStoragePermission(){
		if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
			showRationaleDialog("Kids Drawing App","Kids Drawing App"+" needs to Access Your External Storage")
		}else{
			requestPermission.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE))
		}
	}

	private fun showRationaleDialog(title: String, message: String) {
		val builder: AlertDialog.Builder = AlertDialog.Builder(this)
		builder.setTitle(title)
			.setMessage(message)
			.setPositiveButton("Ok") { dialog, _ ->
				dialog.dismiss()
				requestPermission.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)) //무조건 권한이 필요할 때: 창 내리면 다시 알림 띄우기? (내가 임의로 추가)
			}.setNegativeButton("Cancel"){ dialog, _ ->
				dialog.dismiss()
			}
		builder.create().show()
	}

	private fun getBitmapFromView(view: View):Bitmap{
		val returnedBitmap=Bitmap.createBitmap(view.width,view.height,Bitmap.Config.ARGB_8888) //view의 넓비와 높이를 가져옴
		val canvas =Canvas(returnedBitmap) //준비된 캔버스에 returnedBitmap을 넣고
		val bgDrawable = view.background //background를 가져와서 배경이 있으면 캔버스로 가져오고, 없으면 흰색 배경을 만들어서 canvas와 view를 합쳐 returnedBitmap에 돌려준다.

		if(bgDrawable!=null){
			bgDrawable.draw(canvas)
		}else{
			canvas.drawColor(Color.WHITE)
		}

		view.draw(canvas)
		return returnedBitmap
	}

	private suspend fun saveBitmapFile(mBitmap:Bitmap?):String{
		var result="" //이미지를 저장할 변수
		withContext(Dispatchers.IO){
			if(mBitmap!=null){
				try {
					val bytes=ByteArrayOutputStream()
					mBitmap.compress(Bitmap.CompressFormat.PNG,90,bytes)

					//파일을 저장할 때, 저장할 위치를 이름에 포함해 저장하도록 함함
					val f= File(externalCacheDir?.absoluteFile.toString()
							+ File.separator + "KidsDrawingApp_" + System.currentTimeMillis()/1000 +".png" )

					val fo=FileOutputStream(f)
					fo.write(bytes.toByteArray())
					fo.close()

					result=f.absolutePath

					runOnUiThread{
						cancelProgressDialog()
						if(result.isNotEmpty()){
							Toast.makeText(this@MainActivity,"File saved successfully: $result",Toast.LENGTH_LONG).show()
							shareImage(result)
						}else{
							Toast.makeText(this@MainActivity,"Something went wrong while saving the file",Toast.LENGTH_LONG).show()
						}
					}
				}catch (e:Exception){
					result=""
					e.printStackTrace()
				}
			}
		}

		return result
	}

	private fun showProgressDialog() {
		customProgressDialog = Dialog(this@MainActivity)
		customProgressDialog?.setContentView(R.layout.dialog_custom_progress)
		customProgressDialog?.show()
	}

	private fun cancelProgressDialog() {
		if (customProgressDialog != null) {
			customProgressDialog?.dismiss()
			customProgressDialog = null
		}
	}

	private fun shareImage(result : String){
		MediaScannerConnection.scanFile(this@MainActivity, arrayOf(result), null) {
			path, uri ->
			val shareIntent = Intent()
			shareIntent.action = Intent.ACTION_SEND //action을 설정하는데, 아이템들을 보낼 수 있는 것으로 설정
			shareIntent.putExtra(Intent.EXTRA_STREAM, uri) //어떤 다이렉션을 사용해야 하는지 알아야 하므로, Intent 세부 사항을 추가함
			shareIntent.type = "image/png" // Intent가 처리하는 데이터의 memeType이다
			startActivity(Intent.createChooser(shareIntent, "Share"))
		}
	}
}