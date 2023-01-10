package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentHomeBinding
import com.google.gson.Gson

class HomeFragment : Fragment() {

	lateinit var binding: FragmentHomeBinding
	private var albumDatas = ArrayList<Album>()


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		// fragment 바인딩
		binding = FragmentHomeBinding.inflate(inflater, container, false)

//		binding.homeAlbumImgIv1.setOnClickListener {
//			// fragment 전환 방법 (일종의 패턴이니 알아두자.)
//			(context as MainActivity).supportFragmentManager.beginTransaction()
//				.replace(R.id.main_frm , AlbumFragment())
//				.commitAllowingStateLoss()
//		}

		// 데이터 리스트 생성 더미 데이터 - 원래는 서버에서 데이터를 받아온다
		albumDatas.apply {
			add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
			add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
			add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp))
			add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp2))
			add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp))
			add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp2))
		}

		// 더미데이터랑 Adapter 연결
		val albumRVAdapter = AlbumRVAdapter(albumDatas)
		// 리사이클러뷰에 어댑터를 연결
		binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
		binding.homeTodayMusicAlbumRv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

		albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener{

			override fun onItemClick(album: Album) {
				changeAlbumFragment(album)
			}
//
//			override fun onRemoveAlbum(position: Int) {
//				albumRVAdapter.removeItem(position)
//			}
		})


		// 뷰페이저
		val bannerAdapter = BannerVPAdapter(this)
		bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
		bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
		binding.homeBannerVp.adapter = bannerAdapter
		binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL //뷰페이저가 좌우로 스크롤 될 수 있도록 하는 것

		return binding.root //fragment에서 onCreateView는 반드시 이렇게 반환할 것
	}

	private fun changeAlbumFragment(album: Album) {
		(context as MainActivity).supportFragmentManager.beginTransaction()
			.replace(R.id.main_frm, AlbumFragment().apply {
				arguments = Bundle().apply { //fragment를 전환시 bundle을 통해 데이터를 같이 넘김
					val gson = Gson()
					val albumJson = gson.toJson(album)
					putString("album", albumJson)
				}
			})
			.commitAllowingStateLoss()
	}


}