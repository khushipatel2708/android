package com.example.exam_demo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Form_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db= dbhelper(this)
        val gender: EditText=findViewById(R.id.gender)
        val date: EditText=findViewById(R.id.date)
        val time: EditText=findViewById(R.id.time)
        val dept: EditText=findViewById(R.id.dept)
        val save=findViewById<Button>(R.id.save)
        val display=findViewById<Button>(R.id.display)

        var updateId =-1

        if(intent.hasExtra("id")){
            updateId=intent.getIntExtra("id",-1)
            gender.setText(intent.getStringExtra("gender"))
            date.setText(intent.getStringExtra("date"))
            time.setText(intent.getStringExtra("time"))
            dept.setText(intent.getStringExtra("dept"))
        }

        save.setOnClickListener {
            val gender=gender.text.toString()
            val date=date.text.toString()
            val time=time.text.toString()
            val dept=dept.text.toString()

            if(updateId == -1){
                val result=db.insertData(gender,date,time,dept)
                if(result){
                    Toast.makeText(this,"save data", Toast.LENGTH_LONG).show()
                    val intent= Intent(this, Display_activity::class.java)
                    startActivity(intent)
                }
            }
            else{
                val result=db.UpdateData(updateId,gender,date,time,dept)
                if(result){
                    Toast.makeText(this,"Update data", Toast.LENGTH_LONG).show()
                    val intent= Intent(this, Display_activity::class.java)
                    startActivity(intent)
                }
            }
        }
        display.setOnClickListener {
            val intent= Intent(this, Display_activity::class.java)
            startActivity(intent)
        }
    }
}