package com.example.employee_mg.Employee

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employee_mg.R
import com.example.employee_mg.dbhelper
import android.widget.EditText
import com.example.employee_mg.Department.Dept_display
import com.example.employee_mg.Department.Dept_form

class emp_display : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_emp_display)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val search=findViewById<EditText>(R.id.search)
        val recylerview=findViewById<RecyclerView>(R.id.recylerview)
        val db= dbhelper(this)
        val list=db.getAllEmployee()

        recylerview.layoutManager= LinearLayoutManager(this)
        recylerview.adapter= employeeAdapter(this,list)

        search.addTextChangedListener(object : TextWatcher{
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
                val filter= ArrayList<employeeModel>()

                for(i in list){
                    if(i.name.lowercase().contains(p0.toString().lowercase())){
                        filter.add(i)
                    }
                }
                recylerview.adapter= employeeAdapter(this@emp_display,filter)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.emp->{
                val intent= Intent(this, emp_form::class.java)
                startActivity(intent)
            }
            R.id.dept->{
                val intent= Intent(this, Dept_form::class.java)
                startActivity(intent)
            }
            R.id.logout->{
                val intent= Intent(this, Dept_display::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}