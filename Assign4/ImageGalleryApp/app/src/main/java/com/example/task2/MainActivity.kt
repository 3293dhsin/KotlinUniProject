package com.example.task2
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), ChckBoxList {
    private var imageList = ArrayList<image>()
    lateinit var imageview: ImageView
    lateinit var recyclerView: RecyclerView
    lateinit var deletebutton: ImageButton
    lateinit var undobutton: ImageButton
    private var tmp = ArrayList<image>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageview = findViewById(R.id.imageView)
        getImgThumbList()
        recyclerView = findViewById(R.id.recyclerview)
        deletebutton = findViewById(R.id.removeButton)
        undobutton = findViewById(R.id.undoButton)
        lateinit var pre : ArrayList<image>
        deletebutton.setOnClickListener {
            // create a temporary array to store list after removing the selected checkboxes
            val tmpt = ArrayList<image>()
            // add the initial image list into 'tmpt'
            for (i in imageList) {
                tmpt.add(i)
            }
            // remove all the selected checkboxes
            for (i in tmp) {
                tmpt.remove(i)
            }

            // create an array for storing previous array list (undo list)
            pre = ArrayList<image>()
            // add the initial image list into 'pre'
            for (i in imageList) {
                pre.add(i)
            }

            // imageList and tmpt are pointing to the same
            imageList = tmpt
            recyclerview()
        }

        undobutton.setOnClickListener {
            //
            var tmpt = ArrayList<image>()
            for (i in pre) {
                tmpt.add(i)
            }
            imageList = tmpt
            recyclerview()
        }

    }

    private fun getImgThumbList() {

        val imageApi = RetrofitHelper.getInstance().create(ImageApi::class.java).getImages()
        Log.d("imageapi", "!1111")

        // create an asynchronous call for the network request
        imageApi.enqueue(object : Callback<ArrayList<image>?> {

            // if successful:
            override fun onResponse(call: Call<ArrayList<image>?>, response: Response<ArrayList<image>?>) {
                if (response.body() != null) {  // if there is data inside
                    imageList = response.body()!!
                    recyclerview()
                }
            }

            // if failed:
            override fun onFailure(call: Call<ArrayList<image>?>, t: Throwable) {
                t.stackTrace  // print error self
                println("failed bacause \n\n" + t.message + "\n\n" + t.cause)
            }

        })
    }

    private fun recyclerview() {
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = imageList?.let { RecyclerViewAdapter(this, it, this) }
    }

    // it will be called everytime checkbox is clicked
    override fun onCheckedChange(arrayList: ArrayList<image>) {
        tmp = arrayList
    }

}