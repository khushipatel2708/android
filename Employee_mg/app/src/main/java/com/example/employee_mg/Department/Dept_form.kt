package com.example.employee_mg.Department

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.employee_mg.Employee.emp_form
import com.example.employee_mg.R
import com.example.employee_mg.dbhelper

class Dept_form : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dept_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val db= dbhelper(this)
        val name=findViewById<EditText>(R.id.name)
        val location=findViewById<EditText>(R.id.location)

        val save=findViewById<Button>(R.id.save)
        val display=findViewById<Button>(R.id.display)

        var updateId =-1
        if(intent.hasExtra("id")){
            updateId=intent.getIntExtra("id",-1)
            name.setText(intent.getStringExtra("name"))
            location.setText(intent.getStringExtra("location"))
        }
        save.setOnClickListener {
            val name=name.text.toString()
            val location=location.text.toString()

            if(updateId == -1){
                val result=db.insertDept(name,location)
                if(result){
                    Toast.makeText(this,"Save data", Toast.LENGTH_LONG).show()
                    val intent= Intent(this, Dept_display::class.java)
                    startActivity(intent)
                }
            }
            else{
                val result=db.updateDept(updateId,name,location)
                if(result){
                    Toast.makeText(this,"update data", Toast.LENGTH_LONG).show()
                    val intent= Intent(this, Dept_display::class.java)
                    startActivity(intent)
                }
            }
        }
        display.setOnClickListener {
            val intent= Intent(this, Dept_display::class.java)
            startActivity(intent)
        }
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