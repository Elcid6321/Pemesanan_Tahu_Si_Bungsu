package com.example.pemesanantahusibungsu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {

    private lateinit var imgProfile: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvBio: TextView
    private lateinit var pref: android.content.SharedPreferences

    // Modern image picker
    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imgProfile.setImageURI(it)
                pref.edit().putString("photo", it.toString()).apply()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // ==== INIT VIEW ====
        imgProfile = findViewById(R.id.imgProfile)
        tvName = findViewById(R.id.tvName)
        tvEmail = findViewById(R.id.tvEmail)
        tvPhone = findViewById(R.id.tvPhone)
        tvBio = findViewById(R.id.tvBio)

        val tvEditPhoto: LinearLayout = findViewById(R.id.tvEditPhoto)
        val tvLogout: LinearLayout = findViewById(R.id.tvLogout)
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)

        pref = getSharedPreferences("USER_DATA", MODE_PRIVATE)

        // ==== LOAD DATA DARI SHARED PREFERENCES ====
        loadProfile()

        // ==== EDIT DATA ====
        tvName.setOnClickListener { showEditDialog("Nama", "nama", tvName) }
        tvPhone.setOnClickListener { showEditDialog("Nomor HP", "phone", tvPhone) }
        tvBio.setOnClickListener { showEditDialog("Bio", "bio", tvBio) }

        tvEditPhoto.setOnClickListener {
            imagePicker.launch("image/*")
        }

        // ==== LOGOUT ====
        tvLogout.setOnClickListener {
            pref.edit().clear().apply()
            Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // ==== BOTTOM NAVIGATION ====
        bottomNav.selectedItemId = R.id.nav_profile
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    if (this !is DashboardActivity) {
                        startActivity(Intent(this, DashboardActivity::class.java))
                    }
                    true
                }
                R.id.nav_cart -> {
                    if (this !is CartActivity) {
                        startActivity(Intent(this, CartActivity::class.java))
                    }
                    true
                }
                R.id.nav_order -> {
                    if (this !is OrderActivity) {
                        startActivity(Intent(this, OrderActivity::class.java))
                    }
                    true
                }
                R.id.nav_profile -> true
                else -> false
            }
        }
    }

    private fun loadProfile() {
        tvName.text = pref.getString("nama", "Your Name")
        tvEmail.text = pref.getString("email", "email@example.com")
        tvPhone.text = pref.getString("phone", "+62 xxxx")
        tvBio.text = pref.getString("bio", "Bio user...")

        val imgUri = pref.getString("photo", null)
        if (!imgUri.isNullOrEmpty()) {
            try {
                imgProfile.setImageURI(Uri.parse(imgUri))
            } catch (e: Exception) {
                imgProfile.setImageResource(R.drawable.logo) // fallback jika gagal
            }
        } else {
            imgProfile.setImageResource(R.drawable.logo) // default
        }
    }

    private fun showEditDialog(title: String, key: String, target: TextView) {
        val input = EditText(this)
        input.setText(target.text.toString())
        input.setSingleLine(true)

        AlertDialog.Builder(this)
            .setTitle("Edit $title")
            .setView(input)
            .setPositiveButton("Simpan") { _, _ ->
                val value = input.text.toString().trim()
                target.text = value
                pref.edit().putString(key, value).apply()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
