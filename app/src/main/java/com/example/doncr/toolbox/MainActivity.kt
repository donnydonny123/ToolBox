package com.example.doncr.toolbox

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.JsonReader
import android.util.Log
import android.view.View
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.io.File
import java.io.StringReader

/*
  https://medium.com/@macastiblancot/android-coroutines-getting-rid-of-runonuithread-and-callbacks-cleaner-thread-handling-and-more-234c0a9bd8eb
  okhttp with kotlin
*/
class MainActivity : AppCompatActivity(){
    var listString: String? = null
    var listOfFiles = List(0, {RemoteFileInfo("name")})
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun serverConnect(view :View){
        toast("connecting...")
        (getString(R.string.server_url)+"/files")
                .httpGet()
                .authenticate(editUsername.text.toString(), editPassword.text.toString())
                .timeout(timeout = 3000)
                .responseString { _: Request, response: Response, result: Result<String, FuelError> ->
                    Log.d("response", response.toString())
                    Log.d("result", result.component1()?:"")
                    runOnUiThread {
                        when (response.statusCode){
                            401 -> longToast("wrong username or password")
                            200 -> {
                                listString = result.component1()
                                textView1.append("connect result:" +result.component1())
                            }
                            -1 -> toast("Server not responding")
                        }
                    }
                }
    }
    fun downloadFileChoose(view :View){
        // check if connected
        if (listString == null) serverConnect(view)
        val files = listString?:return

        //grant storage permission
        val permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
            toast("try again~")
            return
        }
        // parse string to list by gson
        val typeToken = object : TypeToken<List<RemoteFileInfo>>(){}.type
        listOfFiles = Gson().fromJson(files, typeToken)

        //start activity to choose files
        val intent = Intent(this, ChooseFileActivity::class.java)
        intent.putExtra("files", files)
        startActivityForResult(intent, 1)

    }
    private fun serverDownload(item :RemoteFileInfo){
        val dwnUrl = getString(R.string.server_url)+"/files/"+item.index.toString()
        val externalDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).toString()
        Fuel.download(dwnUrl)
                .authenticate(editUsername.text.toString(), editPassword.text.toString())
                .destination { _, _ ->
                    File(externalDir, item.fileName)
                }
                .response { _, _, result ->
                    Log.d("result download", result.toString())
                    if (result is Result.Success)
                        runOnUiThread { toast("Download " +item.fileName+" complete, save to Music") }
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode){
            1-> {
                val datatmp = data?:return
                val items = datatmp.getIntArrayExtra("files")
                for (i in items){
                    serverDownload(listOfFiles[i])
                }
            }
        }
    }
}
