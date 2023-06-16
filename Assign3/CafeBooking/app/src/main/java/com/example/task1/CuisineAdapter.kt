package com.example.task1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView

class CuisineAdapter(
    private var cuisineList: List<String>,
    private var quantityListener: QuantityListener
)
    : RecyclerView.Adapter<CuisineAdapter.ViewHolder>() {

    var selectedList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    = ViewHolder (LayoutInflater.from(parent.context).inflate(R.layout.cuisine_layout, parent, false))

    override fun onBindViewHolder(holder: CuisineAdapter.ViewHolder, position: Int) {
        holder.bindView(cuisineList[position])
        holder.btn.setOnClickListener {
            if (holder.btn.isChecked) {
                selectedList.add(cuisineList.get(position))
            } else {
                selectedList.remove(cuisineList.get(position))
            }
            quantityListener.onQuantityChange(selectedList)
        }
    }

    override fun getItemCount(): Int = cuisineList.size

    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

        var btn = itemView.findViewById<CheckBox>(R.id.checkBox)
        fun bindView (cuisineList: String) {
            when(cuisineList) {
                "Western" -> {
                    btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.western_icon, 0, 0, 0)
                }
                "Italian" -> {
                    btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.italian_icon, 0, 0, 0)
                }
                "Chinese" -> {
                    btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.chinese_icon, 0, 0, 0)
                }
                "Japanese" -> {
                    btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.japanese_icon, 0, 0, 0)
                }
                "Malay" -> {
                    btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.malay_icon, 0, 0, 0)
                }
                "Indian" -> {
                    btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.indian_icon, 0, 0, 0)
                }
            }
        }
    }
}
