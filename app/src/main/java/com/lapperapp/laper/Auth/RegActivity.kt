package com.lapperapp.laper.Auth

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.laperapp.laper.Data.SignUpModel
import com.laperapp.laper.ResponseBodyApi
import com.lapperapp.laper.MainActivity
import com.lapperapp.laper.R

class RegActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var login: TextView
    private lateinit var email: EditText
    private lateinit var name: EditText
    private lateinit var password: EditText
    private lateinit var submitBtn: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        textView = findViewById(R.id.user_text)
        login = findViewById(R.id.sign_in_btn)
        email = findViewById(R.id.user_email)
        name = findViewById(R.id.user_name)
        password = findViewById(R.id.user_password)
        submitBtn = findViewById(R.id.submit_btn)


        submitBtn.setOnClickListener {

            val userEmail = email.text.toString().trim()
            val userPassword = password.text.toString().trim()
            val userName = name.text.toString().trim()
            val user = SignUpModel(userEmail, userName, userName, userPassword)

            ResponseBodyApi.signUpResponseBody(user, onResponse = { msg ->
                if (msg != null) {
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                }
            },
                onFailure = { t ->
                    Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
                })
        }

        login.setOnClickListener {
            val intent = Intent(baseContext, LogActivity::class.java)
            startActivity(intent)
        }

    }


    override fun onStart() {
        super.onStart()
        sharedPreferences = getSharedPreferences("credential", MODE_PRIVATE)
        if (sharedPreferences.getString("token", null) != null) {
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
        }

    }

}