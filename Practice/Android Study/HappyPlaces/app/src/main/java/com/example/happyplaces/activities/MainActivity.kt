package com.example.happyplaces.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.happyplaces.database.HappyPlaceApp
import com.example.happyplaces.database.HappyPlaceDao
import com.example.happyplaces.databinding.ActivityMainBinding
import com.example.happyplaces.models.HappyPlaceModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
	private var binding: ActivityMainBinding?=null
	private var hpDao: HappyPlaceDao?=null


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding=ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding?.root)

		binding?.fabAddHappyPlace?.setOnClickListener{
			val intent= Intent(this, AddHappyPlaceActivity::class.java)
			startActivity(intent)
		}

		hpDao = (application as HappyPlaceApp).db.hpDao()
	}


	private fun showRecord(){
		if(hpDao!=null){
			lifecycleScope.launch {
				hpDao!!.fetchAllHappyPlaces().collect {
					if(it.isNotEmpty()){
						binding?.rvHappyPlacesList?.visibility= View.VISIBLE
						binding?.tvNoRecordsAvailable?.visibility=View.GONE

					}else{
						binding?.rvHappyPlacesList?.visibility= View.GONE
						binding?.tvNoRecordsAvailable?.visibility=View.VISIBLE
					}

				}
			}
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		binding=null // 꼭 null로 재설정 하여, 메모리 누출을 방지해야 한다.
	}
}