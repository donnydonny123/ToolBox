package com.example.doncr.toolbox

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_choose_file.*

class ChooseFileActivity : AppCompatActivity() {
    var listOfFiles :List<RemoteFileInfo> = List(0, {RemoteFileInfo("")})
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_file)
        //prepare data
        val fileString = intent.getStringExtra("files")
        val typeToken = object : TypeToken<List<RemoteFileInfo>>(){}.type
        listOfFiles = Gson().fromJson(fileString, typeToken)
        //prepare adapter
        val listAdapter = FileChoosingAdapter(this, R.layout.file_item, listOfFiles.toMutableList())
        itemsView.adapter = listAdapter

    }

    fun startDownload(view : View){
        val sendFiles = listOfFiles.filter { it.isChosen }
        val intArray = IntArray(sendFiles.size, {sendFiles[it].index})
        val intent = Intent()
        intent.putExtra("files", intArray)
        Log.d("intent back", intArray.toString())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
