package com.example.twotable

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twotable.CategoryAdaper.ViewHolder

class ProductAdapter(val context: Context,val list: ArrayList<ProductModel>): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
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
    ): ProductAdapter.ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.item_main,parent,false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
       val data = list[position]
        holder.txt.text=
            "Product: ${data.name}\nPrice: ${data.price}\nCategory: ${data.categoryName}"

        holder.edit.setOnClickListener {
            val intent= Intent(context, ProductForm::class.java)
            intent.putExtra("id",data.id)
            intent.putExtra("name",data.name)
            intent.putExtra("price",data.price)
            intent.putExtra("categoryId",data.categoryId)

            context.startActivity(intent)
        }

        holder.delete.setOnClickListener {
            val result = db.deleteProduct(data.id)

            if(result){
                list.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }
}