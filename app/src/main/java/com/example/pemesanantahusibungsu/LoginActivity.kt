package com.example.pemesanantahusibungsu

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val tvDaftar = findViewById<TextView>(R.id.tv_daftar)

        val pref: SharedPreferences =
            getSharedPreferences("USER_DATA", MODE_PRIVATE)

        btnLogin.setOnClickListener {
            val inputEmail = etEmail.text.toString()
            val inputPassword = etPassword.text.toString()

            val savedEmail = pref.getString("email", "")
            val savedPassword = pref.getString("password", "")
            val isRegister = pref.getBoolean("isRegister", false)

            if (!isRegister) {
                Toast.makeText(this, "Silakan register terlebih dahulu", Toast.LENGTH_SHORT).show()
            } else if (inputEmail == savedEmail && inputPassword == savedPassword) {
                Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Email atau password salah", Toast.LENGTH_SHORT).show()
            }
        }

        tvDaftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
