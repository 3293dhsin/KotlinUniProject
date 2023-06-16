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

class CompletedFragment : Fragment(), Interface {

    private var list: ArrayList<item>? = null
    private var adapter: BoughtRecyclerViewAdapter? = null
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
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_list, container, false)
        var recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = list?.let { BoughtRecyclerViewAdapter(view, it, this) }
        recyclerView.adapter = adapter

        var fab = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        // set the icon inside the floating button to WHITE color
        fab.imageTintList = ColorStateList.valueOf(Color.rgb(255, 255, 255))
        fab.setOnClickListener {
            val activity = context as AppCompatActivity
            val fragmentThree = AddItemFragment()
            activity.supportFragmentManager.beginTransaction().replace(R.id.container, fragmentThree).commit()
            activity.supportActionBar?.title = "Add Item"
        }

        // dbHelper
        dbHelper = DBHelper(view.context)
        notifyDataChanged(dbHelper.getItemBought())
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: ArrayList<item>) =
            CompletedFragment().apply {
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