package com.example.exam_project

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class dataAdapter(
    val context: Context,
    val list: ArrayList<datamodel>): RecyclerView.Adapter<dataAdapter.ViewHolder>()
{
        val db= dbhelper(context)
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txt: TextView=itemView.findViewById(R.id.txtDta)
        val edit: Button=itemView.findViewById(R.id.edit)
        val delete: Button=itemView.findViewById(R.id.delete)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.item_list,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val data=list[position]

        holder.txt.text="Name:${data.name}\n Age:${data.age}\n Gender:${data.gender}\n Date:${data.date}\n Time:${data.time}\n Dept:${data.dept}\n "

        holder.edit.setOnClickListener {
            val intent= Intent(context, MainActivity::class.java)
            intent.putExtra("id",data.id)
            intent.putExtra("name",data.name)
            intent.putExtra("age",data.age)
            intent.putExtra("gender",data.gender)
            intent.putExtra("date",data.date)
            intent.putExtra("time",data.time)
            intent.putExtra("dept",data.dept)
            context.startActivity(intent)
        }
        holder.delete.setOnClickListener {
            val result=db.deleteData(data.id)
            if(result){
                Toast.makeText(context,"Deleted data ..", Toast.LENGTH_LONG).show()
                list.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }
}