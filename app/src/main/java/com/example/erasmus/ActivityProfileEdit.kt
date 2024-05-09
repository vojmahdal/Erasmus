package com.example.erasmus

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.erasmus.database.ProfileData
import com.example.erasmus.databinding.ActivityProfileEditBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.theartofdev.edmodo.cropper.CropImage
import de.hdodenhof.circleimageview.CircleImageView

class ActivityProfileEdit : AppCompatActivity() {

    private lateinit var binding : ActivityProfileEditBinding
    private lateinit var circleImageView: CircleImageView
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var databaseReference: DatabaseReference

    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri

    private lateinit var finalUri: Uri

    private lateinit var cropActivityResultLauncher : ActivityResultLauncher<Any?>

    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>(){
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setAspectRatio(1,1)
                .getIntent(this@ActivityProfileEdit)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()


        val uid = firebaseAuth.currentUser?.uid

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        binding.progressBar.visibility = View.GONE

        cropActivityResultLauncher = registerForActivityResult(cropActivityResultContract){
            it?.let { uri ->
                binding.profileImage.setImageURI(uri)
                finalUri = uri
            }
        }
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.profileImage.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                cropActivityResultLauncher.launch(null)
                //if(checkPermissions())

            }
        }
        binding.acceptButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            val name = binding.editTextName.text.toString()
            val bio = binding.editBio.text.toString()

            val profile = ProfileData(name, bio)

            if (uid != null){
            /*    var imageReference: DatabaseReference = databaseReference.child("Profile_pics").child(
                    "$uid.jpg")
                imageReference.putFile(
                )
                */
                databaseReference.child(uid).setValue(profile).addOnCompleteListener {
                    if(it.isSuccessful){
                        uploadProfilePic()
                    }else{
                        Toast.makeText(this@ActivityProfileEdit, "Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

    }
    private fun uploadProfilePic(){
       // imageUri = Uri.parse("android.resource://$packageName/${R.drawable.profile}")
        imageUri = finalUri
        storageReference = FirebaseStorage.getInstance().getReference("Users/"+firebaseAuth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnSuccessListener {
            Toast.makeText(this@ActivityProfileEdit, "Profile success", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this@ActivityProfileEdit, "Failed to upload image", Toast.LENGTH_SHORT).show()
        }
    }


}