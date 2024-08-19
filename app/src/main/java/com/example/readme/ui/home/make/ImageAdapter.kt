package com.example.readme.ui.home.make

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.readme.R
import com.google.android.material.imageview.ShapeableImageView

class ImageAdapter(private val imageList: List<Uri>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    var onImageClickListener: ((Uri, ImageView) -> Unit)? = null
    private var selectedPosition: Int = RecyclerView.NO_POSITION // 선택된 아이템의 위치를 저장

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ShapeableImageView = view.findViewById(R.id.addPhoto)
        val checkmark: ImageView = view.findViewById(R.id.checkmark) // 체크마커 아이디 확인 필요
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_item_layout, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val uri = imageList[position]
        holder.imageView.setImageURI(uri)

        // 체크마커 상태 설정
        holder.checkmark.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE

        // 이미지 클릭 리스너 설정
        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = if (selectedPosition == position) {
                RecyclerView.NO_POSITION // 같은 아이템 클릭 시 선택 해제
            } else {
                position
            }
            notifyItemChanged(previousPosition) // 이전 아이템 상태 업데이트
            notifyItemChanged(selectedPosition) // 현재 아이템 상태 업데이트
            onImageClickListener?.invoke(uri, holder.imageView)
        }
    }

    override fun getItemCount(): Int = imageList.size
}
