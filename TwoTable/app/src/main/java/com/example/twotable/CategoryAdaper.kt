package com.example.twotable

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdaper(val context: Context,val list: ArrayList<CategoryModel>): RecyclerView.Adapter<CategoryAdaper.ViewHolder>() {

    val db= dbhelper(context)

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txt: TextView=itemView.findViewById(R.id.txtData)
    }
    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdaper.ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.item_main,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryAdaper.ViewHolder, position: Int) {
        val data=list[position]

        holder.txt.text=
            "Id: ${data.id}\n CategoryName: ${data.name}\n"
    }
}