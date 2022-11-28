package com.example.ecoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ecoapp.databinding.ActivitySingInBinding
import com.example.ecoapp.databinding.ActivitySingUpBinding
import com.google.firebase.auth.FirebaseAuth

class sing_in : AppCompatActivity() {

    private lateinit var binding:ActivitySingInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySingInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.textCreateNewAccount.setOnClickListener{
            val intent = Intent(this, sing_up::class.java)
            startActivity(intent)
        }

        binding.buttonSingIn.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val pass = binding.inputPassword.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if(it.isSuccessful){
                        val intent = Intent(this, proyectos_registrados::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "No se permiten campos vacios", Toast.LENGTH_SHORT).show()
            }
        }
    }
}