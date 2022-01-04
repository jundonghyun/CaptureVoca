package com.example.capturevoca

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.capturevoca.databinding.ItemRecyclerFilelistBinding
import com.google.android.material.snackbar.Snackbar
import java.io.File

class FileListCustomAdapter: RecyclerView.Adapter<Holder>() {
    var listData = ArrayList<FileData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder { //한 화면에 그려지는 아이템 개수만큼 레이아웃 생성
        val binding = ItemRecyclerFilelistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) { //생성된 아이템 레이아웃에 값 입력 후 목록에 출력
        holder.bind(listData[position])

        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int { //목록에 보여줄 아이템의 개수
        return listData.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: ArrayList<FileData>){
        listData = data
        notifyDataSetChanged()
    }

    interface OnItemClickListener{ //액티비티에서 아이템 클릭했을때 동작하는 리스너 생성
        fun onClick(v: View, pos: Int){}
    }
    fun setOnItemCLickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }
    private lateinit var itemClickListener: OnItemClickListener
}

class Holder(private val binding: ItemRecyclerFilelistBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: FileData){
        binding.list = data
    }
    init {
        itemView.setOnClickListener{

        }
    }
}