package com.example.employee_mg.Department

import com.example.employee_mg.R
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.employee_mg.dbhelper

class deptAdapter(val context: Context,val list: ArrayList<deptModel>): RecyclerView.Adapter<deptAdapter.ViewHolder>(){

    val db= dbhelper(context)

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txt: TextView=itemView.findViewById(R.id.txtData)
        val edit: Button=itemView.findViewById(R.id.edit)
        val delete: Button=itemView.findViewById(R.id.delete)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): deptAdapter.ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.item_main,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: deptAdapter.ViewHolder, position: Int) {
        val data=list[position]
         holder.txt.text=
             "Id:${data.id}\n Name:${data.name}\n Location:${data.location}"

         holder.edit.setOnClickListener {
            val intent= Intent(context, Dept_form::class.java)
             intent.putExtra("id",data.id)
             intent.putExtra("name",data.name)
             intent.putExtra("location",data.location)

             context.startActivity(intent)
         }
         holder.delete.setOnClickListener {
            val result=db.deleteDept(data.id)
             if (result){
                 list.removeAt(position)
                 notifyItemRemoved(position)
                 Toast.makeText(context,"delete data", Toast.LENGTH_LONG).show()
             }

         }
    }
}