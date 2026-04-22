package com.example.twotable

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProductForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val db= dbhelper(this)
        val pname=findViewById<EditText>(R.id.pname)
        val price=findViewById<EditText>(R.id.price)
        val category=findViewById<EditText>(R.id.category)
        val save=findViewById<Button>(R.id.save)
        val display=findViewById<Button>(R.id.display)

        var updateId = -1

        if(intent.hasExtra("id")){
            updateId = intent.getIntExtra("id",-1)

            pname.setText(intent.getStringExtra("name"))
            price.setText(intent.getStringExtra("price"))

            val catId = intent.getIntExtra("categoryId",-1)
            category.setText(catId.toString())
        }

        save.setOnClickListener {
            val productName = pname.text.toString()
            val productPrice = price.text.toString()
            val categoryId = category.text.toString().toInt()

            if(updateId == -1){
                val result = db.insertProduct(productName,productPrice,categoryId)

                if(result){
                    Toast.makeText(this, "Product Saved", Toast.LENGTH_LONG).show()

                    val intent = Intent(this, DisplayProduct::class.java)
                    startActivity(intent)
                }
            }
            else{
                val result = db.updateProduct(updateId,productName,productPrice,categoryId)

                if(result){
                    Toast.makeText(this, "Product Updated", Toast.LENGTH_LONG).show()

                    val intent = Intent(this, DisplayProduct::class.java)
                    startActivity(intent)
                }
            }
        }

        display.setOnClickListener {
            val intent= Intent(this, DisplayProduct::class.java)
            startActivity(intent)
        }
    }
}