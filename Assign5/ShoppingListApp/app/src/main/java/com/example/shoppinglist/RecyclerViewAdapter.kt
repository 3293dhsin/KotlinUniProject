package com.example.shoppinglist

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RecyclerViewAdapter(
    var view: View,
    private var itemList: ArrayList<item>,
    var inter: Interface
): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    lateinit var dbHelper: DBHelper

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val icon = itemView.findViewById<ImageView>(R.id.icon)
        val name = itemView.findViewById<TextView>(R.id.textViewName)
        val qty = itemView.findViewById<TextView>(R.id.textViewQty)
        val size = itemView.findViewById<TextView>(R.id.textViewSize)
        val switch = itemView.findViewById<Switch>(R.id.switch_item)
        val constr_layout = itemView.findViewById<ConstraintLayout>(R.id.ConstrainLayout)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.itemview, parent, false)
        dbHelper = DBHelper(view.context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        holder.icon.setImageResource(itemList.get(position).icon)
        holder.name.text = itemList.get(position).name
        holder.qty.text = "Qty: " + itemList[position].qty
        holder.size.text = "Size: " + itemList[position].size

        // to delete item
        holder.constr_layout.setOnLongClickListener {
            popupMSG(view, itemList[position].id, itemList[position].name)
            true
        }

        // to edit item
        holder.constr_layout.setOnClickListener {
            val activity = view.context as AppCompatActivity
            val displayItemFragment = DisplayItemFragment.newInstance(itemList.get(position))
            activity.supportFragmentManager.beginTransaction().replace(R.id.container, displayItemFragment).commit()
            activity.supportActionBar?.title = "Display Item"
        }

        // to set item to bought
        holder.switch.setOnClickListener {
            holder.switch.isChecked = false
            itemList.get(position).icon = R.drawable.bought

            // capture the date when user clicks on the switch button
            var current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
            val formatted = current.format(formatter)
            itemList.get(position).date = formatted

            // edit the item in the database
            dbHelper.editItem(itemList.get(position))
            inter.notifyDataChanged(dbHelper.getNonBoughtList())
        }
    }

    override fun getItemCount() = itemList.size

    fun setList(itemList: ArrayList<item>) {
        this.itemList = itemList
    }

    fun popupMSG(view: View, position: Int, name: String) {
        val dialogBuilder = AlertDialog.Builder(view.context)
            .setIcon(R.drawable.important)
            .setTitle("Delete Item")
            .setMessage("Are you sure you want to delete $name from the list?")
            .setCancelable(false)   // dialog box is cancellable
            .setPositiveButton("Confirm", DialogInterface.OnClickListener { dialog, id ->
                dbHelper.deleteItem(position)
                inter.notifyDataChanged(dbHelper.getNonBoughtList())
            })
            .setNegativeButton("Cancel", null)

        // create dialog box
        val alert = dialogBuilder.create()
        // show alert dialog
        alert.show()
    }
}