package com.example.wavesoffood.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.ActivitySignUpBinding
import com.example.wavesoffood.models.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignUpActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var name: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()


        // initialize Firebase authentication
        auth = Firebase.auth
        // initialize Firebase database
        database = Firebase.database.reference

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        setOnClickSignUpBtn()
        setOnClickLoginTextView()
        setOnClickGoogleSignInBtn()
    }

    private fun setOnClickGoogleSignInBtn() {
        binding.googleBtn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                task.result.let { account ->
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                user?.let {
                                    saveUserInfoToFirebase()
                                    uploadUI()
                                }
                            } else {


                                showToast("Authentication failed.")
                            }
                        }
                }
            }
        }

    private fun setOnClickSignUpBtn() {
        binding.signUpBtn.setOnClickListener {
            name = binding.editTextName.text.toString().trim()
            email = binding.editTextEmailAddress.text.toString().trim()
            password = binding.editTextPassword.text.toString().trim()


            if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                showToast("Please Fill All Fields")
            } else {
                createAccount(email, password)
            }
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    saveUserInfoToFirebase()
                    uploadUI()
                } else {
                    showToast("Authentication failed.")
                }
            }

    }

    private fun saveUserInfoToFirebase() {
        name = binding.editTextName.text.toString().trim()
        email = binding.editTextEmailAddress.text.toString().trim()
        password = binding.editTextPassword.text.toString().trim()

        val userModel = UserModel(name, email, password, "", "")
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        database.child("users").child("${userId!!}/user").setValue(userModel)
    }

    private fun uploadUI() {
        val intent = Intent(this, AddLocationActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun setOnClickLoginTextView() {
        binding.loginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}