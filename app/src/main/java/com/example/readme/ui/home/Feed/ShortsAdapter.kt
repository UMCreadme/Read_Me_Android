package com.example.readme.ui.home.Feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.readme.databinding.ShortsItemBinding
import com.example.readme.data.entities.inithome.ShortsInfo

class ShortsAdapter(var list: ArrayList<ShortsInfo>) : RecyclerView.Adapter<ShortsAdapter.ShortsHolder>() {

    // ViewHolder 정의
    inner class ShortsHolder(val binding: ShortsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val img = binding.recommandshorts

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortsHolder {
        return ShortsHolder(
            ShortsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ShortsHolder, position: Int) {
        val shorts = list[position]
        // Glide를 사용하여 이미지 로드
        Glide.with(holder.itemView.context)
            .load(shorts.shortsImg) // URL 또는 리소스 ID
            .into(holder.img)

        holder.itemView.setOnClickListener {
            myItemClickListener?.onItemClick(shorts) // `ShortsInfo` 객체를 전달
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    // 클릭 리스너 설정
    private var myItemClickListener: MyItemClickListener? = null

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    // 클릭 리스너 인터페이스 정의
    interface MyItemClickListener {
        fun onItemClick(shorts: ShortsInfo) // `ShortsInfo` 객체를 전달
        //fun onDeleteClick(position: Int)
    }

    // 데이터를 업데이트하는 함수
    fun updateData(newList: List<ShortsInfo>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}
