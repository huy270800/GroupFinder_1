package square.ball.groupfinder_1.ui.Favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import square.ball.groupfinder_1.R
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {


    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoriteViewModel =
            ViewModelProvider(this).get(FavoriteViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_favorite, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_search)
        favoriteViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it*/
      //  })
        return root
    }



}