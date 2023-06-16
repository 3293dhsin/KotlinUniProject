package com.example.task3
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlin.collections.LinkedHashMap

class MainActivity : AppCompatActivity(), Fragment_one.onButtonClick, Fragment_two.onClicked {
    var fragment1 = Fragment_one()
    var fragment2 = Fragment_two()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView, fragment1).addToBackStack(null).commit()
    }
    override fun passData(
        selectedFillingList: LinkedHashMap<String, Double>,
        selectedSideList: LinkedHashMap<String, Double>,
        selectedSize: SelectedRadio,
        total: Double
    ) {
        val fragmentTwo = Fragment_two.newInstance(selectedFillingList, selectedSideList, selectedSize, total)
        fragment2 = fragmentTwo
        val transaction = this.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragmentTwo)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun back() {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment1).commit()
    }
    override fun reset() {
        fragment1 = Fragment_one()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment1).commit()
    }
    override fun quit() {
        finish()
    }
}