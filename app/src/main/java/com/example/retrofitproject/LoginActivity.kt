package com.example.retrofitproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(TextView(this))

        val token = getToken()

        if (token == "" && intent.data == null)
            getAuthorizeCode()

        else if (token != "" && !intent?.data.toString().startsWith(RetrofitInterface.redirectURL))
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))

        else
            getAccessToken()

        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private fun getAccessToken(){
        scope.launch {
            val uri = intent.data
            if (uri != null && uri.toString().startsWith(RetrofitInterface.redirectURL)) {
                try {
                    val response =
                        client.getAccessTokenAsync(
                            RetrofitInterface.clientId,
                            RetrofitInterface.clientSecret,
                            uri.getQueryParameter("code")!!
                        )

                    if (response.isSuccessful) {
                        val accessToken = response.body()
                        getSharedPreferences("token", Context.MODE_PRIVATE)
                            .edit()
                            .putString("token", accessToken!!.accessToken)
                            .apply()

                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))

                    } else
                        processResponseCode(response.code())

                } catch (t: Throwable) {
                    Log.i("logAuthProblem", t.message)
                }
            }
        }
    }
}