package com.example.exam_project

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Display_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_display)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val search=findViewById<EditText>(R.id.search)
        val recyclerview=findViewById<RecyclerView>(R.id.recyclerview)
        val db= dbhelper(this)

        val list=db.getAllData()
        recyclerview.layoutManager= LinearLayoutManager(this)
        recyclerview.adapter= dataAdapter(this,list)

        search.addTextChangedListener(object :android.text.TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
                val searchText=p0.toString().lowercase()
                val filtedList= ArrayList<datamodel>();

                for(item in list){
                    if(item.name.lowercase().contains(searchText)){
                        filtedList.add(item)
                    }
                }
                recyclerview.adapter= dataAdapter(this@Display_Activity,filtedList)
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.add ->{
                val intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.display ->{
                val intent= Intent(this, Display_Activity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}