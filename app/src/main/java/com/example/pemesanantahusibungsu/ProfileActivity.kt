package com.example.pemesanantahusibungsu

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {

    companion object {
        private const val PICK_IMAGE_REQUEST = 100
    }

    private val PREFS_NAME = "user_prefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile) // pastikan ini XML yg kamu kirim

        // ===== INIT VIEW =====
        val imgProfile: ImageView = findViewById(R.id.imgProfile)
        val tvName: TextView = findViewById(R.id.tvName)
        val tvEmail: TextView = findViewById(R.id.tvEmail)
        val tvPhone: TextView = findViewById(R.id.tvPhone)
        val tvBio: TextView = findViewById(R.id.tvBio)
        val tvEditPhoto: LinearLayout = findViewById(R.id.tvEditPhoto)
        val tvLogout: LinearLayout = findViewById(R.id.tvLogout)
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // ===== SET ACTIVE NAV =====
        bottomNav.selectedItemId = R.id.nav_profile

        // ===== AMBIL DATA DARI LOGIN / REGISTER =====
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        tvName.text = prefs.getString("user_name", "Your Name")
        tvEmail.text = prefs.getString("user_email", "email@example.com")
        tvPhone.text = prefs.getString("user_phone", "+62 xxxx")
        tvBio.text = prefs.getString("user_bio", "Bio user...")

        // ===== EDIT NAME =====
        tvName.setOnClickListener {
            showEditDialog("Edit Name", tvName.text.toString()) { newValue ->
                tvName.text = newValue
                prefs.edit().putString("user_name", newValue).apply()
            }
        }

        // ===== EDIT PHONE =====
        tvPhone.setOnClickListener {
            showEditDialog("Edit Phone", tvPhone.text.toString()) { newValue ->
                tvPhone.text = newValue
                prefs.edit().putString("user_phone", newValue).apply()
            }
        }

        // ===== EDIT BIO =====
        tvBio.setOnClickListener {
            showEditDialog("Edit Bio", tvBio.text.toString()) { newValue ->
                tvBio.text = newValue
                prefs.edit().putString("user_bio", newValue).apply()
            }
        }

        // ===== EDIT PHOTO =====
        tvEditPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        // ===== LOGOUT =====
        tvLogout.setOnClickListener {
            prefs.edit().clear().apply() // hapus data login
            Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // ===== BOTTOM NAVIGATION =====
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    true
                }
                R.id.nav_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    true
                }
                R.id.nav_order -> {
                    startActivity(Intent(this, OrderActivity::class.java))
                    true
                }
                R.id.nav_profile -> true
                else -> false
            }
        }
    }

    // ===== DIALOG EDIT =====
    private fun showEditDialog(title: String, currentValue: String, onSave: (String) -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)

        val input = EditText(this)
        input.setText(currentValue)
        input.setSelection(currentValue.length)
        builder.setView(input)

        builder.setPositiveButton("Simpan") { dialog, _ ->
            val newValue = input.text.toString().trim()
            if (newValue.isNotEmpty()) onSave(newValue)
            dialog.dismiss()
        }
        builder.setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    // ===== RESULT IMAGE =====
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            val uri: Uri? = data?.data
            uri?.let {
                findViewById<ImageView>(R.id.imgProfile).setImageURI(it)
                Toast.makeText(this, "Foto berhasil diubah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
