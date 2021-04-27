package square.ball.groupfinder_1.ui.Users

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.fragment_users.*
import square.ball.groupfinder_1.R
import square.ball.groupfinder_1.activities.LoginActivity
import square.ball.groupfinder_1.activities.toast

class UsersFragment : Fragment() {

    private lateinit var usersViewModel: UsersViewModel

    lateinit var mAuth : FirebaseAuth
    private var databaseReference: DatabaseReference?= null
    private var database: FirebaseDatabase?= null
    private val currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        usersViewModel =
            ViewModelProvider(this).get(UsersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_users, container, false)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("user")

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadProfile()

        currentUser?.let { user ->
            if (user.isEmailVerified){
                text_not_verified.visibility = View.INVISIBLE
            } else {
                text_not_verified.visibility = View.VISIBLE
            }
        }

        text_not_verified.setOnClickListener{
            currentUser?.sendEmailVerification()?.addOnCompleteListener {
                if (it.isSuccessful) {
                    context?.toast("Verification Email Sent")
                } else {
                    context?.toast(it.exception?.message!!)
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        logout_btn.setOnClickListener(View.OnClickListener {
            mAuth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        })
    }

    private fun loadProfile() {
        val user = mAuth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        text_email.text = user?.email

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                text_name.text = snapshot.child("name").value.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}