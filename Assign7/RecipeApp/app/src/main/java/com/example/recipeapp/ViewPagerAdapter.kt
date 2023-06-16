package com.example.recipeapp
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter (private val appCompatActivity: AppCompatActivity, private val tabTitle:Array<String>): FragmentStateAdapter(appCompatActivity) {
    override fun getItemCount(): Int = tabTitle.size
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                var loadFragment = OneFragment.newInstance("breakfast")
                return loadFragment
            }
            1 -> {
                var loadFragment = OneFragment.newInstance("lunch")
                return loadFragment
            }
            else -> {
                var loadFragment = OneFragment.newInstance("dinner")
                return loadFragment
            }
        }
    }
}