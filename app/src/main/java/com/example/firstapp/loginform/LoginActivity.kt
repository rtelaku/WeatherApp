package com.example.firstapp.loginform

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapp.R
import com.example.firstapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private var userUsername: String? = null
    private var userPassword: String? = null
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)
        binding.logInButton.setOnClickListener(View.OnClickListener { setUpListener() })
    }

    fun setUpListener() {
        userUsername = binding.usernameText.text.toString()
        userPassword = binding.passwordText.text.toString()
        if (userUsername == null || userUsername!!.isEmpty() || userUsername!!.trim { it <= ' ' }
                .isEmpty()) {
            binding.invalidPassword.text = ""
            binding.invalidUsername.text = "Invalid Username"
        } else if (userPassword == null || userPassword!!.isEmpty() || userPassword!!.trim { it <= ' ' }
                .isEmpty() || userPassword!!.length < 3) {
            binding.invalidUsername.text = ""
            binding.invalidPassword.text = "Invalid Password"
        } else {
            binding.invalidUsername.text = ""
            binding.invalidPassword.text  = ""
            val logInIntent = Intent(applicationContext, MainActivity::class.java)
            logInIntent.putExtra("username", userUsername)
            startActivity(logInIntent)
        }
    }
}