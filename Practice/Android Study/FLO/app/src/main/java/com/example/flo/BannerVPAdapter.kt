package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

//어댑터는 하나의 클래스를 상속받아야 한다.
class BannerVPAdapter(fragment:Fragment) :FragmentStateAdapter(fragment) {

	//여러 fragment를 담아두기 위함
	private val fragmentlist : ArrayList<Fragment> = ArrayList()

	override fun getItemCount(): Int = fragmentlist.size

	//fragmentlist 안에 있는 아이템들, 즉 fragment들을 초기화 시켜주는 함수이다.
	override fun createFragment(position: Int): Fragment = fragmentlist[position]

	fun addFragment(fragment: Fragment){
		fragmentlist.add(fragment)
		notifyItemInserted(fragmentlist.size-1)//리스트에 새로운 값이 추가됐을 때, viewpager한테 새로운 값이 추가됐음을 알리는 것
		//새로운 값이 리스트에 추가되는 위치를 알리는 것이다
	}
}