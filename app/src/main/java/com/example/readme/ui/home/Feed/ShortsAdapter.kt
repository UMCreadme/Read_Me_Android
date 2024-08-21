package com.example.readme.ui.home.Feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.readme.data.entities.inithome.ShortsInfo
import com.example.readme.databinding.ShortsItemBinding

class ShortsAdapter(var list: ArrayList<ShortsInfo>) : RecyclerView.Adapter<ShortsAdapter.ShortsHolder>() {

    init {
        setHasStableIds(true)
    }

    // ViewHolder 정의
    inner class ShortsHolder(val binding: ShortsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val img = binding.shortsImg

        fun bind(shorts: ShortsInfo) {
            // Glide를 사용하여 이미지 로드
            Glide.with(binding.root.context)
                .load(shorts.shortsImg) // URL 또는 리소스 ID
                .into(img)

            binding.shortsPhrase.text = shorts.phrase

            binding.root.setOnClickListener {
                myItemClickListener?.onItemClick(shorts) // `ShortsInfo` 객체를 전달
            }
        }
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
        holder.bind(shorts)
    }

    override fun getItemId(position: Int): Long {
        return list[position].shorts_id.hashCode().toLong() // 각 아이템의 고유 ID 반환
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
    }

    // 데이터를 업데이트하는 함수
    fun updateData(newList: List<ShortsInfo>) {
        val diffCallback = ShortsDiffCallback(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    // DiffUtil.Callback 구현
    class ShortsDiffCallback(
        private val oldList: List<ShortsInfo>,
        private val newList: List<ShortsInfo>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].shorts_id == newList[newItemPosition].shorts_id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
