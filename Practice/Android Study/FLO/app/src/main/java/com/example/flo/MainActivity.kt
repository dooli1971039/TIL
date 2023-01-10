package com.example.flo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

	lateinit var binding: ActivityMainBinding //바인딩은 전역변수에 선언
	private var song:Song = Song()
	private var gson: Gson = Gson()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setTheme(R.style.Theme_FLO) //스플래시 이후 다시 원래 테마로 돌아옴
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		initBottomNavigation()

		//val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(),0,68,false,"music_lilac")

		binding.mainPlayerCl.setOnClickListener {
			//1번 방법
			//startActivity(Intent(this,SongActivity::class.java))

			//2번 방법
			val intent = Intent(this,SongActivity::class.java)
			intent.putExtra("title", song.title) //데이터를 넘길 필요가 있을 떄
			intent.putExtra("singer",song.singer)
			intent.putExtra("second",song.second)
			intent.putExtra("playTime",song.playTime)
			intent.putExtra("isPlaying",song.isPlaying)
			intent.putExtra("music",song.music)
			startActivity(intent)
		}


	}


	private fun initBottomNavigation(){
		supportFragmentManager.beginTransaction()
			.replace(R.id.main_frm, HomeFragment()) //이게 기본
			.commitAllowingStateLoss()

		binding.mainBnv.setOnItemSelectedListener{ item ->
			when (item.itemId) {
				R.id.homeFragment -> {
					supportFragmentManager.beginTransaction()
						.replace(R.id.main_frm, HomeFragment())
						.commitAllowingStateLoss()
					return@setOnItemSelectedListener true
				}

				R.id.lookFragment -> {
					supportFragmentManager.beginTransaction()
						.replace(R.id.main_frm, LookFragment())
						.commitAllowingStateLoss()
					return@setOnItemSelectedListener true
				}
				R.id.searchFragment -> {
					supportFragmentManager.beginTransaction()
						.replace(R.id.main_frm, SearchFragment())
						.commitAllowingStateLoss()
					return@setOnItemSelectedListener true
				}
				R.id.lockerFragment -> {
					supportFragmentManager.beginTransaction()
						.replace(R.id.main_frm, LockerFragment())
						.commitAllowingStateLoss()
					return@setOnItemSelectedListener true
				}
			}
			false
		}
	}

	override fun onStart() { //액티비티 "전환"시 onStart부터 시작하게 된다.
		super.onStart()
		val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val songJson = sharedPreferences.getString("songData", null)

        song = if(songJson == null){
            Song("라일락", "아이유(IU)", 0,60, false, "music_lilac")
        } else {
            gson.fromJson(songJson, Song::class.java)
        }

		setMiniPlayer(song)
	}

	private fun setMiniPlayer(song : Song){
		binding.mainMiniplayerTitleTv.text = song.title
		binding.mainMiniplayerSingerTv.text = song.singer
		binding.mainMiniplayerProgressSb.progress = (song.second*100000)/song.playTime
	}


}