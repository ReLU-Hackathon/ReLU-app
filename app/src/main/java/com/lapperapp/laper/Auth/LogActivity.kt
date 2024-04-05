package com.lapperapp.laper.Auth

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.laperapp.laper.Data.LoginModel
import com.laperapp.laper.ResponseBodyApi
import com.lapperapp.laper.MainActivity
import com.lapperapp.laper.R

class LogActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var submitBtn: Button
    private lateinit var siginup: TextView
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.login_user_email)
        password = findViewById(R.id.login_user_password)
        submitBtn = findViewById(R.id.login_btn)
        siginup = findViewById(R.id.sign_up_btn)


        submitBtn.setOnClickListener {
            val user = LoginModel(email.text.toString().trim(), password.text.toString().trim())
            ResponseBodyApi.logInResponseBody(user, onResponse = { token ->
                if (token != null) {
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("token", token)
                    editor.apply()
                    Toast.makeText(baseContext, "welcome back", Toast.LENGTH_SHORT).show()
                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                }
            }, onFailure = { t ->
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            })
        }

        siginup.setOnClickListener {
            val intent = Intent(baseContext, RegActivity::class.java)
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