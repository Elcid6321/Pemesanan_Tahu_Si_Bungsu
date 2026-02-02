package com.example.pemesanantahusibungsu

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {

    private lateinit var imgProfile: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvBio: TextView
    private lateinit var pref: android.content.SharedPreferences

    private val PICK_IMAGE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        imgProfile = findViewById(R.id.imgProfile)
        tvName = findViewById(R.id.tvName)
        tvEmail = findViewById(R.id.tvEmail)
        tvPhone = findViewById(R.id.tvPhone)
        tvBio = findViewById(R.id.tvBio)

        val tvEditPhoto: LinearLayout = findViewById(R.id.tvEditPhoto)
        val tvLogout: LinearLayout = findViewById(R.id.tvLogout)
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)

        pref = getSharedPreferences("USER_DATA", MODE_PRIVATE)

        // LOAD DATA
        loadProfile()

        // EDIT NAME
        tvName.setOnClickListener {
            showEditDialog("Nama", "nama", tvName)
        }

        // EDIT PHONE
        tvPhone.setOnClickListener {
            showEditDialog("Nomor HP", "phone", tvPhone)
        }

        // EDIT BIO
        tvBio.setOnClickListener {
            showEditDialog("Bio", "bio", tvBio)
        }

        // EDIT PHOTO
        tvEditPhoto.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(intent, PICK_IMAGE)
        }

        // LOGOUT
        tvLogout.setOnClickListener {
            pref.edit().clear().apply()
            Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // BOTTOM NAV
        bottomNav.selectedItemId = R.id.nav_profile
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
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

    private fun loadProfile() {
        tvName.text = pref.getString("nama", "Your Name")
        tvEmail.text = pref.getString("email", "email@example.com")
        tvPhone.text = pref.getString("phone", "+62 xxxx")
        tvBio.text = pref.getString("bio", "Bio user...")

        val imgUri = pref.getString("photo", null)
        if (!imgUri.isNullOrEmpty()) {
            imgProfile.setImageURI(Uri.parse(imgUri))
        }
    }

    private fun showEditDialog(title: String, key: String, target: TextView) {
        val input = EditText(this)
        input.setText(target.text.toString())

        AlertDialog.Builder(this)
            .setTitle("Edit $title")
            .setView(input)
            .setPositiveButton("Simpan") { _, _ ->
                val value = input.text.toString()
                target.text = value
                pref.edit().putString(key, value).apply()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            val uri = data?.data
            if (uri != null) {
                imgProfile.setImageURI(uri)
                pref.edit().putString("photo", uri.toString()).apply()
            }
        }
    }
}
