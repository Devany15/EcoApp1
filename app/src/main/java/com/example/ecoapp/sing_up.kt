package com.example.ecoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import com.example.ecoapp.databinding.ActivitySingUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class sing_up : AppCompatActivity() {

    private lateinit var binding:ActivitySingUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val aaMunicipios = ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_dropdown_item)
        aaMunicipios.addAll(Arrays.asList("General Escobedo", "San Nicolas de los Garza", "Apodaca",
                                "San Pedro Garza Garcia", "Guadalupe"))

        binding.municipios.adapter = aaMunicipios

        firebaseAuth = FirebaseAuth.getInstance()
        binding.textSignIn.setOnClickListener{
            val intent = Intent(this, sing_in::class.java)
            startActivity(intent)
        }

        binding.buttonSignUp.setOnClickListener{

            val name = binding.inputName.text.toString()
            val email =binding.inputEmail.text.toString()
            val pass = binding.inputPassword.text.toString()
            val confirmPass = binding.inputConfirmPassword.text.toString()
            val municipio = binding.municipios.get(0).toString()

            if(name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()){
                if(pass.equals(confirmPass)){
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(){
                        if(it.isSuccessful){
                            database = FirebaseDatabase.getInstance().getReference("Users")
                            val User = User(name, email, pass, municipio)

                            database.child(name).setValue(User).addOnCompleteListener {
                                binding.inputName.text.clear()
                                binding.inputEmail.text.clear()
                                binding.inputPassword.text.clear()
                                binding.inputConfirmPassword.text.clear()
                            }
                            Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Las contraseñas no concuerdan", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "No se permiten campos vacios", Toast.LENGTH_SHORT).show()
            }
        }
    }
}