package com.example.task1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(
    val context: Context,
    private var list: ArrayList<Song>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.textview_title)
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        val artist = itemView.findViewById<TextView>(R.id.textview_artist)
        val releasedDate = itemView.findViewById<TextView>(R.id.textview_releasedDate)
        var cardview = itemView.findViewById<CardView>(R.id.cardview)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        holder.title.text = list.get(position).title
        holder.imageView.setImageResource(list.get(position).ImageSrc)
        var msg = "Artist: " + list.get(position).artist
        if (list.get(position).genre != "none") {
            msg += "\nGenre: " + list.get(position).genre
        }
        holder.artist.text = msg
        holder.releasedDate.text = "Released date: " + list.get(position).releasedDate
        holder.cardview.setOnClickListener {
            val activity = context as AppCompatActivity
            // initiate a new fragment - fragment with web view
            val fragmentThree = FragmentThree.newInstance(list.get(position).website)
            activity.supportFragmentManager.beginTransaction().replace(R.id.rec, fragmentThree).addToBackStack(null).commit()
        }
    }

    override fun getItemCount(): Int = list.size
}