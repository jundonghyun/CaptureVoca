package com.example.capturevoca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capturevoca.databinding.ActivityFileListBinding
import com.google.android.material.snackbar.Snackbar
import java.io.File
import android.app.AlertDialog

import java.text.FieldPosition

class FileListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFileListBinding
    private lateinit var maAdapter: FileListCustomAdapter
    val mainviewModel: FileListViewModel by viewModels() //뷰 모델선언
    private val list = ArrayList<FileData>()
    //private lateinit var mainviewModel: FileListViewModel 1번

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_file_list)

        //mainviewModel = ViewModelProvider(this).get(FileListViewModel::class.java) 2번 위에 뷰모델선언 처럼 사용하거나 1번과 2번처럼 사용하면 됨
        binding.fileListViewModel = mainviewModel

        maAdapter = FileListCustomAdapter()
        binding.fileListRecyclerView.adapter = maAdapter

        maAdapter.setOnItemCLickListener(object: FileListCustomAdapter.OnItemClickListener {
            override fun onClick(v: View, pos: Int) {
                var items = arrayOf("저장된 단어 보기",
                "단어장파일 삭제",
                "단어 추가",
                "단어 삭제 및 수정")

            var builder = AlertDialog.Builder(this@FileListActivity)
            builder.setTitle("단어장파일")
            builder.setItems(items) { _, i ->
                when(i){
                    1 -> {
                        val file = File(this@FileListActivity.filesDir, "/word_data/${list[pos].FileName}")
                        file.delete()
                        list.removeAt(pos) //데이터에서 삭제
                        mainviewModel.deleteList(list) //뷰모델에서 리스트데이터 삭제
                    }
                }
            }
            builder.create()
            builder.show()
            }
        })

        mainviewModel.listData.observe(this, Observer {
            if(it.isEmpty()){
                maAdapter.setData(it)
                binding.fileListEmptyLayout.visibility = View.VISIBLE
            }
            else{
                binding.fileListEmptyLayout.visibility = View.INVISIBLE
                maAdapter.setData(it)
            }
        })

        fileStatus() //폴더나 파일이 존재하는지 확인(액티비티 첫 실행시에만 작동)

        binding.fileListRecyclerView.layoutManager = LinearLayoutManager(this)

        val fab: View = binding.addFileFAB
        fab.setOnClickListener { view ->
            val dialog = CustomDialog(this)
            dialog.showDialog()
            dialog.setOnClickListener(object : CustomDialog.OnDialogClickListener{
                override fun onClicked(name: String) {
                    val file = File(baseContext.filesDir, "/word_data/${name}.txt")
                    if(file.exists()){
                        Snackbar.make(view, "중복된 단어장입니다", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null)
                            .show()
                    }
                    else {
                        file.createNewFile() //파일 생성
                        list.add(FileData(file.name))
                        mainviewModel.updateList(list) //뷰모델에 리스트 업데이트
                        Snackbar.make(view, "단어장이 생성되었습니다", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null)
                            .show()
                    }
                }
            })
        }
    }

    private fun fileStatus(){

        val folder = File(baseContext.filesDir, "/word_data")
        if(!folder.exists()){ //폴더가 존재하지 않으면 폴더생성
            folder.mkdirs()
        }

        var file: Array<File> = folder.listFiles()
        if(file.isEmpty()){
            binding.fileListEmptyLayout.visibility = View.VISIBLE
        }
        else{
            makeRecycler() //리사이클러뷰 만들기
        }
    }

    private fun makeRecycler(){
        binding.fileListEmptyLayout.visibility = View.INVISIBLE
        binding.fileListNotEmptyLayout.visibility = View.VISIBLE


        val folder = File(baseContext.filesDir, "/word_data")
        val file: Array<File> = folder.listFiles()

        for(element in file){
            list.add(FileData(element.name))
        }
        mainviewModel.updateList(list)

    }
}