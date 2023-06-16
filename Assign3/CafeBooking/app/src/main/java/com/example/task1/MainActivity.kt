package com.example.task1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    companion object {
        const val INTENT_PARCELABLE = "OBJECT_INTENT"
    }

    private var cafeList = ArrayList<Cafe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        populateContactList()
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        // The lambda {} is passed as an argument to the CafeAdapter
        // https://stackoverflow.com/questions/67405648/i-dont-understand-the-argument-passing-of-the-lambda-expression-in-the-listener
        recyclerView.adapter = CafeAdapter(this, cafeList, {
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra(INTENT_PARCELABLE, it)
            startActivity(intent)
        })
    }

    private fun populateContactList () {
        cafeList.add(Cafe(R.drawable.cafe1_icon, R.drawable.cafe1, R.string.cafe1_name, "3415 Lobortis. Avenue", "Opening hours: 10:00 am - 10:00 pm", listOf<String>("Western", "Italian"), "75", 10))
        cafeList.add(Cafe(R.drawable.cafe2_icon, R.drawable.cafe2, R.string.cafe2_name, "430-985 Eleifend St.", "Opening hours: 11:00 am - 9:30 pm", listOf<String>("Chinese", "Japanese", "Malay") , "120", 40))
        cafeList.add(Cafe(R.drawable.cafe3_icon, R.drawable.cafe3, R.string.cafe3_name, "481-8762 Nulla Street", "Opening hours: 9:00 am - 11:00 pm", listOf<String>("Western", "Italian", "Japanese", "Chinese", "Malay") , "30", 100))
        cafeList.add(Cafe(R.drawable.cafe4_icon, R.drawable.cafe4, R.string.cafe4_name, "2136 Adipiscing Av.", "Opening hours: 10:30 am - 10:00 pm",  listOf<String>("Western", "Italian", "Japanese") , "85", 50))
        cafeList.add(Cafe(R.drawable.cafe5_icon, R.drawable.cafe5, R.string.cafe5_name, "43-6527 Purus. Avenue", "Opening hours: 5:00 pm - 10:00 pm",  listOf<String>("Indian", "Malay") , "65", 45))
    }
}