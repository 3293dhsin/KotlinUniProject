package com.example.recipeapp
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerViewAdapter (
    val context: Context,
    private var list: recipeList
    ) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val label = itemView.findViewById<TextView>(R.id.label_textview)
        val source = itemView.findViewById<TextView>(R.id.source_textview)
        val dietlabel = itemView.findViewById<TextView>(R.id.diettype_textview)
        val imgthumb = itemView.findViewById<ImageView>(R.id.imageView)
        val button = itemView.findViewById<Button>(R.id.button)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cuisineview, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        holder.label.text = list.hits[position].recipe.label
        holder.source.text = list.hits[position].recipe.source
        holder.dietlabel.text = TextUtils.join(", ", list.hits[position].recipe.dietLabels)
        displayImage(holder.imgthumb, position)
        holder.button.setOnClickListener {
            val activity = context as AppCompatActivity
            val displayIngredientsFragment = DisplayIngredientsFragment.newInstance(list.hits[position].recipe)
            activity.supportFragmentManager.beginTransaction().add(R.id.rec, displayIngredientsFragment).addToBackStack(null).commit()
        }
    }
    override fun getItemCount(): Int = list.hits.size
    private fun displayImage(imageview: ImageView, position: Int) {
        Glide.with(context)
            .load("" + list.hits[position].recipe.images.THUMBNAIL.url)
            .override(400,400)
            .into(imageview)
    }
}