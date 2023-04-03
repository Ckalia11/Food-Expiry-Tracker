package com.example.inventory
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class MultiSelectSpinnerAdapter(
    private val context: Context,
    private val itemList: List<String>,
) : BaseAdapter() {

    private val selectedItems = mutableListOf<String>()

    init {
        selectedItems.addAll(itemList)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.multi_select_spinner_item, parent, false)
        val checkBox = view.findViewById<CheckBox>(R.id.recipeDropdownCheckbox)
        val textView = view.findViewById<TextView>(R.id.recipeDropdownTextView)

        if (itemList[position] == "Select All") {
            textView.text = "Select/Deselect All"
            checkBox.isChecked = selectedItems.size == itemList.size
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedItems.clear()
                    selectedItems.addAll(itemList)
                } else {
                    selectedItems.clear()
                }
                notifyDataSetChanged()
            }
        } else {
            val selectAllCheckbox = parent?.getChildAt(0)?.findViewById<CheckBox>(R.id.recipeDropdownCheckbox)
            textView.text = itemList[position]
            checkBox.isChecked = selectedItems.contains(itemList[position])
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (!selectedItems.contains(itemList[position])) {
                        selectedItems.add(itemList[position])
                    }
                } else {
                    selectedItems.remove(itemList[position])
                }
                // Uncheck "Select All" checkbox if any other checkbox is unchecked
                // TODO: This is kinda buggy right now. Perhaps pass in a parameter to the onclicklistener
                // To determine where the call is coming from. If it's coming from here then we simply
                // Uncheck the "Select All" button but then we DON'T trigger the array.clear()
                if (!isChecked && selectedItems.size >= itemList.size - 1) {
                    selectAllCheckbox?.isChecked = false
                }
            }
            }

            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
            return getView(position, convertView, parent)
        }

        override fun getItem(position: Int): Any {
            return itemList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return itemList.size
        }

        fun getSelectedItems(): List<String> {
            return selectedItems
        }
    }
