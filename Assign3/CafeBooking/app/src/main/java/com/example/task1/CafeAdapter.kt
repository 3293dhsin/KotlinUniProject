package com.example.task1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CafeAdapter(
    val context: Context,
    private var cafeList: List<Cafe>,
    val listener: (Cafe) -> Unit)
    : RecyclerView.Adapter<CafeAdapter.ViewHolder> ()  {

    class ViewHolder (itemView: View):RecyclerView.ViewHolder(itemView) {
        val imageViewCafe = itemView.findViewById<ImageView>(R.id.imageViewCafe)
        val txtName = itemView.findViewById<TextView>(R.id.textViewName)
        val imageViewLocation = itemView.findViewById<ImageView>(R.id.imageViewLocation)
        val txtAdd = itemView.findViewById<TextView>(R.id.textViewAddress)
        val txtOpenHr = itemView.findViewById<TextView>(R.id.textViewOpenHours)
        val txtRate = itemView.findViewById<TextView>(R.id.textViewRating)
        val imageViewLike = itemView.findViewById<ImageView>(R.id.imageViewLike)

            fun bindView (cafe: Cafe, listener: (Cafe) -> Unit, context: Context) {
            imageViewCafe.setImageResource(cafe.ImageIcon)
            txtName.text = context.getString(cafe.Name)
            txtAdd.text = cafe.Address
            txtOpenHr.text = cafe.OpenHours
            txtRate.text = cafe.rate
            imageViewLocation.setImageResource(R.drawable.location_32)
            imageViewLike.setImageResource(R.drawable.like_icon)
            // once a cafe view is clicked, pass the clicked cafe object to the listener
            itemView.setOnClickListener { listener(cafe) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    = ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(cafeList[position], listener, context)
    }

    override fun getItemCount(): Int = cafeList.size

}