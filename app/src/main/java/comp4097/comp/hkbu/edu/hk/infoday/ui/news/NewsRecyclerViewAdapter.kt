package comp4097.comp.hkbu.edu.hk.infoday.ui.news

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import comp4097.comp.hkbu.edu.hk.infoday.R
import comp4097.comp.hkbu.edu.hk.infoday.data.News

import comp4097.comp.hkbu.edu.hk.infoday.ui.news.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class NewsListRecyclerViewAdapter(
    //private val values: List<DummyItem>
    private val values: List<News>
) : RecyclerView.Adapter<NewsListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_news_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.titleTextView.text = item.title
        holder.detailTextView.text = item.detail
        if (item.image != "")
            Picasso.get().load(item.image).into(holder.newsImageView)
        else

            holder.newsImageView.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.ic_outline_cloud_download_24))
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val newsImageView: ImageView = view.findViewById(R.id.newsImageView)
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val detailTextView: TextView = view.findViewById(R.id.detailTextView)
        override fun toString(): String {
            return super.toString() + " '" + titleTextView.text + "'"
        }
    }

}