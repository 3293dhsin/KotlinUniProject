package com.example.task1

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class MainActivity2 : AppCompatActivity(), View.OnClickListener, QuantityListener {

    private lateinit var imageView: ImageView
    private lateinit var btnDatePicker: ImageButton
    private lateinit var btnTimePicker:ImageButton
    private lateinit var txtDate: EditText
    private lateinit var txtTime:EditText
    private lateinit var btnArrowUp: ImageButton
    private lateinit var btnArrowDown: ImageButton
    private lateinit var txtNumPeople: TextView
    private var cuisineFullList = ArrayList<String>()
    private var cuisineList = ArrayList<String>()
    private lateinit var reserveBtn: Button
    private var ImageSrc: Int = 0
    private var CafeName: Int = 0
    private var cuisinel = ArrayList<String>()

    private var mHour = 0
    private var mMinute = 0
    private var NumPeople = 0
    private lateinit var calendar: Calendar
    private var MaxSeat = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val cafe = intent.getParcelableExtra<Cafe>(MainActivity.INTENT_PARCELABLE)

        initUI()
        btnDatePicker.setOnClickListener(this)
        btnTimePicker.setOnClickListener(this)
        btnArrowUp.setOnClickListener(this)
        btnArrowDown.setOnClickListener(this)
        reserveBtn.setOnClickListener(this)

        if (cafe != null) {
            imageView.setImageResource(cafe.Image)
            MaxSeat = cafe.maxDinner
            for (i in cuisineFullList) {
                for (j in cafe.cuisine.indices) {
                    if (cafe.cuisine[j] == i) {
                        cuisineList.add(i)
                    }
                }
            }
            ImageSrc = cafe.Image
            CafeName = cafe.Name
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = CuisineAdapter(cuisineList,this)
    }

    private fun initUI() {
        imageView = findViewById(R.id.imageViewCafe)
        btnDatePicker = findViewById(R.id.imageButtonCalendar)
        btnTimePicker = findViewById(R.id.imageButtonClock)
        txtDate = findViewById(R.id.editTextDate)
        txtTime = findViewById(R.id.editTextTime)
        btnArrowUp = findViewById(R.id.imageButtonArrowUp)
        btnArrowDown = findViewById(R.id.imageButtonArrowDown)
        txtNumPeople = findViewById(R.id.textViewNumber)
        reserveBtn = findViewById(R.id.button)

        btnDatePicker.setImageResource(R.drawable.calendar)
        btnTimePicker.setImageResource(R.drawable.clock)
        btnArrowUp.setImageResource(R.drawable.arrow_up)
        btnArrowDown.setImageResource(R.drawable.arrow_down)

        cuisineFullList.add("Western")
        cuisineFullList.add("Italian")
        cuisineFullList.add("Chinese")
        cuisineFullList.add("Japanese")
        cuisineFullList.add("Malay")
        cuisineFullList.add("Indian")
    }

    override fun onClick(v: View?) {

        if (v == btnDatePicker) {

            // Get Current Date
            calendar = Calendar.getInstance()

            val dateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, monthOfYear)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    txtDate.setText("  " + dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                }

            btnDatePicker.setOnClickListener {
                var dialog = DatePickerDialog(
                    this@MainActivity2,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                dialog.datePicker.minDate = Calendar.getInstance().timeInMillis
                dialog.show()
            }
        }

        if (v == btnTimePicker) {

            // Get Current Date
            calendar = Calendar.getInstance()
            mHour = calendar.get(Calendar.HOUR_OF_DAY)
            mMinute = calendar.get(Calendar.MINUTE)

            val timePickerDialog =
                TimePickerDialog.OnTimeSetListener { v, hourOfDay, minute ->
                    txtTime.setText("  $hourOfDay:$minute")
                }

            btnTimePicker.setOnClickListener {
                TimePickerDialog(
                    this@MainActivity2,
                    timePickerDialog,
                    mHour,
                    mMinute,
                    false
                ).show()
            }
        }

        if (v == btnArrowUp && NumPeople < MaxSeat) {
            NumPeople += 1
            txtNumPeople.text = NumPeople.toString()
        }

        if (v == btnArrowDown && NumPeople > 0) {
            NumPeople -= 1
            txtNumPeople.text = NumPeople.toString()
        }

        if (v == reserveBtn) {
            val dialogBuilder = AlertDialog.Builder(this)
            var message = ""

            if (txtDate != null  && txtTime != null && NumPeople > 0) {

                setContentView(R.layout.display_booking)
                val cimage = findViewById<ImageView>(R.id.imageViewCafe)
                val cName = findViewById<TextView>(R.id.textView1)
                val orderTxt=findViewById<TextView>(R.id.bookingList)
                var list = "Date: ${txtDate.text}\nTime: ${txtTime.text}\nNumbers of diners: ${txtNumPeople.text}\n"
                var cuisineArr = "Cuisine: "
                for (i in cuisinel) {
                    cuisineArr = "$cuisineArr$i, "
                }
                list += cuisineArr
                cimage.setImageResource(ImageSrc)
                cName.text = getString(CafeName)

                orderTxt.text = list

            } else {
                message = "You must fill in all the fields."
                // set message of alert dialog
                dialogBuilder.setMessage(message)
                    // if the dialog is cancelable
                    .setCancelable(false)
                    // negative button text and action
                    .setNegativeButton("Return", DialogInterface.OnClickListener {
                            dialog, id -> dialog.cancel()
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

    override fun onQuantityChange(arrayList: ArrayList<String>) {
        cuisinel = arrayList
    }
}


