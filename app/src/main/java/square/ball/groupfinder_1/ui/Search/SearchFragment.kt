package square.ball.groupfinder_1.ui.Search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import square.ball.groupfinder_1.R

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_search, container, false)
        val search = root.findViewById<SearchView>(R.id.searchView)
        val listView = root.findViewById<ListView>(R.id.listView)

        val names = arrayOf("Test0","Test1", "Test2", "Test3")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)

        /* val adapter: ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, names
        )*/

       listView.adapter = adapter



        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                search.clearFocus()
                if (names.contains(p0)) {
                    adapter.filter.filter(p0)
                } else {
                    Toast.makeText(activity, "Item not found", Toast.LENGTH_LONG).show()
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return false
            }


        })

        return root

    }

}



