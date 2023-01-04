package com.example.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding
import com.example.a7minutesworkout.databinding.DialogCustomBackConfirmationBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
	private var binding: ActivityExerciseBinding?=null

	private var restTimerDuration:Long=1
	private var restTimer:CountDownTimer?=null
	private var restProgress=0

	private var exerciseTimerDuration:Long=1
	private var exerciseTimer:CountDownTimer?=null
	private var exerciseProgress=0

	private var exerciseList:ArrayList<ExerciseModel>?=null
	private var currentExercisePosition=-1 //어떤 exercise를 하고 있는지

	private var tts: TextToSpeech?=null
	private var player:MediaPlayer?=null

	private var exerciseAdapter:ExerciseStatusAdapter?=null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		/* viewBinding */
		binding=ActivityExerciseBinding.inflate(layoutInflater)
		setContentView(binding?.root)

		/* 툴바 세팅 */
		setSupportActionBar(binding?.toolbarExercise)
		if(supportActionBar!=null){
			supportActionBar?.setDisplayHomeAsUpEnabled(true) //뒤로가기 버튼 활성화
		}
		binding?.toolbarExercise?.setNavigationOnClickListener {
			onBackPressed() //뒤로 가기 기능 추가
		}
		/* tts */
		tts=TextToSpeech(this,this)


		/* 운동 리스트 */
		exerciseList=Constants.defaultExerciseList()

		/* 대기 타이머 */
		setupRestView()
		
		/* 리사이클러 뷰 - 운동 리스트 이후에 호출할 것*/
		setupExerciseStatusRecyclerView()
	}

	override fun onBackPressed() {
		customDialogForBackButton()
	}

	private fun customDialogForBackButton() {
		val customDialog=Dialog(this)
		val dialogBinding=DialogCustomBackConfirmationBinding.inflate(layoutInflater)
		customDialog.setContentView(dialogBinding.root)
		customDialog.setCanceledOnTouchOutside(false)

		dialogBinding.btnYes.setOnClickListener {
			this@ExerciseActivity.finish()
			customDialog.dismiss()
		}
		dialogBinding.btnNo.setOnClickListener {
			customDialog.dismiss()
		}

		customDialog.show()
	}

	private fun setupExerciseStatusRecyclerView(){
		binding?.rvExerciseStatus?.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
		exerciseAdapter=ExerciseStatusAdapter(exerciseList!!)
		binding?.rvExerciseStatus?.adapter=exerciseAdapter
	}

	private fun setupRestView(){ //대기 시간

		try {
			val soundURI= Uri.parse("android.resource://com.example.a7minutesworkout/"+R.raw.press_start)
			player=MediaPlayer.create(applicationContext,soundURI)
			player?.isLooping=false
			player?.start()
		}catch (e:Exception){
			e.printStackTrace()
		}

		binding?.flRestView?.visibility = View.VISIBLE
		binding?.tvTitle?.visibility=View.VISIBLE
		binding?.tvUpcomingLabel?.visibility=View.VISIBLE
		binding?.tvUpcomingExerciseName?.visibility=View.VISIBLE

		binding?.tvExerciseName?.visibility=View.INVISIBLE
		binding?.flExerciseView?.visibility = View.INVISIBLE
		binding?.ivImage?.visibility=View.INVISIBLE

		binding?.tvUpcomingExerciseName?.text=exerciseList!![currentExercisePosition+1].getName()


		if(restTimer!=null){
			restTimer?.cancel()
			restProgress=0
		}
		setRestProgressBar()
	}

	private fun setRestProgressBar(){ //대기 progressbar 세팅
		binding?.progressBar?.progress=restProgress
		restTimer=object:CountDownTimer(restTimerDuration*1000,1000) { //전체 초 10초, 인터벌 1초
			override fun onTick(p0: Long) {
				restProgress++
				binding?.progressBar?.progress=10-restProgress
				binding?.tvTimer?.text=(10-restProgress).toString()
			}

			override fun onFinish() {
				currentExercisePosition++ // 운동 교체
				exerciseList!![currentExercisePosition].setIsSelected(true)
				exerciseAdapter!!.notifyDataSetChanged()

				setExerciseView() // 운동 타이머 시작
			}
		}.start()
	}

	private fun setExerciseView(){ //운동 view 세팅
		/* 프레임 교체 */
		binding?.flRestView?.visibility = View.INVISIBLE
		binding?.tvTitle?.visibility=View.INVISIBLE
		binding?.tvUpcomingLabel?.visibility=View.INVISIBLE
		binding?.tvUpcomingExerciseName?.visibility=View.INVISIBLE

		binding?.tvExerciseName?.visibility=View.VISIBLE
		binding?.flExerciseView?.visibility = View.VISIBLE
		binding?.ivImage?.visibility=View.VISIBLE

		binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
		binding?.tvExerciseName?.text=exerciseList!![currentExercisePosition].getName()

		if (exerciseTimer != null) {
			exerciseTimer?.cancel()
			exerciseProgress = 0
		}

		speakOut(exerciseList!![currentExercisePosition].getName())

		setExerciseProgressBar()
	}

	private fun setExerciseProgressBar() { // 운동 progress 세팅
		binding?.progressBarExercise?.progress=exerciseProgress
		exerciseTimer=object:CountDownTimer(exerciseTimerDuration*1000,1000) { //전체 초 30초, 인터벌 1초
			override fun onTick(p0: Long) {
				exerciseProgress++
				binding?.progressBarExercise?.progress=30-exerciseProgress
				binding?.tvTimerExercise?.text=(30-exerciseProgress).toString()
			}

			override fun onFinish() {
				if(currentExercisePosition<exerciseList?.size!!-1){
					exerciseList!![currentExercisePosition].setIsSelected(false)
					exerciseList!![currentExercisePosition].setIsCompleted(true)
					exerciseAdapter!!.notifyDataSetChanged()

					setupRestView() //다시 쉬는 시간
				}else {
					finish()
					val intent = Intent(this@ExerciseActivity,FinishActivity::class.java)
					startActivity(intent)
				}
			}
		}.start()
	}

	override fun onDestroy() {
		super.onDestroy()

		if(restTimer!=null){
			restTimer?.cancel()
			restProgress=0
		}
		if (exerciseTimer != null) {
			exerciseTimer?.cancel()
			exerciseProgress = 0
		}

		if (tts != null) { //tts 해제
			tts?.stop()
			tts?.shutdown()
		}

		if(player!=null){ //오디오 해제
			player!!.stop()
		}

		binding=null
	}

	override fun onInit(status: Int) {
		if (status == TextToSpeech.SUCCESS) {
			val result = tts!!.setLanguage(Locale.US) //언어 설정

			if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "The Language specified is not supported!")
			}
		} else {
			Log.e("TTS", "Initialization Failed!")
		}
	}

	private fun speakOut(text: String) {
		// Queue_flush이면 새로운 tts가 이전 tts를 끊고 시작된다.
		// Queue_add는 뒤에 이어준다.
		tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
	}
}