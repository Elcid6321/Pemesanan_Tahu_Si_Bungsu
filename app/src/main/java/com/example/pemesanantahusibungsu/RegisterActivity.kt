package com.example.pemesanantahusibungsu

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etNama = findViewById<EditText>(R.id.et_nama)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val btnRegister = findViewById<Button>(R.id.btn_register)
        val tvLogin = findViewById<TextView>(R.id.tv_login)

        val pref: SharedPreferences =
            getSharedPreferences("USER_DATA", MODE_PRIVATE)

        btnRegister.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua data harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                pref.edit().apply {
                    putString("nama", nama)
                    putString("email", email)
                    putString("password", password)
                    apply()
                }

                Toast.makeText(this, "Register berhasil, silakan login", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        tvLogin.setOnClickListener {
            finish()
        }
    }
}
