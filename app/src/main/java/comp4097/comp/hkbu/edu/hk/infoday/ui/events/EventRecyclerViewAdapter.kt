package comp4097.comp.hkbu.edu.hk.infoday.ui.events

import android.annotation.SuppressLint
import android.util.Log
import android.util.TypedValue
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import comp4097.comp.hkbu.edu.hk.infoday.R
import comp4097.comp.hkbu.edu.hk.infoday.data.AppDatabase
import comp4097.comp.hkbu.edu.hk.infoday.data.Event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class EventRecyclerViewAdapter(
    private val values: List<Event>
) : RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_event_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("LongLogTag")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.deptId+ ":" + item.id
        holder.contentView.text = item.title


        //EventRecyclerView.onBindViewHolder
        holder.itemView.setOnClickListener { v ->
            CoroutineScope(IO).launch {
                val dao = AppDatabase.getInstance(v.context).eventDao()

                dao.update(values[position].also{ it.bookmarked = true })
                dao.findAllBookmarkedEvents().forEach {
                    Log.d("EventRecyclerViewAdapter", "onBindViewHolder: $it") }
            }
            Toast.makeText(v.context, "${item.title} is bookmarked",
                Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)
        init { //add this
            view.addRipple()
            view.isClickable = true
        }
    }

}

private fun View.addRipple() = with(TypedValue()) {
    context.theme.resolveAttribute(R.attr.selectableItemBackground, this, true)
    setBackgroundResource(resourceId)
}