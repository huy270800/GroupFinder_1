package square.ball.groupfinder_1.ui.MyGroups

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import square.ball.groupfinder_1.R
import square.ball.groupfinder_1.ui.Group
import square.ball.groupfinder_1.ui.Home.MyHomeGroupRecyclerViewAdapter


class MyGroupsFragment : Fragment() {

    private lateinit var groups: ArrayList<Group>
    private lateinit var rcList: RecyclerView
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Firebase.database.reference
        groups = arrayListOf()

        database.child("groups").get().addOnSuccessListener {
            if (it.value != null){
                val groupsFromDB = it.value as HashMap<String, Any>

                groupsFromDB.map{(key,value) ->
                    val groupFromDB = value as HashMap<String,Any>

                    val groupname1 = groupFromDB.get("groupname").toString()
                    val image1 = groupFromDB.get("image").toString()
                    val location1 = groupFromDB.get("location").toString()
                    val tags1 = groupFromDB.get("tags").toString()
                    val membercount1 = groupFromDB.get("membercount").toString()
                    val group = Group(groupname1,image1,location1,tags1,membercount1)
                    groups.add(group)
                }

                rcList.adapter?.notifyDataSetChanged()
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_groups_list, container, false)

        rcList = view.findViewById(R.id.mygrouplist)
        rcList.layoutManager = LinearLayoutManager(context)
        rcList.adapter = MyGroupRecyclerViewAdapter(groups)

        return view
    }

}