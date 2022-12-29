package com.example.kidsdrawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

// DrawingView라는 클래스를 view로 사용할 예정
// View를 상속 받고, drawing view 클래스에 기본 생성자를 추가함
class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {

	private var mDrawPath: CustomPath? = null //
	private var mCanvasBitmap: Bitmap? = null //비트맵
	private var mDrawPaint: Paint? = null // paint 클래스 : geometry, text, bitmap 등을 그릴 때 사용하는 스타일이나 색상의 정보값을 담고 있음
	private var mCanvasPaint: Paint? = null // canvasPaint 뷰의 인스턴스가 될 예정
	private var mBrushSize: Float = 0.toFloat()
	private var color = Color.BLACK
	private var canvas: Canvas? = null //그림 그리는 배경

	private var mPaths=ArrayList<CustomPath>() // 목록 자체는 불변해도, 목록의 요소는 바뀔 수 있음
	private var mUndoPaths=ArrayList<CustomPath>()

	init{
		setUpDrawing()
	}

	fun onClickUndo(){
		if(mPaths.size>0){
			mUndoPaths.add(mPaths.removeAt(mPaths.size-1)) //removeAt 메서드는 지운 아이템은 반환한다
			invalidate() //onDraw는 직접적으로 불러오지 X. override메서드라서
			// invalidate: 뷰가 화면에 보일 때, 전체 뷰를 무효화 한다. 그리고 onDraw가 그 후에 실행된다.
		}
	}

	fun onClickRedo(){
		if(mUndoPaths.size>0){
			mPaths.add(mUndoPaths.removeAt(mUndoPaths.size-1)) //removeAt 메서드는 지운 아이템은 반환한다
			invalidate() //onDraw는 직접적으로 불러오지 X. override메서드라서
			// invalidate: 뷰가 화면에 보일 때, 전체 뷰를 무효화 한다. 그리고 onDraw가 그 후에 실행된다.
		}
	}

	private fun setUpDrawing(){
		mDrawPaint=Paint()
		mDrawPath=CustomPath(color,mBrushSize)
		mDrawPaint!!.color=color
		mDrawPaint!!.style = Paint.Style.STROKE
		mDrawPaint!!.strokeJoin=Paint.Join.ROUND
		mDrawPaint!!.strokeCap=Paint.Cap.ROUND // Cap은 선 끝의 위치를 지정한다. paint 스타일이 Stroke이나 StrokeAndFill일때 사용한다
		mCanvasPaint=Paint(Paint.DITHER_FLAG)
		//mBrushSize=20.toFloat() <-- 메인액티비티에서 하니까 여기서 초기화 할 필요 없음
	}

	// onSizeChanged 메서드는 뷰의 크기가 변경되었을 때 레이아웃 안에 설정된다. <- 여기에 비트맵 만들기
	// 화면의 크기가 변경될 때 bitmap을 표시하도록 해야 뷰에 뭔가를 표시할 수 있다.
	override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
		super.onSizeChanged(w, h, oldw, oldh)
		mCanvasBitmap=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888) //이 비트맵을 이제 캔버스 비트맵으로 사용할 것임
		canvas=Canvas(mCanvasBitmap!!) //mCanasBitmap이 null타입이라 !!추가해서 null이 아니게 하자
	}

	// onDraw 메서드는 뭔가를 적거나 그릴 캔버스를 포함한다.
	//그림을 그리고 싶을 때 실행될 코드
	override fun onDraw(canvas: Canvas) { // Canvas? 였는데 바꿈. 오류나면 수정할 것
		super.onDraw(canvas)
		// drawBitmap은 특정한 비트맵을 상단 왼쪽을 기준으로 위치정보 (x,y)에 명시된 곳에 현재 행렬에 의해 변환된 지정 페인트를 사용하여 그림
		canvas.drawBitmap(mCanvasBitmap!!,0f,0f,mCanvasPaint) //00라 입력해서 왼쪽 위에서 시작하겠다고 지정

		//이전에 그린 것들 그리기
		for(path in mPaths){
			mDrawPaint!!.strokeWidth=path!!.brushThickness  // path의 두께를 사용함
			mDrawPaint!!.color=path!!.color // 과거의 두께,색이 지금 선택한 것과 다를 수 있음
			canvas.drawPath(path,mDrawPaint!!)
		}


		if(!mDrawPath!!.isEmpty){ //아무것도 안 그렸을 때를 제외해야 함
			mDrawPaint!!.strokeWidth=mDrawPath!!.brushThickness  //paint의 두께를 설정
			mDrawPaint!!.color=mDrawPath!!.color
			canvas.drawPath(mDrawPath!!,mDrawPaint!!)
		}
	}

	/* MotionEvent의 이벤트 기능 중 중요한 것은 3가지
	1. action done : 화면에 손가락을 댔을 때
	2. action move : 화면을 손가락으로 드래그했을 때
	3. action up : 손가락을 들었으 때
*/
	override fun onTouchEvent(event: MotionEvent?): Boolean {
		val touchX=event?.x
		val touchY=event?.y

		when(event?.action){
			MotionEvent.ACTION_DOWN -> {
				//화면을 눌렀을 때 실행할 코드
				mDrawPath!!.color=color
				mDrawPath!!.brushThickness=mBrushSize //path가 얼마나 두꺼운지 설정

				mDrawPath!!.reset() //그린 선을 지움
				mDrawPath!!.moveTo(touchX!!,touchY!!)
			}

			MotionEvent.ACTION_MOVE -> {
				//화면을 드래그하면 실행할 행동
				mDrawPath!!.lineTo(touchX!!,touchY!!)
			}

			MotionEvent.ACTION_UP -> {
				//화면에서 손가락을 떼면 실행할 행동
				mPaths.add(mDrawPath!!)
				mDrawPath=CustomPath(color,mBrushSize)
			}

			else -> return false //아무것도 일어나지 않음 (모션 이벤트는 종류가 매우 많다)
		}

		invalidate() // 뷰가 화면에 보일 때, 전체 뷰를 무효화 한다. 그리고 onDraw가 그 후에 실행된다.
		return true
	}

	fun setSizeForBrush(newSize:Float){ // 메인 액티비티에서 사용하기 위해 public으로
		//바로 newSize를 쓰면 안됨. 화면 크기를 고려해야 하기 때문
		mBrushSize=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,newSize,resources.displayMetrics) // 사용할 단위, 정보값, 측정 기준
		mDrawPaint!!.strokeWidth=mBrushSize
	}

	fun setColor(newColor: String){
		color=Color.parseColor(newColor)
		mDrawPaint!!.color=color
	}

	internal inner class CustomPath(var color: Int, var brushThickness: Float) : Path() {
		// CustomPath 클래스를 DrawingView 내부에서만 사용하며, 변수를 가져오거나 내보낼 수 있음
		// Path()를 상속받고,
	}

}