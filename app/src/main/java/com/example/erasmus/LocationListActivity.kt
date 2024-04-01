package com.example.erasmus

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.erasmus.database.LocationData
import com.example.erasmus.utils.Adapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LocationListActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase

    private lateinit var locationRecyclerView: RecyclerView
    private lateinit var locationArrayList : ArrayList<LocationData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_location_list)

        locationRecyclerView = findViewById(R.id.locationList)
        locationRecyclerView.layoutManager = LinearLayoutManager(this)
        locationRecyclerView.setHasFixedSize(true)

        locationArrayList = arrayListOf<LocationData>()
        getLocationData()
    }
    private fun getLocationData(){
        databaseReference = firebaseDatabase.getReference("Location Information")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for(locationSnapshot in snapshot.children){

                        val location = locationSnapshot.getValue(LocationData::class.java)
                        locationArrayList.add(location!!)
                    }
                    locationRecyclerView.adapter = Adapter(locationArrayList)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@LocationListActivity, "Database Error: ${databaseError.message}", Toast.LENGTH_LONG).show()

            }

        })
    }
}