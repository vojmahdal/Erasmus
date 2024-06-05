package com.example.erasmus

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.erasmus.database.UserData
import com.example.erasmus.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        binding.signupButton.setOnClickListener {
            val signupUsername = binding.signupUsername.text.toString()
            val signupPassword = binding.signupPassword.text.toString()
            val signupConfirm = binding.signupConfirmPassword.text.toString()

            signupEmail(signupUsername, signupPassword, signupConfirm)
        }
        binding.loginRedirect.setOnClickListener {
            startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
            finish()
        }
    }

    private fun signupEmail(email: String, password: String, confirmPass: String){
        if(email.isNotEmpty() && password.isNotEmpty() && confirmPass.isNotEmpty()){
            if(password == confirmPass){
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    if(it.isSuccessful){
                        //startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                        startActivity(Intent(this@SignupActivity, ActivityProfileEdit::class.java))
                        finish()
                    }else{
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Password not matching", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()

        }
    }

}