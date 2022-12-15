package com.example.myquizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

//상속을 2개 받음
class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
	private var mUserName:String?=null

	private var mCorrectAnswers:Int=0
	private var mCurrentPosition:Int=1
	private var mQuestionsList:ArrayList<Question>?=null
	private var mSelectedOptionPosition:Int=0

	private var tvQuestion: TextView? = null
	private var ivImage: ImageView? = null

	private var progressBar: ProgressBar? = null
	private var tvProgress: TextView? = null

	private var tvOptionOne: TextView? = null
	private var tvOptionTwo: TextView? = null
	private var tvOptionThree: TextView? = null
	private var tvOptionFour: TextView? = null

	private var btnSubmit:Button?=null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_quiz_questions)

		mUserName=intent.getStringExtra(Constants.USER_NAME)

		tvQuestion = findViewById(R.id.tv_question)
		ivImage = findViewById(R.id.iv_image)
		progressBar = findViewById(R.id.progressBar)
		tvProgress = findViewById(R.id.tv_progress)
		tvOptionOne = findViewById(R.id.tv_option_one)
		tvOptionTwo = findViewById(R.id.tv_option_two)
		tvOptionThree = findViewById(R.id.tv_option_three)
		tvOptionFour = findViewById(R.id.tv_option_four)
		btnSubmit=findViewById(R.id.btn_submit)

		// (this로 설정한 클래스 == QuizQuestionActivity)를 onClickListener로 설정하는 것
		// onClickListener를 사용해 override 메소드인 onClick 메소드를 사용해서 재정의하는 것
		tvOptionOne?.setOnClickListener(this)
		tvOptionTwo?.setOnClickListener(this)
		tvOptionThree?.setOnClickListener(this)
		tvOptionFour?.setOnClickListener(this)
		btnSubmit?.setOnClickListener(this)

		mQuestionsList = Constants.getQuestions()
		setQuestion()
	}

	private fun setQuestion() {
		defaultOptionView() // 다음 질문으로 넘어갔을 때도 초기화할 것
		val question: Question = mQuestionsList!![mCurrentPosition - 1] //nullable이지만 변수가 비워지지 않을 것이기에 force-unwrapping함

		progressBar?.progress =mCurrentPosition
		tvProgress?.text = "$mCurrentPosition/${progressBar?.max}"
		tvQuestion?.text = question.question
		ivImage?.setImageResource(question.image)
		tvOptionOne?.text = question.optionOne
		tvOptionTwo?.text = question.optionTwo
		tvOptionThree?.text = question.optionThree
		tvOptionFour?.text = question.optionFour

		if(mCurrentPosition==mQuestionsList!!.size){
			btnSubmit?.text="FINISH"
		}else{
			btnSubmit?.text="SUBMIT"
		}
	}

	private fun defaultOptionView(){
		val options=ArrayList<TextView>()
		tvOptionOne?.let{
			options.add(0,it)
		}
		tvOptionTwo?.let{
			options.add(1,it)
		}
		tvOptionThree?.let{
			options.add(2,it)
		}
		tvOptionFour?.let{
			options.add(3,it)
		}

		for(option in options){
			option.setTextColor(Color.parseColor("#7a8089"))
			option.typeface= Typeface.DEFAULT //typeface : 폰트
			option.background=ContextCompat.getDrawable(
				this,
				R.drawable.default_option_border_bg
			)
		}
	}

	private fun selectedOptionView(tv:TextView,selectedOptionNum:Int){
		defaultOptionView() // 뭔가를 선택할때마다 초기화하고 선택해야 함.(제출할때 초기화하면 여러개 골랐을 때 문제 생김)
		mSelectedOptionPosition=selectedOptionNum
		tv.setTextColor(Color.parseColor("#363a43"))
		tv.setTypeface(tv.typeface,Typeface.BOLD)
		tv.background=ContextCompat.getDrawable(
			this,
			R.drawable.selected_option_border_bg
		)
	}

	override fun onClick(view: View?) {
		when(view?.id){
			R.id.tv_option_one->{
				tvOptionOne?.let {
					selectedOptionView(it,1)
				}
			}
			R.id.tv_option_two->{
				tvOptionTwo?.let {
					selectedOptionView(it,2)
				}
			}
			R.id.tv_option_three->{
				tvOptionThree?.let {
					selectedOptionView(it,3)
				}
			}
			R.id.tv_option_four->{
				tvOptionFour?.let {
					selectedOptionView(it,4)
				}
			}

			R.id.btn_submit->{
				if(mSelectedOptionPosition==0){
					mCurrentPosition++

					if(mCurrentPosition<=mQuestionsList!!.size){
						setQuestion()
					}else{ // 퀴즈 끝났을 때
						var intent=Intent(this, FinishActivity::class.java)
						intent.putExtra(Constants.USER_NAME,mUserName)
						intent.putExtra(Constants.CORRECT_ANSWERS,mCorrectAnswers)
						intent.putExtra(Constants.TOTAL_QUESTIONS,mQuestionsList?.size)
						startActivity(intent)
						finish()
					}

				}else{
					val question =mQuestionsList?.get(mCurrentPosition-1)
					if(question!!.correctAnswer != mSelectedOptionPosition){
						answerView(mSelectedOptionPosition,R.drawable.wrong_option_border_bg)
					}else{
						mCorrectAnswers++
					}
					answerView(question.correctAnswer,R.drawable.correct_option_border_bg) //맞든 틀리든 정답은 초록색으로 보여야 함

					if(mCurrentPosition==mQuestionsList!!.size){ //마지막이면
						btnSubmit?.text="FINISH"
					}else{
						btnSubmit?.text="GO TO NEXT QUESTION"
					}
					mSelectedOptionPosition=0
				}

			}
		}
	}

	private fun answerView(answer:Int, drawbleView:Int){
		when(answer){
			1->{
				tvOptionOne?.background=ContextCompat.getDrawable(this, drawbleView)
			}
			2->{
				tvOptionTwo?.background=ContextCompat.getDrawable(this, drawbleView)
			}
			3->{
				tvOptionThree?.background=ContextCompat.getDrawable(this, drawbleView)
			}
			4->{
				tvOptionFour?.background=ContextCompat.getDrawable(this, drawbleView)
			}
		}

	}
}