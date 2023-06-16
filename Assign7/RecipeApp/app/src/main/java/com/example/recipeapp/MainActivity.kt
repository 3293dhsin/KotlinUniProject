package com.example.recipeapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tabtitles = arrayOf("BREAKFAST", "LUNCH", "DINNER")
        val tablayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewpager = findViewById<ViewPager2>(R.id.view_pager)
        viewpager.adapter = ViewPagerAdapter(this, tabtitles)
        TabLayoutMediator(tablayout, viewpager) {
            tab, position -> tab.text = tabtitles[position]
        }.attach()
    }
}