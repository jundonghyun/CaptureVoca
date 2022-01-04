package com.example.capturevoca

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capturevoca.databinding.ActivityFileListBinding

class FileListViewModel: ViewModel() {
    private var _listData = MutableLiveData<ArrayList<FileData>>()
    val listData: LiveData<ArrayList<FileData>>
    get() = _listData

    private var items = ArrayList<FileData>()

    fun updateList(data: ArrayList<FileData>){
        items = data
        _listData.value = items
    }

    fun deleteList(data: ArrayList<FileData>){
        items = data
        _listData.value = items
    }
}