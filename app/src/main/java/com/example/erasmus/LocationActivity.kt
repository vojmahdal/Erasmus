package com.example.erasmus

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.erasmus.database.LocationData
import com.example.erasmus.databinding.ActivityLocationBinding
import com.google.firebase.database.FirebaseDatabase

class LocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLocationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
        setValuestToViews()

        binding.updateButton.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("lId").toString(),
                intent.getStringExtra("lName").toString()
            )
        }
    }
    private fun initView(){

    }
    private fun setValuestToViews(){
        binding.TextViewName.text = intent.getStringExtra("lName")
        binding.TextViewCountry.text = intent.getStringExtra("lCountry")
        binding.TextViewCity.text = intent.getStringExtra("lCity")
    }
    private fun openUpdateDialog(
        lId: String,
        lName: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_location_dialog, null)

        mDialog.setView(mDialogView)

        val etLName = mDialogView.findViewById<EditText>(R.id.uploadLocationName)
        val etLCity = mDialogView.findViewById<EditText>(R.id.uploadLocationCity)
        val etLCountry = mDialogView.findViewById<EditText>(R.id.uploadLocationCountry)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.saveButton)


        etLName.setText(intent.getStringExtra("lName").toString())
        etLCity.setText(intent.getStringExtra("lCity").toString())
        etLCountry.setText(intent.getStringExtra("lCountry").toString())

        mDialog.setTitle("Updating $lName")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateLData(
                lId,
                etLName.text.toString(),
                etLCity.text.toString(),
                etLCountry.text.toString()
            )

            Toast.makeText(applicationContext, "Location Data Updated", Toast.LENGTH_SHORT).show()

            //setting updated data to textviews
            binding.TextViewName.text = etLName.text.toString()
            binding.TextViewCity.text = etLCity.text.toString()
            binding.TextViewCountry.text = etLCountry.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateLData(
        id: String,
        name: String,
        city: String,
        country: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Location Information").child(id)
        val lInfo = LocationData(id, name, country, city)
        dbRef.setValue(lInfo)
    }
}