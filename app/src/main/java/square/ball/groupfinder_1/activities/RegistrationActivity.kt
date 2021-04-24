package square.ball.groupfinder_1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration.*
import square.ball.groupfinder_1.R

class RegistrationActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database : FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("user")

        register_btn.setOnClickListener{
            val email = email_register.text.toString().trim()
            val password = password_register.text.toString().trim()


            if (email.isEmpty()) {
                email_register.error = "Email Required"
                email_register.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                email_register.error = "Valid Email Required"
                email_register.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                password_register.error = "6 char password required"
                password_register.requestFocus()
                return@setOnClickListener
            }

            registerUser(email, password)
        }

        text_view_login.setOnClickListener {
            startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
        }
    }

    private fun registerUser(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val currentUser = mAuth.currentUser
                    val currentUserDb = databaseReference?.child(currentUser?.uid!!)
                    currentUserDb?.child("name")?.setValue(name_register.text.toString())

                    register()

                } else {
                    task.exception?.message?.let {
                        toast(it)
                    }
                }
            }
    }
    override fun onStart() {
        super.onStart()
        mAuth.currentUser?.let {
            login()
        }
    }
}