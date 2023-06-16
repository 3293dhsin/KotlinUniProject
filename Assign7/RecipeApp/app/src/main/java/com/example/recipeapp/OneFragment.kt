package com.example.recipeapp
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
class OneFragment : Fragment() {
    private var mealtype: String? = null
    private var hits = ArrayList<Hits>()
    private var recipeList = recipeList(hits)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealtype = it.getString(ARG_PARAM1)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment, container, false)
        getImgThumbList()
        return view
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            OneFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
    private fun getImgThumbList() {
        val imageApi = RetrofitHelper.getInstance().create(ImageApi::class.java).getRecipe("public", mealtype,"4d120e0f", "4b5002f7c322e9da28999fa6b17b53ea")
        // create an asynchronous call for the network request
        imageApi.enqueue(object : Callback<recipeList> {
            // if successful:
            override fun onResponse(call: Call<recipeList>, response: Response<recipeList>) {
                if (response.body() != null) {  // if there is data inside
                    recipeList = response.body()!!
                    var pri_hits = ArrayList<Hits>()
                    for (i in 0..7) {
                        hits.add(recipeList.hits[i])
                    }
                    var pri_list = recipeList(hits)
                    recyclerview(pri_list)
                }
            }
            // if failed:
            override fun onFailure(call: Call<recipeList>, t: Throwable) {
                t.stackTrace  // print error self
                println("failed bacause \n\n" + t.message + "\n\n" + t.cause)
            }
        })
    }
    private fun recyclerview(list: recipeList) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        recyclerView?.adapter = list?.let { view?.context?.let { it1 -> RecyclerViewAdapter(it1, it) } }
    }
}