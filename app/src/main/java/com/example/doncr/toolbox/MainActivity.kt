package com.example.doncr.toolbox

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import java.io.File

/*
  https://medium.com/@macastiblancot/android-coroutines-getting-rid-of-runonuithread-and-callbacks-cleaner-thread-handling-and-more-234c0a9bd8eb
  okhttp with kotlin
*/
class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun serverConnect(view :View){
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
                            200 -> textView1.text = result.component1()
                            -1 -> toast("Server not responding")
                        }
                    }
                }
    }
    fun serverDownload(view :View){
        val permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
            toast("try again~")
            return
        }
        val tmp = textView1.text
        val downloadTargetFile :String = tmp.subSequence(1, tmp.lastIndex-1).split(",")[0].trim(predicate = { it <= ' ' || it == '\"'})
        Log.d("target file name", "" + downloadTargetFile) // TODO: choose file to download
        val dwnUrl = getString(R.string.server_url)+"/files/0"
        val externalDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).toString()
        Fuel.download(dwnUrl)
                .authenticate(editUsername.text.toString(), editPassword.text.toString())
                .destination { _, _ ->
                    File(externalDir, downloadTargetFile)
                }
                .response { _, _, result ->
                    Log.d("result download", result.toString())
                    if (result is Result.Success)
                        runOnUiThread { toast("Download complete, save to Music") }
                }

    }
}
