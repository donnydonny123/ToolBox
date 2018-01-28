package com.example.doncr.toolbox

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Handler
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_main.*

/*
  https://medium.com/@macastiblancot/android-coroutines-getting-rid-of-runonuithread-and-callbacks-cleaner-thread-handling-and-more-234c0a9bd8eb
  okhttp with kotlin
*/
class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun serverConnect( view :View){/*
        launch(Android){
            try {
                val result = ServerConnect.serverGet()
                textView1.text = result.await()
            }catch (e :IOException) {
                Toast.makeText(this@MainActivity, "Server not responding", Toast.LENGTH_SHORT).show()
            }

        }*/
        "http://192.168.0.102:5000".httpGet().responseString { _: Request, _: Response, result: Result<String, FuelError> ->
            when (result) {
                is Result.Success -> {
                    runOnUiThread { textView1.text = result.value }
                }
                is Result.Failure -> Toast.makeText(this@MainActivity, "Server not responding", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
