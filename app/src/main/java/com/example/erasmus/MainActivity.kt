package com.example.erasmus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.erasmus.databinding.ActivityMainBinding
import com.example.erasmus.language.LangPreference
import com.google.firebase.Firebase
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.initialize




class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    lateinit var langPreference: LangPreference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()

      /*  Firebase.initialize(context = this)
        Firebase.appCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance(),
        )*/
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.mainLogout.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }

        binding.mainUpload.setOnClickListener {
            val intent = Intent(this@MainActivity, UploadActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.mainFind.setOnClickListener {
            val intent = Intent(this@MainActivity, LocationListActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.mainProfile.setOnClickListener {
            val intent = Intent(this@MainActivity, ActivityProfile::class.java)
            startActivity(intent)
            finish()
        }
        binding.mainSettings.setOnClickListener {
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        langPreference = LangPreference(newBase!!)
        val lang: String? = langPreference.getLoginCount()
        super.attachBaseContext(MyContextWrapper.wrap(newBase, lang!!))
    }
}