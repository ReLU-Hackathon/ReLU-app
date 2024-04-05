package com.lapperapp.laper.Auth

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.laperapp.laper.Data.LoginResponse
import com.laperapp.laper.api.RetrofitClient
import com.lapperapp.laper.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class GoogleAuthActivity : AppCompatActivity() {
    private lateinit var authBtn:Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_auth)
        authBtn = findViewById(R.id.auth_btn)


        authBtn.setOnClickListener {
            auth()
        }


    }


    fun auth(){
        val jsonapi = RetrofitClient.getClient()
        jsonapi.authGoogle().enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(baseContext,"successful",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext,"unsuccessful : "+response.body() ,Toast.LENGTH_SHORT).show()
                    Log.d("rohit",response.body().toString())
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(baseContext,t.message,Toast.LENGTH_LONG).show()
                Log.d("test",t.message.toString())
            }
        })

    }

}