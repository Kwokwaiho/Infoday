package comp4097.comp.hkbu.edu.hk.infoday.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.firestore.FirebaseFirestore
import comp4097.comp.hkbu.edu.hk.infoday.R
import comp4097.comp.hkbu.edu.hk.infoday.data.News
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
class NewsListFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recyclerView = inflater.inflate(R.layout.fragment_news_list, null, false) as
                RecyclerView
        val swipeLayout = SwipeRefreshLayout(requireContext())
        swipeLayout.addView(recyclerView)
        swipeLayout.setOnRefreshListener {
            swipeLayout.isRefreshing = true
            reloadData(recyclerView)
            swipeLayout.isRefreshing = false
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        reloadData(recyclerView)
        return swipeLayout
    }

    private fun reloadData(recyclerView: RecyclerView) {
        val db = FirebaseFirestore.getInstance()

        db.collection("news").addSnapshotListener { value, error ->
            value?.let {
                CoroutineScope(Dispatchers.Main).launch {
                    recyclerView.adapter = NewsListRecyclerViewAdapter(it.documents.map { doc ->
                        News(doc.getString("image")!!,
                            doc.getString("title")!!,
                            doc.getString("detail")!!)
                    })
                }
            }
        }
    }

//    private fun reloadData(recyclerView: RecyclerView) {
//        val NEWS_URL = "https://api.npoint.io/256da2ee7badc12b0ec2"
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val json = Network.getTextFromNetwork(NEWS_URL)
//                val news = Gson().fromJson<List<News>>(json,object :
//                    TypeToken<List<News>>() {}.type)
//                CoroutineScope(Dispatchers.Main).launch {
//                    recyclerView.adapter = NewsListRecyclerViewAdapter(news)
//                }
//            } catch (e: Exception) {
//                Log.d("NewsListFragment", "Error in loading data")
//                val news = listOf(News("", "Cannot fetch news", "Please check your network connection,"))
//                        CoroutineScope(Dispatchers.Main).launch {
//                    recyclerView.adapter = NewsListRecyclerViewAdapter(news)
//                }
//            }
//        }
//    }


    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            NewsListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}