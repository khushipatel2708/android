package com.example.employee_mg.Employee

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.employee_mg.Department.Dept_display
import com.example.employee_mg.Department.Dept_form
import com.example.employee_mg.R
import com.example.employee_mg.dbhelper
import kotlin.time.Duration.Companion.days

class emp_form : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_emp_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val db= dbhelper(this)
        val name=findViewById<EditText>(R.id.name)
        val gender=findViewById<Spinner>(R.id.gender)
            //date
        val date=findViewById<Button>(R.id.date)
        val disdate=findViewById<TextView>(R.id.disdate)

        val dept=findViewById<Spinner>(R.id.dept)

        val save=findViewById<Button>(R.id.save)
        val display=findViewById<Button>(R.id.display)

        //dept spinner code
        val deptlist=db.getAllDept()

        val deptnames= ArrayList<String>()

        for(i in deptlist){
            deptnames.add(i.name)
        }

        //gender spinner
        val genderval=arrayOf("Male","Female")
        val genadapter= ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            genderval
        )
        gender.adapter=genadapter


        //date piccker
        date.setOnClickListener {
            val calendar= Calendar.getInstance()
            val year=calendar.get(Calendar.YEAR)
            val month=calendar.get(Calendar.MONTH)
            val day=calendar.get(Calendar.DAY_OF_MONTH)
            val datepicker= DatePickerDialog(
                this,
                {
                    _,y,m,d ->
                    disdate.text="${d}/${m+1}/${y}"
                },year,month,day
            )
            datepicker.show()
        }

        val adapter= ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            deptnames
        )
        dept.adapter=adapter
        var updateId = -1

        if(intent.hasExtra("id")){
            updateId=intent.getIntExtra("id",-1)
            name.setText(intent.getStringExtra("name"))

            // ✅ CORRECT gender spinner set
            val genderValue = intent.getStringExtra("gender")
            val position = genadapter.getPosition(genderValue)
            gender.setSelection(position)
            
            disdate.text=intent.getStringExtra("date")


            val deptId=intent.getIntExtra("deptId",-1)
            for(i in deptlist.indices){
                if(deptlist[i].id==deptId){
                    dept.setSelection(i)
                    break
                }
            }
        }

            save.setOnClickListener {
                val name=name.text.toString()
                val genderValue = gender.selectedItem.toString()
                val date=disdate.text.toString()
                val deptId=deptlist[dept.selectedItemPosition]
                val dept=deptId.id

                if(name.isEmpty()){
                    Toast.makeText(this,"All fields are Required", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                if(updateId == -1){
                    var result=db.insertEmployee(name,genderValue,date, dept)

                    if(result){
                        Toast.makeText(this,"Insert data", Toast.LENGTH_LONG).show()
                        val intent= Intent(this, emp_display::class.java)
                        startActivity(intent)
                    }
                }
                else{
                    var result=db.updateEmployee(updateId,name,genderValue,date, dept)

                    if(result){
                        Toast.makeText(this,"Insert data", Toast.LENGTH_LONG).show()
                        val intent= Intent(this, emp_display::class.java)
                        startActivity(intent)
                    }
                }

            }

            display.setOnClickListener {
                val intent= Intent(this, emp_display::class.java)
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