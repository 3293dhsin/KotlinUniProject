package com.example.task3
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"

class Fragment_two : Fragment(), View.OnClickListener {
    var addOnmap: LinkedHashMap<String, Double> = LinkedHashMap<String, Double>()
    private lateinit var callBack: onClicked
    interface onClicked {
        fun back()
        fun reset()
        fun quit()
    }
    private var selectedFillingList: LinkedHashMap<String, Double>? = null
    private var selectedSideList: LinkedHashMap<String, Double>? = null
    private var selectedSize: SelectedRadio? = null
    private var passedtotal: Double? = null
    private val radiobtn: Array<RadioButton?> = arrayOfNulls<RadioButton>(3)
    private lateinit var radiogrp: RadioGroup
    private lateinit var rm: TextView
    private lateinit var placeOrderBtn: ImageButton
    private lateinit var resetBtn: ImageButton
    private var addontotal = 0.00
    private var total = 0.00
    private lateinit var selectedAddOn: SelectedRadio
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedFillingList = it.getSerializable(ARG_PARAM1) as LinkedHashMap<String, Double>?
            selectedSideList = it.getSerializable(ARG_PARAM2) as LinkedHashMap<String, Double>?
            selectedSize = it.getParcelable(ARG_PARAM3)
            passedtotal = it.getDouble(ARG_PARAM4)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_two, container, false)
        val btn = view.findViewById<Button>(R.id.back)
        initUI(view)
        btn.setOnClickListener {
            callBack.back()
        }
        resetBtn.setOnClickListener {
            callBack.reset()
        }
        callBack = activity as onClicked
        return view
    }
    companion object {
        fun newInstance(param1: LinkedHashMap<String, Double>, param2: LinkedHashMap<String, Double>, param3: SelectedRadio, param4: Double) =
            Fragment_two().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1,param1)
                    putSerializable(ARG_PARAM2, param2)
                    putParcelable(ARG_PARAM3, param3)
                    putDouble(ARG_PARAM4, param4)
                }
            }
    }
    private fun initUI(view: View) {
        radiobtn[0] = view.findViewById(R.id.addon1)
        radiobtn[1] = view.findViewById(R.id.addon2)
        radiobtn[2] = view.findViewById(R.id.addon3)
        radiogrp = view.findViewById(R.id.radiogroup)
        rm = view.findViewById(R.id.rm)
        placeOrderBtn = view.findViewById(R.id.place_order_button)
        resetBtn = view.findViewById(R.id.reset_button)
        setPricing()
        total = 0.00
        total += passedtotal!!
        rm.text = "RM ${String.format("%.2f", total)}"
        radiogrp.setOnCheckedChangeListener { radioGroup, i -> addOnClick(i) }
        placeOrderBtn.setOnClickListener(this)
        resetBtn.setOnClickListener(this)
    }
    private fun setPricing() {
        addOnmap.put("Tea", 3.50)
        addOnmap.put("Coffee", 4.50)
        addOnmap.put("French Fries", 5.00)
        for (i in 0..2) {
            radiobtn[i]?.tag = addOnmap.keys.elementAt(i)
        }
    }
    private fun addOnClick(i: Int) {
        total -= addontotal
        addontotal = 0.00
        when (i) {
            R.id.addon1->{
                addontotal += addOnmap.values.elementAt(0)
                selectedAddOn = SelectedRadio(addOnmap.keys.elementAt(0), addOnmap.values.elementAt(0))
            }
            R.id.addon2->{
                addontotal += addOnmap.values.elementAt(1)
                selectedAddOn = SelectedRadio(addOnmap.keys.elementAt(1), addOnmap.values.elementAt(1))
            }
            R.id.addon3->{
                addontotal += addOnmap.values.elementAt(2)
                selectedAddOn = SelectedRadio(addOnmap.keys.elementAt(2), addOnmap.values.elementAt(2))
            }
        }
        if (selectedFillingList?.size == 3) {
            addontotal -= discount(addontotal)
        }
        total += addontotal
        rm.text = "RM ${String.format("%.2f", total)}"
    }
    private fun discount(i: Double): Double {
        return (i * 20)/100
    }
    override fun onClick(btn: View) {
        when (btn?.id) {
            R.id.place_order_button -> {
                val dialogBuilder = AlertDialog.Builder(btn.context)
                var message = ""
                var f = false
                message += "${selectedSize?.opt}: RM ${selectedSize?.price}" + "\n"
                for (i in 0 until selectedFillingList!!.size) {
                    if (selectedFillingList!!.size > 0) {
                        f = true
                        message += "${selectedFillingList!!.keys.elementAt(i)}: RM ${
                            selectedFillingList!!.values.elementAt(
                                i
                            )
                        }" + "\n"
                    }
                }
                for (i in 0 until selectedSideList!!.size) {
                    if (selectedSideList!!.size > 0) {
                        message += "${selectedSideList!!.keys.elementAt(i)}: RM ${
                            selectedSideList!!.values.elementAt(
                                i
                            )
                        }" + "\n"
                    }
                }
                for (i in radiobtn.indices) {
                    if (radiobtn[i]?.isChecked == true) {
                        if (selectedFillingList?.size == 3) {
                            var dis = discount(selectedAddOn!!.price)
                            message += "${selectedAddOn?.opt}: RM ${selectedAddOn?.price - dis} (-RM ${dis})"
                        } else {
                            message += "${selectedAddOn?.opt}: RM ${selectedAddOn?.price}"
                        }
                    }
                }
                if (f) {
                    message += "\n=====================\n" +
                            "Total: RM $total" +
                            "\n====================="
                    // set message of alert dialog
                    dialogBuilder.setMessage(message)
                        // if the dialog is cancelable
                        .setCancelable(false)
                        // positive button text and action
                        .setPositiveButton("YES", DialogInterface.OnClickListener { dialog, id ->
                            callBack.quit()
                        })
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
                }
                // create dialog box
                val alert = dialogBuilder.create()
                // set title for alert dialog box
                alert.setTitle("Your Order")
                // show alert dialog
                alert.show()
            }
        }
    }
}