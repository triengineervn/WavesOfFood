package com.example.wavesoffood.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.wavesoffood.databinding.FragmentProfileBinding
import com.example.wavesoffood.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userId: String
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid ?: ""

        setupUI()
        setUserInfo()

        return binding.root
    }

    private fun setupUI() {
        binding.apply {
            disableEditing() // Initially disable editing fields

            editProfile.setOnClickListener {
                enableEditing() // Enable editing when 'edit' button is clicked
            }

            btnSaveInfo.setOnClickListener {
                val name = nameEditText.text.toString().trim()
                val email = emailEditText.text.toString().trim()
                val address = addressEditText.text.toString().trim()
                val phone = phoneEditText.text.toString().trim()

                updateUserInfo(name, email, address, phone)
                disableEditing() // Disable editing after saving
            }
        }
    }

    private fun enableEditing() {
        binding.apply {
            nameEditText.isEnabled = true
            emailEditText.isEnabled = true
            addressEditText.isEnabled = true
            phoneEditText.isEnabled = true
        }
    }

    private fun disableEditing() {
        binding.apply {
            nameEditText.isEnabled = false
            emailEditText.isEnabled = false
            addressEditText.isEnabled = false
            phoneEditText.isEnabled = false
        }
    }

    private fun updateUserInfo(name: String, email: String, address: String, phone: String) {
        val userRef = database.reference.child("users").child(userId).child("user")
        val updatedUser = UserModel(name, email, null, phone, address)

        userRef.setValue(updatedUser)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setUserInfo() {
        val userRef = database.reference.child("users").child(userId).child("user")

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                snapshot.getValue(UserModel::class.java)?.let { user ->
                    binding.apply {
                        nameEditText.setText(user.name)
                        emailEditText.setText(user.email)
                        addressEditText.setText(user.address)
                        phoneEditText.setText(user.phone)
                    }
                }
            }

            override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
