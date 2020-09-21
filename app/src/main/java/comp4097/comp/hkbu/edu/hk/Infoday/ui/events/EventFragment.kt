package comp4097.comp.hkbu.edu.hk.Infoday.ui.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import comp4097.comp.hkbu.edu.hk.Infoday.R
import comp4097.comp.hkbu.edu.hk.Infoday.data.AppDatabase
import comp4097.comp.hkbu.edu.hk.Infoday.data.SampleData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
class EventFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_event_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                //our code here
                val dept_id = arguments?.getString("dept_id")
                if (dept_id == null)
                    adapter = DeptRecyclerViewAdapter(SampleData.DEPT)
                else {
                    //adapter = EventRecyclerViewAdapter(SampleData.EVENT.filter {it.deptId== dept_id})

                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = AppDatabase.getInstance(context).eventDao()
                        val events = dao.findEventsByDeptID(dept_id)
                        CoroutineScope(Dispatchers.Main).launch {
                            adapter = EventRecyclerViewAdapter(events)
                        }
                    }


                    //to enable the back-arrow in the ActionBar.
                    (activity as
                            AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }
        }


        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            EventFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}