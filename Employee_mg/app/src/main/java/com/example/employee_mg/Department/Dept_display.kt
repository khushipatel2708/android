package com.example.employee_mg.Department

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employee_mg.Employee.emp_form
import com.example.employee_mg.R
import com.example.employee_mg.dbhelper

class Dept_display : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dept_display)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val recyclerview=findViewById<RecyclerView>(R.id.recylerview)
        val db= dbhelper(this)

        val list=db.getAllDept()

        recyclerview.layoutManager= LinearLayoutManager(this)
        recyclerview.adapter= deptAdapter(this, list)
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