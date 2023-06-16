package com.example.task3

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import java.util.*
import kotlin.collections.LinkedHashMap

class Fragment_one : Fragment() {
    private lateinit var callBack: onButtonClick
    interface onButtonClick {
        fun passData(selectedFillingList: LinkedHashMap<String, Double>,
                     selectedSideList: LinkedHashMap<String, Double>,
                     selectedSize: SelectedRadio,
                     total: Double)
    }
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
    // selected list for filling and side
    var selectedFillingList: LinkedHashMap<String, Double> = LinkedHashMap<String, Double>()
    var selectedSideList: LinkedHashMap<String, Double> = LinkedHashMap<String, Double>()
    lateinit var selectedSize: SelectedRadio
    private var count = 0
    private var total = 0.00
    private var sizetotal = 0.00
    private var min = 0.00
    private var key = ""
    private var fillingtotal = 0.00
    private var sidetotal = 0.00
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_one, container, false)
        val btn = view.findViewById<Button>(R.id.nextpage)
        initUI(view)
        btn.setOnClickListener() {
            buttonClickMethod(view)
        }
        callBack = activity as onButtonClick
        return view
    }
    private fun initUI(view: View) {
        fillingGroupChck[0] = view.findViewById(R.id.filling1)
        fillingGroupChck[1] = view.findViewById(R.id.filling2)
        fillingGroupChck[2] = view.findViewById(R.id.filling3)
        fillingGroupChck[3] = view.findViewById(R.id.filling4)
        fillingGroupChck[4] = view.findViewById(R.id.filling5)

        sideGroupChck[0] = view.findViewById (R.id.side1)
        sideGroupChck[1] = view.findViewById (R.id.side2)
        sideGroupChck[2] = view.findViewById (R.id.side3)
        sideGroupChck[3] = view.findViewById (R.id.side4)

        radiobtn[0] = view.findViewById(R.id.radiobutton1)
        radiobtn[1] = view.findViewById(R.id.radiobutton2)
        radiobtn[2] = view.findViewById(R.id.radiobutton3)

        radiogrp = view.findViewById(R.id.radiogroup)
        fillingTextView = view.findViewById(R.id.filling)
        sideTextView = view.findViewById(R.id.side)

        setPricing()
        sideOnClick()
        fillingOnClick()
        radiogrp.setOnCheckedChangeListener { radioGroup, i -> sizeOnClick(i) }
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
    private fun fillingOnClick() {
        val filling: CompoundButton.OnCheckedChangeListener =
            CompoundButton.OnCheckedChangeListener { cb, b ->
                if(count == 3 && b){
                    cb.isChecked = false
                }else if(b){
                    if (!selectedFillingList.containsKey(cb.tag)) {
                        count++
                        total -= fillingtotal
                        for (i in fillingmap) {
                            if (i.key == cb.tag) {
                                selectedFillingList[i.key] = i.value
                            }
                        }
                        fillingtotal = selectingRule (selectedFillingList, fillingmap)
                    }
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
        val side: CompoundButton.OnCheckedChangeListener =
            CompoundButton.OnCheckedChangeListener { cb, b ->
                if(b) {
                    if (!selectedSideList.containsKey(cb.tag)) {
                        total += sidetotal
                        for (i in sidemap) {
                            if (i.key == cb.tag) {
                                selectedSideList[i.key] = i.value
                            }
                        }
                        sidetotal = selectingRule (selectedSideList, sidemap)
                    }
                } else if(!b) {
                    total -= sidetotal
                    selectedSideList.remove(cb.tag)
                    sidetotal = selectingRule (selectedSideList, sidemap)
                }
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
        return ttl
    }
    private fun buttonClickMethod(view: View) {
        val dialogBuilder = AlertDialog.Builder(view.context)
        var message = ""
        var r = false
        for (i in radiobtn.indices) {
            if (radiobtn[i]?.isChecked == true) {
                r = true
                selectedSize = SelectedRadio(sizemap.keys.elementAt(i), sizemap.values.elementAt(i))
            }
        }
        if (r && selectedFillingList.size > 0) {
            callBack.passData(selectedFillingList, selectedSideList, selectedSize, total)
        } else {
            message = "You must select at least 1 filling and size!"
            // set message of alert dialog
            dialogBuilder.setMessage(message)
                // if the dialog is cancelable
                .setCancelable(false)
                // negative button text and action
                .setNegativeButton("Return", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })
            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Your Order")
            // show alert dialog
            alert.show()
        }
    }
}