package com.example.wavesoffood.activities

import CartItem
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wavesoffood.databinding.ActivityPaymentBinding
import com.example.wavesoffood.models.OrdersModel
import com.example.wavesoffood.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

@Suppress("DEPRECATION")
class PaymentActivity : AppCompatActivity() {
    private val binding: ActivityPaymentBinding by lazy {
        ActivityPaymentBinding.inflate(layoutInflater)
    }

    private var cartItems: MutableList<CartItem> = mutableListOf()
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var userId: String

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
            val name = binding.nameEditText.text.toString().trim()
            val address = binding.addressEditText.text.toString().trim()
            val phone = binding.phoneEditText.text.toString().trim()
            val totalPrice = binding.totalPriceTextView.text.toString().trim()
            if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || totalPrice.isEmpty()) {
                Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
            } else {
                placeOrder(name, address, phone, totalPrice, cartItems)
            }
        }
    }

    private fun placeOrder(
        name: String,
        address: String,
        phone: String,
        totalPrice: String,
        cartItems: MutableList<CartItem>
    ) {
        userId = auth.currentUser?.uid!!
        val time = System.currentTimeMillis()
        val itemPushKey = database.child("users").child(userId).child("orders").push().key

        val orderDetails = OrdersModel(
            userId,
            name,
            cartItems,
            totalPrice,
            address,
            phone,
            false,
            false,
            itemPushKey,
            time
        )

        val orderRef = database.child("users").child(userId).child("orders").child(itemPushKey!!)
        orderRef.setValue(orderDetails).addOnSuccessListener {
            val intent = Intent(this, OrderSuccessActivity::class.java)
            removeItemsFromCart()
            addOrderToHistory(orderDetails)
            startActivity(intent)
            finish()
        }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to place order", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addOrderToHistory(orderDetails: OrdersModel) {
        database.child("users").child(userId).child("history").child(orderDetails.itemPushKey!!)
            .setValue(orderDetails).addOnSuccessListener {
                Toast.makeText(this, "Order added to history", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add order to history", Toast.LENGTH_SHORT).show()
            }

    }

    private fun removeItemsFromCart() {
        val cartItemRef = database.child("users").child(userId).child("cart")
        cartItemRef.removeValue()
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
