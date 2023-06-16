package com.example.task1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tabtitles = arrayOf("POP", "OTHERS")
        val tablayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewpager = findViewById<ViewPager2>(R.id.view_pager)

        // link the adapter to the view pager
        // 1st parameter: our activity
        // 2nd parameter: tabtitles
        viewpager.adapter = ViewPagerAdapter(this, tabtitles)

        // synchronize tab and view pager
        // when the tab is clicked, the view pager will load the right fragment
        // using tablayoutmediator
        TabLayoutMediator(tablayout, viewpager) {
                tab, position -> tab.text = tabtitles[position]
        }.attach()
    }
}