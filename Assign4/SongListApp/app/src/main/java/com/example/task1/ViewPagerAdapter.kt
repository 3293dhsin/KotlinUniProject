package com.example.task1

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter (appCompatActivity: AppCompatActivity, private val tabTitle:Array<String>): FragmentStateAdapter(appCompatActivity) {

    val fileText: List<String> = appCompatActivity.applicationContext.assets.open("song_list.txt").bufferedReader().use {
        it.readLines()
    }
    var song_pop = ArrayList<Song>()
    var song_others = ArrayList<Song>()

    override fun getItemCount(): Int = tabTitle.size

    override fun createFragment(position: Int): Fragment {
        var arr = ArrayList<String>()
        for (i in fileText.indices) {
            // split each line into two elements in the array
            // e.g. value[0] = category
            //      value[1] = pop
            // e.g. value[0] = website
            //      value[1] = https://youtu.be/SlPhMPnQ58k
            var value = fileText[i].split(":",ignoreCase = true, limit=2).toTypedArray()
            // add all the value into an array
            arr.add(value[1])
            if (value[0] == "Artist") {
                if (arr[0] == "Pop") {
                    addSong(song_pop,arr)
                } else {
                    if (song_others.size < 3) {
                        addSong(song_others,arr)
                    }
                }
                arr.clear()
            }
        }

        when (position) {
            0 -> {
                var loadFragment = FragmentOne.newInstance(song_pop)
                return loadFragment
            }
            else -> {
                var loadFragment = FragmentTwo.newInstance(song_others)
                return loadFragment
            }
        }
    }

    fun addSong(song: ArrayList<Song>, arr: ArrayList<String>) {
        var img = 0
        when (arr[1]) {
            "memories.png"-> { img = R.drawable.memories}
            "senorita.png"-> { img = R.drawable.senorita}
            "wild.png" -> { img = R.drawable.wild}
            "beforeyougo.png" -> { img = R.drawable.beforeyougo}
            "daisies.png" -> { img = R.drawable.daisies}
            "idontcare.png" -> { img = R.drawable.idontcare}
            "ihope.png" -> { img = R.drawable.ihope}
            "hardonyourself.png" -> { img = R.drawable.hardonyourself}
        }
        if (arr.size == 7) {
            song.add(Song(arr[0], img, arr[2], arr[3], arr[4], arr[5], arr[6]))
        } else {
            song.add(Song(arr[0], img, arr[2], "none", arr[3], arr[4], arr[5]))
        }
    }
}