package com.example.wavesoffood.activities

import CartItem
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wavesoffood.databinding.ActivityPaymentBinding
import com.example.wavesoffood.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PaymentActivity : AppCompatActivity() {
    private val binding: ActivityPaymentBinding by lazy {
        ActivityPaymentBinding.inflate(layoutInflater)
    }

    private var cartItems: MutableList<CartItem> = mutableListOf()
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        intent.getParcelableArrayListExtra<CartItem>("cartItems")?.let {
            cartItems = it.toMutableList()
        } ?: run {
            Toast.makeText(this, "No cart items found", Toast.LENGTH_SHORT).show()
            finish()
        }

        setUserData()

        binding.placeOrderBtn.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val address = binding.addressEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()
            val totalPrice = binding.totalPriceTextView.text.toString()

            Log.d(
                "minhtri",
                "Name: $name, Address: $address, Phone: $phone, Total Price: $totalPrice"
            )

            val intent = Intent(this, OrderSuccessActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setUserData() {
        val user = auth.currentUser
        val userId = user!!.uid
        val userRef = database.child("users").child(userId).child("user")

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userData = snapshot.getValue(UserModel::class.java)
                    populateUserData(userData)
                } else {
                    Toast.makeText(
                        this@PaymentActivity,
                        "User data not found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@PaymentActivity,
                    "Failed to retrieve user data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun populateUserData(userData: UserModel?) {
        binding.apply {

            nameEditText.setText(userData?.name)
            addressEditText.setText(userData?.address)
            phoneEditText.setText(userData?.phone)
            totalPriceTextView.text = calculateTotalAmount()
        }
    }

    private fun calculateTotalAmount(): String {
        val total = cartItems.sumOf {
            val itemPrice = it.price?.replace(",", "")?.toIntOrNull() ?: 0
            val itemQuantity = it.quantity ?: 0
            itemPrice * itemQuantity
        }
        return total.toString()
    }

}
