package com.example.task2
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide

class MainActivity2 : AppCompatActivity() {
    lateinit var imageview: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        var image = intent.getStringExtra("image")
        imageview = findViewById(R.id.imageview2)
        if (image != null) {
            displayImage(image)
        }
    }
    private fun displayImage(image: String) {
        Glide.with(this)
            .load("http://192.168.0.172/android/img/" + image)
            .override(1000,1000)
            .into(imageview)
    }
}