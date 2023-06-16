package com.example.recipeapp
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

private const val ARG_PARAM1 = "param1"
class DisplayIngredientsFragment : Fragment() {
    private var recipe: Recipe? = null
    private lateinit var label:TextView
    private lateinit var img:ImageView
    private lateinit var ingredientsline:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipe = it.getParcelable(ARG_PARAM1)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_display_ingredients, container, false)
        initUI(view)
        label.text = recipe?.label
        displayImage(img)
        ingredientsline.text = "\u2022 " + recipe?.ingredientLines?.let { TextUtils.join("\n\u2022 ", it) }
        return view
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: Recipe) =
            DisplayIngredientsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                }
            }
    }
    fun initUI(view: View) {
        label = view.findViewById(R.id.label_txtview)
        img = view.findViewById(R.id.imageView3)
        ingredientsline = view.findViewById(R.id.ingredientsline_txtview)
    }
    private fun displayImage(imageview: ImageView) {
        Glide.with(this)
            .load("" + recipe?.images?.REGULAR?.url)
            .override(800,800)
            .into(imageview)
    }
}