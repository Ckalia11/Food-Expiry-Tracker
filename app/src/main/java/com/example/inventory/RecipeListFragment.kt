package com.example.inventory

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class RecipeListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.recipe_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: RecipeListFragmentArgs by navArgs()
        val url = args.url

        getRecipes(url)
    }

    private fun getRecipes(url : String) {
//        val url = "https://api.spoonacular.com/recipes/findByIngredients?apiKey=c08a9abc204a46908523eeddcf170c27&ingredients=apples,+flour,+sugar"
        val apiService = MyApiService()
        apiService.makeApiRequest(url, MyCallback(this))
    }

    class MyApiService {
        fun makeApiRequest(url: String, callback: Callback) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()

            client.newCall(request).enqueue(callback)
        }
    }

    class MyCallback(private val fragment: RecipeListFragment) : Callback {
        private val handler = Handler(Looper.getMainLooper())

        override fun onResponse(call: okhttp3.Call, response: Response) {
            val responseBody = response.body?.string()
            val recipeList = Gson().fromJson(responseBody, Array<Recipe>::class.java)
            handler.post {
                if (recipeList.isEmpty()) {
                    Toast.makeText(fragment.requireContext(), "No results found for ingredients selected.", Toast.LENGTH_LONG).show()
                    val action = RecipeListFragmentDirections.actionRecipeListFragmentToRecipeHomeFragment()
                    fragment.findNavController().navigate(action)
                } else {
                    val recyclerView = fragment.view?.findViewById<RecyclerView>(R.id.recycler_view)
                    val layoutManager = LinearLayoutManager(fragment.context)
                    val adapter = RecipeListAdapter(recipeList)
                    if (recyclerView != null) {
                        recyclerView.layoutManager = layoutManager
                        recyclerView.setHasFixedSize(true)
                        recyclerView.adapter = adapter
                    }
                }
            }
        }

        override fun onFailure(call: okhttp3.Call, e: IOException) {
            // Handle failure
        }
    }

}
