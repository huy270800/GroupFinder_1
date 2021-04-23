package square.ball.groupfinder_1.ui.Users

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import square.ball.groupfinder_1.R
import square.ball.groupfinder_1.ui.Search.SearchViewModel

class UsersFragment : Fragment() {

    companion object {
        fun newInstance() = UsersFragment()
    }

    private lateinit var usersViewModel: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        usersViewModel =
            ViewModelProvider(this).get(UsersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_users, container, false)
        val textView: TextView = root.findViewById(R.id.text_user)
        usersViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }



}