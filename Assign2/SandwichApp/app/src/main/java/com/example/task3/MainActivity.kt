package com.example.task3

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.LinkedHashMap

class MainActivity : AppCompatActivity(), View.OnClickListener {
    //Hash Map for options and their prices
    var fillingmap: LinkedHashMap<String, Double> = LinkedHashMap<String, Double>()
    var sidemap: LinkedHashMap<String, Double> = LinkedHashMap<String, Double>()
    var sizemap: LinkedHashMap<String, Double> = LinkedHashMap<String, Double>()

    // checkboxes for filling and side
    private val fillingGroupChck: Array<CheckBox?> = arrayOfNulls<CheckBox>(5)
    private val sideGroupChck: Array<CheckBox?> = arrayOfNulls<CheckBox>(4)

    // radio group, radio button
    private lateinit var radiogrp: RadioGroup
    private val radiobtn: Array<RadioButton?> = arrayOfNulls<RadioButton>(3)

    // text view
    private lateinit var fillingTextView: TextView
    private lateinit var sideTextView: TextView
    private lateinit var rm: TextView

    // place order button and reset button
    private lateinit var placeOrderBtn: ImageButton
    private lateinit var resetBtn: ImageButton

    // selected list for filling and side
    var selectedFillingList: LinkedHashMap<String, Double> = LinkedHashMap<String, Double>()
    var selectedSideList: LinkedHashMap<String, Double> = LinkedHashMap<String, Double>()

    private var count = 0
    private var total = 0.00
    private var sizetotal = 0.00
    private var min = 0.00
    private var key = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
    }

    private fun initUI() {
        for(i in 1..fillingGroupChck.size) {
            val id = resources.getIdentifier("filling$i", "id", packageName)
            fillingGroupChck[i-1] = findViewById(id)
        }
        for(i in 1..sideGroupChck.size) {
            val id = resources.getIdentifier("side$i", "id", packageName)
            sideGroupChck[i-1] = findViewById (id)
        }
        for(i in 1..radiobtn.size) {
            val id = resources.getIdentifier("radiobutton$i","id", packageName)
            radiobtn[i-1] = findViewById (id)
        }
        rm = findViewById (R.id.rm)
        placeOrderBtn = findViewById(R.id.place_order_button)
        resetBtn = findViewById(R.id.reset_button)
        radiogrp = findViewById(R.id.radiogroup)
        fillingTextView = findViewById(R.id.filling)
        sideTextView = findViewById(R.id.side)

        setPricing()
        sideOnClick()
        fillingOnClick()
        radiogrp.setOnCheckedChangeListener { radioGroup, i -> sizeOnClick(i) }
        placeOrderBtn.setOnClickListener(this)
        resetBtn.setOnClickListener(this)
    }

    private fun fillingOnClick() {
        var fillingtotal = 0.00

        val filling: CompoundButton.OnCheckedChangeListener =
            CompoundButton.OnCheckedChangeListener { cb, b ->
                if(count == 3 && b){
                    cb.isChecked = false
                }else if(b){
                    count++
                    total -= fillingtotal
                    for (i in fillingmap) {
                        if (i.key == cb.tag) {
                            selectedFillingList[i.key] = i.value
                            println("*")
                        }
                    }
                    fillingtotal = selectingRule (selectedFillingList, fillingmap)
                }else if(!b){
                    count--
                    total -= fillingtotal
                    selectedFillingList.remove(cb.tag)
                    fillingtotal = selectingRule (selectedFillingList, fillingmap)
                }
                if (selectedFillingList.size > 0)
                    fillingTextView.text = getString(R.string.filling) + " ${selectedFillingList.keys.elementAt(selectedFillingList.size-1)}"
                else
                    fillingTextView.text = getString(R.string.filling)
            }

        for(i in 0..4) {
            fillingGroupChck[i]?.setOnCheckedChangeListener(filling)
        }
    }

    private fun sideOnClick() {
        var sidetotal = 0.00

        val side: CompoundButton.OnCheckedChangeListener =
            CompoundButton.OnCheckedChangeListener { cb, b ->
                total -= sidetotal
                if(b) {
                    for (i in sidemap) {
                        if (i.key == cb.tag) {
                            selectedSideList[i.key] = i.value
                        }
                    }
                } else if(!b) {
                    selectedSideList.remove(cb.tag)
                }
                sidetotal = selectingRule (selectedSideList, sidemap)
                if (selectedSideList.size > 0)
                    sideTextView.text = getString(R.string.side) + " ${selectedSideList.keys.elementAt(selectedSideList.size-1)}"
                else
                    sideTextView.text = getString(R.string.side)
            }

        for(i in 0..3) {
            sideGroupChck[i]?.setOnCheckedChangeListener(side)
        }
    }

    // setting for size radiobutton
    private fun sizeOnClick(i: Int) {
        total -= sizetotal
        sizetotal = 0.00
        when (i) {
            R.id.radiobutton1->{ sizetotal += sizemap.values.elementAt(0) }
            R.id.radiobutton2->{ sizetotal += sizemap.values.elementAt(1) }
            R.id.radiobutton3->{ sizetotal += sizemap.values.elementAt(2) }
        }
        total += sizetotal
        rm.text = "RM ${String.format("%.2f", total)}"
    }

    private fun setPricing() {
        fillingmap.put("Ham Slices", 2.50)
        fillingmap.put("Roasted Chicken",2.00)
        fillingmap.put("Beef Steak", 4.50)
        fillingmap.put("Grilled Salmon", 3.70)
        fillingmap.put("Kebab", 4.00)
        for (i in 0..4) {
            fillingGroupChck[i]?.tag = fillingmap.keys.elementAt(i)
        }

        sidemap.put("Tomato", 1.00)
        sidemap.put("Lettuce", 1.20)
        sidemap.put("Onion", 0.50)
        sidemap.put("Cheese", 1.50)
        for (i in 0..3) {
            sideGroupChck[i]?.tag = sidemap.keys.elementAt(i)
        }

        sizemap.put("6 inch", 7.00)
        sizemap.put("9 inch", 9.50)
        sizemap.put("12 inch", 13.00)
        for (i in 0..2) {
            radiobtn[i]?.tag = sidemap.keys.elementAt(i)
        }

    }

    private fun selectingRule (selectedlist: LinkedHashMap<String, Double>, list: LinkedHashMap<String, Double>): Double {

        for (i in selectedlist) {
            for (j in list) {
                if (i.key == j.key) {
                    selectedlist[j.key] = j.value
                }
            }
        }

        if (selectedlist.size > 0) {
            min = Collections.min(selectedlist.values)
            for (i in list.keys) {
                if (min == selectedlist[i]) {
                    key = i
                    selectedlist[key] = 0.00
                }
            }
        }

        var ttl = 0.0
        for (i in selectedlist.values) {
            if (i != null) {
                ttl += i
            }
        }

        total += ttl
        rm.text = "RM ${String.format("%.2f", total)}"
        return ttl
    }

    override fun onClick(btn: View?) {
        when (btn?.id) {
            R.id.place_order_button->{
                val dialogBuilder = AlertDialog.Builder(this)
                var message = ""

                var f = false
                var r = false

                for (i in radiobtn.indices) {
                    if (radiobtn[i]?.isChecked == true) {
                        r = true
                        message += "${sizemap.keys.elementAt(i)}: RM ${sizemap.values.elementAt(i)}" + "\n"
                    }
                }
                for (i in 0 until selectedFillingList.size) {
                    if (selectedFillingList.size > 0) {
                        f = true
                        message += "${selectedFillingList.keys.elementAt(i)}: RM ${selectedFillingList.values.elementAt(i)}" + "\n"
                    }
                }
                for (i in 0 until selectedSideList.size) {
                    if (selectedSideList.size > 0) {
                        message += "${selectedSideList.keys.elementAt(i)}: RM ${selectedSideList.values.elementAt(i)}" + "\n"
                    }
                }


                if (r && f) {
                    message += "=====================\n" +
                            "Total: RM $total" +
                            "\n====================="
                    // set message of alert dialog
                    dialogBuilder.setMessage(message)
                        // if the dialog is cancelable
                        .setCancelable(false)
                        // positive button text and action
                        .setPositiveButton("Confirm", DialogInterface.OnClickListener {
                                dialog, id -> finish()
                        })
                        // negative button text and action
                        .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                                dialog, id -> dialog.cancel()
                        })
                } else {
                    message = "You must select at least 1 filling and size!"
                    // set message of alert dialog
                    dialogBuilder.setMessage(message)
                        // if the dialog is cancelable
                        .setCancelable(false)
                        // negative button text and action
                        .setNegativeButton("Return", DialogInterface.OnClickListener {
                                dialog, id -> dialog.cancel()
                        })
                }

                // create dialog box
                val alert = dialogBuilder.create()
                // set title for alert dialog box
                alert.setTitle("Your Order")
                // show alert dialog
                alert.show()
            }
            R.id.reset_button->{
                for (i in fillingGroupChck) {
                    i?.isChecked = false
                }
                for (i in sideGroupChck) {
                    i?.isChecked = false
                }
                radiogrp.clearCheck()
                total = 0.00
                sizetotal = 0.00
                rm.text = getString(R.string.rm_0_00)
                fillingTextView.text = getString(R.string.filling)
                sideTextView.text = getString(R.string.side)
            }
        }
    }
}