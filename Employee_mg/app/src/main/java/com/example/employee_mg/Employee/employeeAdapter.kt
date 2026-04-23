package com.example.employee_mg.Employee

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.employee_mg.R
import com.example.employee_mg.dbhelper


class employeeAdapter(val context: Context,val list: ArrayList<employeeModel>): RecyclerView.Adapter<employeeAdapter.ViewHolder>(){

    val db= dbhelper(context)

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txt: TextView=itemView.findViewById(R.id.txtData)
        val edit: Button=itemView.findViewById(R.id.edit)
        val delete: Button=itemView.findViewById(R.id.delete)
    }

    override fun getItemCount(): Int {
        return  list.size
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
        holder.txt.text=
            "Name:${data.name}\n Gender:${data.gender}\n Date:${data.date}\n DeptName:${data.deptName}\n"

        holder.edit.setOnClickListener {
            val intent= Intent(context, emp_form::class.java)
            intent.putExtra("id",data.id)
            intent.putExtra("name",data.name)
            intent.putExtra("gender",data.gender)
            intent.putExtra("date",data.date)
            intent.putExtra("deptId",data.deptId)

            context.startActivity(intent)
        }
        holder.delete.setOnClickListener {

            AlertDialog.Builder(context)
                .setTitle("Delete Confirmation")
                .setMessage("Are you sur to delete date?")
                .setPositiveButton("yes"){_,_->
                    val result=db.deleteEmployee(data.id)
                    if(result){
                        list.removeAt(position)
                        notifyItemRemoved(position)
                    }
                }
                .setNegativeButton("No",null)
                .show()

        }
    }
}