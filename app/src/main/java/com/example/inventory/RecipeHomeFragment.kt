package com.example.inventory

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.inventory.MultiSelectSpinnerAdapter
import com.example.inventory.data.Item
import com.example.inventory.databinding.FragmentRecipeHomeBinding
import com.example.inventory.databinding.ItemListFragmentBinding

class RecipeHomeFragment : Fragment() {

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao(),
            (activity?.application as InventoryApplication).database.labelDao()
        )
    }
    private lateinit var spinner: Spinner
    private lateinit var itemNameList: List<String>
    val ENDPOINT = "https://api.spoonacular.com/recipes/findByIngredients"
    val APIKEY = "33dde6e60fde4a11bc1040e239a4fdb3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_home, container, false)
        spinner = view.findViewById(R.id.ingredients_selected_for_recipe_search)

        val itemLiveData: LiveData<List<String>> = viewModel.getAllUniqueNames()
        itemLiveData.observe(viewLifecycleOwner) { names ->
            itemNameList = listOf("Select All") + names
            val adapter = MultiSelectSpinnerAdapter(requireContext(), itemNameList)
            spinner.adapter = adapter
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner: Spinner = view.findViewById(R.id.ingredients_selected_for_recipe_search)

        val submit: Button = view.findViewById(R.id.findRecipes)
        submit.setOnClickListener {
            val url = constructAPIQuery(getSelectedItems())
            val bundle = bundleOf("url" to url)
            findNavController().navigate(R.id.recipeListFragment, bundle)

        }

    }
    private fun getSelectedItems(): List<String> {
        var userSelectedItems : MutableList<String> = ((spinner.adapter as MultiSelectSpinnerAdapter).getSelectedItems()).toMutableList()
        userSelectedItems.remove("Select All")
        return userSelectedItems
    }

     fun constructAPIQuery(ingredients : List<String>): String {
         val url = "$ENDPOINT?apiKey=$APIKEY&ingredients=${ingredients.joinToString(",+")}"
         Log.d("URL", url)
         return (url)
     }
}