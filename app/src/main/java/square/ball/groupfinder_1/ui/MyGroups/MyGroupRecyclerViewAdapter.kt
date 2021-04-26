package square.ball.groupfinder_1.ui.MyGroups

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import square.ball.groupfinder_1.R
import square.ball.groupfinder_1.ui.Group


class MyGroupRecyclerViewAdapter(
    private val values: List<Group>
) : RecyclerView.Adapter<MyGroupRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_my_groups, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = values[position]

        holder.nameView.text = group.name
        Picasso.get().load(group.image).into(holder.imageView)
        holder.locationView.text = group.location
        holder.tagsView.text = group.tags
        holder.membercountView.text = group.membercount
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.name)
        val imageView: ImageView = view.findViewById(R.id.image)
        val locationView: TextView = view.findViewById(R.id.location)
        val tagsView: TextView = view.findViewById(R.id.tags)
        val membercountView: TextView = view.findViewById(R.id.membercount)

    }
}