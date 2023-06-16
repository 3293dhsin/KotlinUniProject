package com.example.shoppinglist
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val ARG_PARAM1 = "param1"

class HomeFragment : Fragment(), Interface {

    private var list: ArrayList<item>? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: RecyclerViewAdapter? = null
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            list = it.getParcelableArrayList(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(this.context)
        adapter = list?.let { RecyclerViewAdapter(view, it, this) }
        recyclerView?.adapter = adapter
        var fab = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        // set the icon inside the floating button to WHITE color
        fab.imageTintList = ColorStateList.valueOf(Color.rgb(255, 255, 255))
        fab.setOnClickListener {
            val activity = context as AppCompatActivity
            val addItemFragment = AddItemFragment()
            activity.supportFragmentManager.beginTransaction().replace(R.id.container, addItemFragment).commit()
            activity.supportActionBar?.title = "Add Item"
        }

        dbHelper = DBHelper(view.context)
        notifyDataChanged(dbHelper.getNonBoughtList())

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: ArrayList<item>) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_PARAM1, param1)
                }
            }
    }

    override fun notifyDataChanged(itemList: ArrayList<item>) {
        adapter?.setList(itemList)
        adapter?.notifyDataSetChanged()
    }
}