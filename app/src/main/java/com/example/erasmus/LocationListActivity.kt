package com.example.erasmus

import android.content.Intent
import android.os.Bundle
import android.view.View
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
        firebaseDatabase = FirebaseDatabase.getInstance()
        locationRecyclerView = findViewById(R.id.locationList)


        locationRecyclerView.layoutManager = LinearLayoutManager(this)
        locationRecyclerView.setHasFixedSize(true)

        locationArrayList = arrayListOf<LocationData>()
        getLocationData()
    }
    private fun getLocationData(){
        locationRecyclerView.visibility = View.GONE

        databaseReference = firebaseDatabase.getReference("Location Information")
        databaseReference.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                locationArrayList.clear()

                if (snapshot.exists()){
                    for(locationSnapshot in snapshot.children){

                        val location = locationSnapshot.getValue(LocationData::class.java)
                        locationArrayList.add(location!!)

                    }
                    val mAdapter = Adapter(locationArrayList)
                    locationRecyclerView.adapter = mAdapter

                    locationRecyclerView.visibility = View.VISIBLE

                    mAdapter.setOnItemClickListener(object : Adapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@LocationListActivity, LocationActivity::class.java)

                            //put extras
                            intent.putExtra("lId",locationArrayList[position].locationId)
                            intent.putExtra("lName",locationArrayList[position].locationName)
                            intent.putExtra("lCountry",locationArrayList[position].locationCountry)
                            intent.putExtra("lCity",locationArrayList[position].locationCity)
                            intent.putExtra("lGps",locationArrayList[position].locationGps)
                            intent.putExtra("lCreatedBy",locationArrayList[position].createdBy)
                            startActivity(intent)
                        }

                    })
                }
            }



            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@LocationListActivity, "Database Error: ${databaseError.message}", Toast.LENGTH_LONG).show()

            }



        })
    }
}