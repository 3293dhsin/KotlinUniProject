package com.example.shoppinglist

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddItemFragment : Fragment() {

    private lateinit var dbHelper: DBHelper
    private lateinit var editText_name: EditText
    private lateinit var editMultiLine_details: EditText
    private lateinit var textView_quantity: TextView
    var qty = 1
    private lateinit var spinnerBtn: Spinner
    private lateinit var chckbox_urgent: CheckBox
    private lateinit var button_addtolist: Button
    private lateinit var buttonUp: ImageButton
    private lateinit var buttonDown: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_item_form, container, false)

        initUI(view)
        dbHelper = DBHelper(view.context)

        // Spinner adapter
        ArrayAdapter.createFromResource(
            view.context,
            R.array.size_Array,
            android.R.layout.simple_spinner_item
        ).also {
            adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerBtn.adapter = adapter
        }
        buttonUp.setOnClickListener {
            qty += 1
            textView_quantity.text = qty.toString()
        }
        buttonDown.setOnClickListener {
            if (qty > 1) {
                qty -= 1
            }
            textView_quantity.text = qty.toString()
        }
        var icon = R.drawable.buy
        chckbox_urgent.setOnClickListener {
            if (chckbox_urgent.isChecked) {
                icon = R.drawable.urgent
            } else {
                icon = R.drawable.buy
            }
        }

        button_addtolist.setOnClickListener {
            var name = editText_name.text.toString()
            var detail = editMultiLine_details.text.toString()
            var quantity = textView_quantity.text.toString()
            var selectedSize = spinnerBtn.selectedItem.toString()
            if (TextUtils.isEmpty(editText_name.text)) {
                editText_name.setError("Please enter the item to be purchased.")
            } else {
                dbHelper.insertItem(item(icon, name, detail, quantity, selectedSize, " "))
                val activity = context as AppCompatActivity
                val homefragment = HomeFragment.newInstance(dbHelper.getNonBoughtList())
                activity.supportFragmentManager.beginTransaction().replace(R.id.container, homefragment).commit()
            }
        }
        return view
    }

    fun initUI(view: View) {
        editText_name = view.findViewById(R.id.editText_Name)
        editMultiLine_details = view.findViewById(R.id.editTextTextMultiLine)
        textView_quantity = view.findViewById(R.id.textview_quantitynum)
        spinnerBtn = view.findViewById(R.id.spinner)
        chckbox_urgent = view.findViewById(R.id.checkBox_urgent)
        button_addtolist = view.findViewById(R.id.button)
        buttonDown = view.findViewById(R.id.imageButton_down)
        buttonUp = view.findViewById(R.id.imageButton_up)
    }
}