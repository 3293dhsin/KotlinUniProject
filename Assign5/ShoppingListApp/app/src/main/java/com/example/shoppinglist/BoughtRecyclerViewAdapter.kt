package com.example.shoppinglist
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BoughtRecyclerViewAdapter(
    var view: View,
    private var itemList: ArrayList<item>,
    var inter: Interface
): RecyclerView.Adapter<BoughtRecyclerViewAdapter.ViewHolder>() {
    lateinit var dbHelper: DBHelper
    class ViewHolder (itemView: View): RecyclerView.ViewHolder (itemView) {
        var icon = itemView.findViewById<ImageView>(R.id.icon_bought)
        val name = itemView.findViewById<TextView>(R.id.textViewName_bought)
        val qty = itemView.findViewById<TextView>(R.id.textViewQty_bought)
        val size = itemView.findViewById<TextView>(R.id.textViewSize_bought)
        val date = itemView.findViewById<TextView>(R.id.textViewDate_bought)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.itemboughtview, parent, false)
        dbHelper = DBHelper(view.context)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.icon.setImageResource(itemList.get(position).icon)
        holder.name.text = itemList.get(position).name
        holder.qty.text = "Qty: " + itemList[position].qty
        holder.size.text = "Size: " + itemList[position].size
        holder.date.text = itemList[position].date
    }
    override fun getItemCount(): Int = itemList.size
    fun setList(itemList: ArrayList<item>) {
        this.itemList = itemList
    }
}