package square.ball.groupfinder_1

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_favorite.*
import square.ball.groupfinder_1.Adapter.FavoriteAdapter
import square.ball.groupfinder_1.model.Favorite

class FavoriteActivity : AppCompatActivity() {
    var userList = ArrayList<Favorite.User>()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_favorite)

        userRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        getUsersList()

    }

    fun getUsersList(){
        val firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()

                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val user = dataSnapShot.getValue(Favorite.User::class.java)
                    if (!user!!.userId.equals(firebase.uid)) {
                        userList.add(user)
                    }
                }
                val FavoriteAdapter = FavoriteAdapter(this@FavoriteActivity, userList)

                userRecyclerView.adapter = FavoriteAdapter
            }
        })
    }
}

