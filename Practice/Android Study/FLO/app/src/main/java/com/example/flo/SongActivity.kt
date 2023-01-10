package com.example.flo

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson
import java.util.*

class SongActivity : AppCompatActivity() {
	//소괄호 : 클래스를 다른 클래스로 상속을 진행할 때는 소괄호를 넣어줘야 한다.

	//전역 변수
	lateinit var binding : ActivitySongBinding
	lateinit var song : Song
	lateinit var timer: Timer
	private var mediaPlayer: MediaPlayer? = null
	private var gson: Gson = Gson()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivitySongBinding.inflate(layoutInflater)
		setContentView(binding.root)

		initSong()
		setPlayer(song)

		binding.songDownIb.setOnClickListener {
			finish()
		}

		binding.songMiniplayerIv.setOnClickListener {
			setPlayerStatus(true)
		}

		binding.songPauseIv.setOnClickListener {
			setPlayerStatus(false)
		}

	}

	// 전달 받은 데이터로 초기화
	private fun initSong(){
		// intent로 데이터를 전달받는 부분. 받을 수도 있고 안 받을 수도 있으니 if문
		if(intent.hasExtra("title") && intent.hasExtra("singer")){
			song = Song(
				intent.getStringExtra("title")!!,
				intent.getStringExtra("singer")!!,
				intent.getIntExtra("second",0),
				intent.getIntExtra("playTime",0),
				intent.getBooleanExtra("isPlaying",false),
				intent.getStringExtra("music")!!
			)
		}
		startTimer()
	}

	//화묜에 문구 세팅
	private fun setPlayer(song: Song){
		binding.songMusicTitleTv.text = intent.getStringExtra("title")!!
		binding.songSingerNameTv.text = intent.getStringExtra("singer")!!
		binding.songStartTimeTv.text = String.format("%02d:%02d",song.second / 60, song.second % 60)
		binding.songEndTimeTv.text = String.format("%02d:%02d",song.playTime / 60, song.playTime % 60)
		binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

		val music=resources.getIdentifier(song.music,"raw",this.packageName)
		mediaPlayer=MediaPlayer.create(this,music)

		setPlayerStatus(song.isPlaying)
	}

	//화면 세팅 변환
	fun setPlayerStatus (isPlaying : Boolean){
		song.isPlaying = isPlaying
		timer.isPlaying = isPlaying

		if(isPlaying){ //음악 재생
			binding.songMiniplayerIv.visibility = View.GONE
			binding.songPauseIv.visibility = View.VISIBLE
			mediaPlayer?.start()
		} else { //음악 정지
			binding.songMiniplayerIv.visibility = View.VISIBLE
			binding.songPauseIv.visibility = View.GONE
			if(mediaPlayer?.isPlaying==true){ //진행 되지 않는데 pause하면 문제생김
				mediaPlayer?.pause()
			}
		}
	}


	private fun startTimer(){
		timer = Timer(song.playTime,song.isPlaying)
		timer.start()
	}

	//inner를 안 쓰면 내부 클래스로 인식을 안 함.
	//바인딩을 사용해야하므로, SongActivity의 변수에 접근할 수 있어야 함. => inner
	inner class Timer(private val playTime: Int,var isPlaying: Boolean = true):Thread(){ //총 몇초인지, 진행중인지
		private var second : Int = 0
		private var mills: Float = 0f

		override fun run() {
			super.run()
			try {
				while (true){ //타이머는 계속 진행이 되어야 하니까
					if (second >= playTime){
						break //노래 시간이 끝나면 종료
					}

					if (isPlaying){
						sleep(50)
						mills += 50

						runOnUiThread { //뷰를 렌더링 한다
							binding.songProgressSb.progress = ((mills / playTime)*100).toInt()
						}

						if (mills % 1000 == 0f){
							runOnUiThread {
								binding.songStartTimeTv.text = String.format("%02d:%02d",second / 60, second % 60)
							}
							second++
						}
					}
				}
			}catch (e: InterruptedException){
				Log.d("Song","쓰레드가 죽었습니다. ${e.message}")
			}
		}
	}

	// 사용자가 포커스를 잃었을 때 음악이 중지
	override fun onPause() {
		super.onPause()
		setPlayerStatus(false) //음악 중지
		song.second=((binding.songProgressSb.progress*song.playTime)/100)/1000

		val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE) //내부 저장소에 데이터를 저장할 수 있게 해주는 것
		//MODE_PRIVATE 이면 다른 앱에서 사용 불가능
		val editor=sharedPreferences.edit() //에디터
		//editor.putString("title",song.title) //이렇게 하나 하나 넣거나, json으로 넣거나
		val songJson=gson.toJson(song)
		editor.putString("songData",songJson) //일종의 commit
		editor.apply() //apply까지 해줘야 저장된다. (push)

	}

	override fun onDestroy() {
		super.onDestroy()
		timer.interrupt()
		mediaPlayer?.release() //미디어 플레이어가 갖고 있던 리소스 해제
		mediaPlayer=null //미디어 플레이어 해제
	}
}