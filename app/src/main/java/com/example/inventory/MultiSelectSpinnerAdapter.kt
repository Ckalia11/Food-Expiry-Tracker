package com.example.inventory
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class MultiSelectSpinnerAdapter(private val context: Context, private val items: List<String>) :
    BaseAdapter(), SpinnerAdapter {

    private val selectedItems = mutableListOf<String>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.multi_select_spinner_item, parent, false)

        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        val textView = view.findViewById<TextView>(R.id.textView)

        textView.text = items[position]

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedItems.add(items[position])
            } else {
                selectedItems.remove(items[position])
            }
        }

        checkBox.isChecked = selectedItems.contains(items[position])

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent)
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getSelectedItems(): List<String> {
        return selectedItems
    }
}


//class MultiSelectSpinnerAdapter(
//    context: Context,
//    private val resourceLayout: Int,
//    private val items: List<String>
//) : ArrayAdapter<String>(context, resourceLayout, items) {
//
//    private class ViewHolder {
//        lateinit var text: TextView
//        lateinit var checkbox: CheckBox
//    }
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val view: View
//        val holder: ViewHolder
//
//        if (convertView == null) {
//            view = LayoutInflater.from(context).inflate(resourceLayout, parent, false)
//            holder = ViewHolder()
//            holder.text = view.findViewById(R.id.text)
//            holder.checkbox = view.findViewById(R.id.checkbox)
//            view.tag = holder
//        } else {
//            view = convertView
//            holder = convertView.tag as ViewHolder
//        }
//
//        val item = items[position]
//        holder.text.text = item
//        holder.checkbox.isChecked = false
//
//        return view
//    }
//}