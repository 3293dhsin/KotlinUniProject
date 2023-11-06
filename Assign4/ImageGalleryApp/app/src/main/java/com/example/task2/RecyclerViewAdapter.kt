package com.example.task2
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerViewAdapter(
    var context: Context,
    var list: ArrayList<image>,
    var chckBoxList: ChckBoxList
): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private var imageList = ArrayList<image>()
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var checkbox = itemView.findViewById<CheckBox>(R.id.checkBox)
        var imageview = itemView.findViewById<ImageView>(R.id.imageButton)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.icon,parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        displayImage(holder.imageview, position)
        holder.imageview.setOnClickListener {
            val intent = Intent(context, MainActivity2::class.java)
            intent.putExtra("image", list.get(position).image)
            context.startActivity(intent)
        }
        holder.checkbox.setOnClickListener {
            if (holder.checkbox.isChecked) {
                imageList.add(list.get(position))
            } else {
                imageList.remove(list.get(position))
            }
            chckBoxList.onCheckedChange(imageList)
        }
    }
    override fun getItemCount(): Int = list.size
    private fun displayImage(imageview: ImageView, position: Int) {
        Glide.with(context)
            .load("http://10.0.2.2/android/img/" + list.get(position).imagethumb)
            .override(400,400)
            .into(imageview)
    }
}