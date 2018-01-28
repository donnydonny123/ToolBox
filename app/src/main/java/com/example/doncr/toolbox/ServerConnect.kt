package com.example.doncr.toolbox

import com.github.kittinunf.fuel.core.Handler
import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Create for server connect
 */
object ServerConnect {
    fun OKserverGet(): Deferred<String> {
        return async(CommonPool){
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url("http://192.168.0.102:5000/")
                    .header("Content-Type", "application/json")
                    .build()
            val res = client.newCall(request).execute().body() //deal with nullable
            if (res != null) res.string() else "\"empty\""

        }
    }
    fun serverGet(handler: Handler<String>){
        "http://192.168.0.102:5000".httpGet().responseString(handler)
    }
}