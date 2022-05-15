package com.android.employeemanagmentsystem.ui.registration

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.Role

class RolesAdapter (
    val context: Context,
    val roles: List<Role>
): BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_training_types, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.type.text = roles[position].role_name

        return view
    }

    override fun getItem(position: Int): Any? {
        return roles[position]
    }

    override fun getCount(): Int {
        return roles.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val type: TextView

        init {
            type = row?.findViewById(R.id.tv_training_type) as TextView
        }
    }

}

