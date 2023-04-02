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
import androidx.navigation.fragment.findNavController
import com.example.inventory.MultiSelectSpinnerAdapter
import com.example.inventory.databinding.FragmentRecipeHomeBinding
import com.example.inventory.databinding.ItemListFragmentBinding


/**
 * A simple [Fragment] subclass.
 * Use the [RecipeHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeHomeFragment : Fragment() {

//    private var _binding: FragmentRecipeHomeBinding? = null
//    private val binding get() = _binding!!
    private lateinit var spinner: Spinner

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
        val options = listOf("Option 1", "Option 2", "Option 3", "Option 4")
        val adapter = MultiSelectSpinnerAdapter(requireContext(), options)
        spinner.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = view.findViewById(R.id.ingredients_selected_for_recipe_search)

        val submit: Button = view.findViewById(R.id.testingSubmit)
        submit.setOnClickListener {
            val itemms = getSelectedItems()
            for (item in itemms) {
                Log.d("item", item)
            }
            Log.d("lol", "lool")
        }

    }





//    testingSubmit
    fun getSelectedItems(): List<String> {
        return (spinner.adapter as MultiSelectSpinnerAdapter).getSelectedItems()
    }
}