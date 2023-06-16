package com.example.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView
    private lateinit var allitmList: ArrayList<item>
    private lateinit var urgentitmList: ArrayList<item>
    private lateinit var itemBoughtList: ArrayList<item>
    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DBHelper(this)
        dbHelper.deleteAll()
        populateDB(dbHelper)

        // get from the database
        allitmList = dbHelper.getNonBoughtList()
        urgentitmList = dbHelper.getUrgentList()
        itemBoughtList = dbHelper.getItemBought()

        loadFragment(HomeFragment.newInstance(allitmList))

        //Initialize the bottom navigation view
        //create bottom navigation view object
        bottomNav = findViewById(R.id.bottmNav)

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.urgentFragment -> {
                    loadFragment(UrgentFragment.newInstance(urgentitmList))
                    supportActionBar?.title = "Urgent Page"
                    true
                }
                R.id.homeFragment2 -> {
                    loadFragment(HomeFragment.newInstance(allitmList))
                    supportActionBar?.title = "Shopping List"
                    true
                }
                R.id.completedFragment -> {
                    loadFragment(CompletedFragment.newInstance(itemBoughtList))
                    supportActionBar?.title = "Items bought"
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun populateDB (dbHelper: DBHelper) {
        dbHelper.insertItem(item(R.drawable.buy, "Chocolate Bar", " ", "1", "Default", ""))
        dbHelper.insertItem(item(R.drawable.urgent, "Bread"," ", "1", "Small", ""))
        dbHelper.insertItem(item(R.drawable.buy, "Instant Noodle"," ", "1", "Default", ""))
        dbHelper.insertItem(item(R.drawable.buy, "Juice", " ", "2", "Large", "12 Aug 2022"))
        dbHelper.insertItem(item(R.drawable.urgent, "Milk", " ", "3", "Large", ""))
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}