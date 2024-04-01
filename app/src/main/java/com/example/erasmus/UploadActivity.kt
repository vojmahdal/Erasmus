package com.example.erasmus

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.erasmus.database.LocationData
import com.example.erasmus.databinding.ActivityUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.saveButton.setOnClickListener {
            //push user, who made it to database
            val locationName = binding.uploadLocationName.text.toString()
            val locationCountry = binding.uploadLocationCountry.text.toString()
            val locationCity = binding.uploadLocationCity.text.toString()
            val locationGps = binding.uploadLocationGPS.text.toString()

            databaseReference = FirebaseDatabase.getInstance().getReference("Location Information")
            val locationData = LocationData(locationName, locationCountry, locationCity, locationGps, createdBy = null)
            databaseReference.child(locationName).setValue(locationData).addOnSuccessListener {
                binding.uploadLocationName.text.clear()
                binding.uploadLocationCountry.text.clear()
                binding.uploadLocationCity.text.clear()
                binding.uploadLocationGPS.text.clear()
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@UploadActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}