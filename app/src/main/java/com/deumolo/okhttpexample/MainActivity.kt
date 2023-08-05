package com.deumolo.okhttpexample

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var loaderTxt: TextView
    private lateinit var bodyTxt: TextView
    private var loading: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loaderTxt = findViewById<TextView>(R.id.loaderTxt)
        bodyTxt = findViewById<TextView>(R.id.bodyTxt)

        GlobalScope.launch(Dispatchers.Main) {
            val response = httpRequestHandler("https://dummyjson.com/products/")
        }
    }

    private suspend fun httpRequestHandler(url: String): Any {

        var parsedResponse: Any = ""
        loaderTxt.visibility = View.VISIBLE
        bodyTxt.visibility = View.GONE

        withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()
            val response = client.newCall(request).execute()
            parsedResponse = response.body?.string() ?: ""
            }

        loaderTxt.visibility = View.GONE
        bodyTxt.visibility = View.VISIBLE
        println("Response: " + parsedResponse)
        return parsedResponse
    }
}
