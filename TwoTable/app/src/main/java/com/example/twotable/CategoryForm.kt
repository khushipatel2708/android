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

class CategoryForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_category_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val db= dbhelper(this)
        val name=findViewById<EditText>(R.id.name)
        val save=findViewById<Button>(R.id.save)
        val display=findViewById<Button>(R.id.display)

        save.setOnClickListener {
            val name=name.text.toString()
            val result=db.insertCategory(name)

            if(result){
                Toast.makeText(this,"Category add", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this,"Error", Toast.LENGTH_LONG).show()
            }
        }
        display.setOnClickListener {
            val intent= Intent(this, DisplayCategory::class.java)
            startActivity(intent)
        }
    }
}