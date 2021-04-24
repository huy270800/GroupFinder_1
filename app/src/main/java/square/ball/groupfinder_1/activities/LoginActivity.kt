package square.ball.groupfinder_1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import square.ball.groupfinder_1.R

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        loginUser()

        text_view_register.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
        }
    }

    private fun loginUser(){
        login_btn.setOnClickListener {
            val email = email_login.text.toString().trim()
            val password = password_login.text.toString().trim()

            if (email.isEmpty()) {
                email_login.error = "Email Required"
                email_login.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                email_login.error = "Valid Email Required"
                email_login.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                password_login.error = "6 char password required"
                password_login.requestFocus()
                return@setOnClickListener
            }

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        login()
                    } else {
                        task.exception?.message?.let {
                            toast(it)
                        }
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