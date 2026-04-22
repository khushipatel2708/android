package com.example.exam_demo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class dataAdapter(val context: Context,
                  val  list: ArrayList<DataModel>):
    RecyclerView.Adapter<dataAdapter.ViewHolder> (){

    val  db= dbhelper(context)

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtData: TextView=itemView.findViewById(R.id.txtData)
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
       val view= LayoutInflater.from(context).inflate(R.layout.item_main,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val data=list[position]
        holder.txtData.text=
            "Gender:${data.gender}\nDate:${data.date}\nTime:${data.time}\nDept:${data.dept}"

        holder.edit.setOnClickListener {
            val intent= Intent(context, Form_activity::class.java)
            intent.putExtra("id",data.id)
            intent.putExtra("gender",data.gender)
            intent.putExtra("date",data.date)
            intent.putExtra("time",data.time)
            intent.putExtra("dept",data.dept)
            context.startActivity(intent)
        }
        holder.delete.setOnClickListener {
            val result=db.DeleteData(data.id)
            if(result){
                Toast.makeText(context,"Delete Data", Toast.LENGTH_LONG).show()
                list.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }
}