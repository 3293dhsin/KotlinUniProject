package com.example.shoppinglist

import android.media.Image
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

private const val ARG_PARAM1 = "param1"

class DisplayItemFragment : Fragment() {

    private var item: item? = null
    lateinit var textview_shoppingitem: TextView
    lateinit var textview_detail: TextView
    lateinit var textview_quantity: TextView
    lateinit var textview_size: TextView
    lateinit var img: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            item = it.getParcelable<item>(ARG_PARAM1)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_display_item, container, false)
        initUI(view)
        textview_shoppingitem.text = item?.name
        textview_detail.text = item?.detail
        textview_quantity.text = item?.qty
        textview_size.text = item?.size
        if (item?.icon == R.drawable.urgent) {
            img.setImageResource(R.drawable.checked)
        } else {
            img.setImageResource(R.drawable.unchecked)
        }
        return view
    }

    fun initUI(view: View) {
        textview_shoppingitem = view.findViewById(R.id.shoppingitem_box)
        textview_detail = view.findViewById(R.id.detail_box)
        textview_quantity = view.findViewById(R.id.quantity_box)
        textview_size = view.findViewById(R.id.size_box)
        img = view.findViewById(R.id.imageView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.uppermenu, menu)
    }

    override fun onOptionsItemSelected(itm: MenuItem): Boolean {
        return when (itm.itemId) {
            R.id.edit_icon -> {
                // load another fragment: edit fragment
                val activity = context as AppCompatActivity
                val fragmentThree = EditItemFragment.newInstance(item)
                activity.supportFragmentManager.beginTransaction().replace(R.id.container, fragmentThree).commit()
                activity.supportActionBar?.title = "Edit Item"
                true
            }
            else -> super.onOptionsItemSelected(itm)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: item) =
            DisplayItemFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                }
            }
    }
}