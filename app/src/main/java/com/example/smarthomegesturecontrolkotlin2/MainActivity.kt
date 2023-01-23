package com.example.smarthomegesturecontrolkotlin2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private val url = "http://" + "10.0.2.2" + ":" + 5000 + "/"
    private var postBodyString: String? = null
    private var mediaType: MediaType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTurnOnLights = findViewById<Button>(R.id.buttonTurnOnLights)
        btnTurnOnLights.setOnClickListener {
            val intent = Intent(this, ActivityTurnOnLights1::class.java)
            startActivity(intent)
        }


        val connect = findViewById<Button>(R.id.buttonTurnOffLights)
        connect.setOnClickListener(View.OnClickListener {
            postRequest(
                "AK message here: it's working",
                url
            )
        })


    }


    private fun buildRequestBody(msg: String): RequestBody {
        postBodyString = msg
        mediaType = "text/plain; charset=utf-8".toMediaType()
        return postBodyString.toString().toRequestBody(mediaType)
    }


    private fun postRequest(message: String, URL: String) {
        val requestBody = buildRequestBody(message)
        val okHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
            .post(requestBody)
            .url(URL)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "Something went wrong:" + " " + e.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    call.cancel()
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    try {
                        Toast.makeText(
                            this@MainActivity,
                            response.body!!.string(),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }






}