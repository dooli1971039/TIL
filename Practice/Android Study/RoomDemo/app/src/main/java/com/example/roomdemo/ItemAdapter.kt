package com.example.roomdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.databinding.ItemsRowBinding

class ItemAdapter( //개별 항목을 처리하는 어댑터 클래스
	private val items: ArrayList<EmployeeEntity>,
	private val updateListener: (id: Int) -> Unit,
	private val deleteListener: (id: Int) -> Unit
) :
	RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(ItemsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val context = holder.itemView.context
		val item = items[position]

		holder.tvName.text = item.name
		holder.tvEmail.text = item.email

		if (position % 2 == 0) {
			holder.llMain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.colorLightGray))
		} else {
			holder.llMain.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
		}

		holder.ivEdit.setOnClickListener {
			updateListener.invoke(item.id) //invoke?
		}

		holder.ivDelete.setOnClickListener {
			deleteListener.invoke(item.id)
		}
	}

	override fun getItemCount(): Int {
		return items.size
	}

	class ViewHolder(binding: ItemsRowBinding) : RecyclerView.ViewHolder(binding.root) {
		val llMain = binding.llMain
		val tvName = binding.tvName
		val tvEmail = binding.tvEmail
		val ivEdit = binding.ivEdit
		val ivDelete = binding.ivDelete
	}
}