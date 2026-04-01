package com.example.exam_project

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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = dbhelper(this)
        val name = findViewById<EditText>(R.id.name)
        val age = findViewById<EditText>(R.id.age)
        val gender = findViewById<RadioGroup>(R.id.gender)
        val date = findViewById<Button>(R.id.date)
        val disDate =findViewById<TextView>(R.id.disDate)
        val time = findViewById<Button>(R.id.time)
        val disTime =findViewById<TextView>(R.id.disTime)
        val dept = findViewById<Spinner>(R.id.dept)
        val save = findViewById<Button>(R.id.save)
        val display = findViewById<Button>(R.id.display)

        //Date picker
        date.setOnClickListener {
            val  calendar= Calendar.getInstance()
            val year=calendar.get(Calendar.YEAR)
            val month=calendar.get(Calendar.MONTH)
            val day=calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker= DatePickerDialog(
                this,
                {
                    _,y,m,d ->
                    disDate.text="$d/${m+1}/$y"
                },year,month,day
            )
            datePicker.show()
        }

        //time picker
        time.setOnClickListener {
            val  calendar= Calendar.getInstance()
            val hour=calendar.get(Calendar.HOUR_OF_DAY)
            val minute=calendar.get(Calendar.MINUTE)

            val timePicker= TimePickerDialog(
                this,
                {
                        _,h,m ->
                    disTime.text="$h:$m"
                },hour,minute,true
            )
            timePicker.show()
        }

        //spinner
        val datalist=arrayOf("it","hr","computer","development")
        val adapter= ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            datalist
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dept.adapter=adapter

        var updatedId =-1

        if(intent.hasExtra("id")){
            updatedId=intent.getIntExtra("id",1)
            name.setText(intent.getStringExtra("name"))
            age.setText(intent.getStringExtra("age"))
            date.setText(intent.getStringExtra("date"))
            time.setText(intent.getStringExtra("time"))

            val deptValue = intent.getStringExtra("dept")
            val position = adapter.getPosition(deptValue)
            dept.setSelection(position)

            val gedentVlaue=intent.getStringExtra("gender")
            if(gedentVlaue == "Male"){
                gender.check(R.id.male)
            }else if(gedentVlaue == "Female"){
                gender.check(R.id.female)
            }
        }

        save.setOnClickListener {
            val name=name.text.toString().trim()
            val age=age.text.toString().trim()
            val date=date.text.toString().trim()
            val disDate=disDate.text.toString().trim()
            val time=time.text.toString().trim()
            val disTime=disTime.text.toString().trim()
            val dept=dept.selectedItem.toString().trim()
            val selected=gender.checkedRadioButtonId
            val genderValue=findViewById<RadioButton>(selected).text.toString()

            if(name.isEmpty() || age.isEmpty() ||date.isEmpty() ||time.isEmpty()||dept.isEmpty()){
                Toast.makeText(this,"All fields are required", Toast.LENGTH_LONG).show()
            }
            if(updatedId == -1){
                val result=db.insertData(name,age,genderValue,disDate,disTime,dept)
                Toast.makeText(this,"insert successfully", Toast.LENGTH_LONG).show()
                val intent = Intent(this, Display_Activity::class.java)
                startActivity(intent)
            }else{
                val result=db.updateData(updatedId,name,age,genderValue,disDate,disTime,dept)
                Toast.makeText(this,"update successfully", Toast.LENGTH_LONG).show()
                val intent = Intent(this, Display_Activity::class.java)
                startActivity(intent)
            }
        }

        display.setOnClickListener {
            val intent = Intent(this, Display_Activity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add->{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.display->{
                val intent = Intent(this, Display_Activity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}