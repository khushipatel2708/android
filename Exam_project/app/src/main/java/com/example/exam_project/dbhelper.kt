package com.example.exam_project

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class dbhelper(context: Context): SQLiteOpenHelper(context,"DataDb",null,1) {
    override fun onCreate(db: SQLiteDatabase) {
        val query="""
            create table data(
                id integer primary key autoincrement,
                name text,
                age text,
                gender text,
                date text,
                time text,
                dept text
            )
        """.trimIndent()
        db.execSQL(query)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("drop table if exists data")
        onCreate(db)
    }

    fun insertData(
        name: String,
        age:String,
        gender:String,
        date:String,
        time:String,
        dept:String
    ): Boolean{
        val db=writableDatabase
        val v= ContentValues()

        v.put("name",name)
        v.put("age",age)
        v.put("gender",gender)
        v.put("date",date)
        v.put("time",time)
        v.put("dept",dept)

        val result=db.insert("data",null,v)
        return result !=-1L
    }

    fun getAllData(): ArrayList<datamodel>{
        val list= ArrayList<datamodel>();
        val db=readableDatabase

        val cursor=db.rawQuery("select * from data",null)

        if(cursor.moveToFirst()){
            do{
                val id=cursor.getInt(0)
                val name=cursor.getString(1)
                val age=cursor.getString(2)
                val gender=cursor.getString(3)
                val date=cursor.getString(4)
                val time=cursor.getString(5)
                val dept=cursor.getString(6)

                list.add(datamodel(id,name,age,gender,date,time,dept))
            }while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun deleteData(id: Int): Boolean{
        val db=writableDatabase
        val result=db.delete("data","id=?",arrayOf(id.toString()))
        return result>0
    }

    fun updateData(
        id:Int,
        name: String,
        age:String,
        gender:String,
        date:String,
        time:String,
        dept:String
    ): Boolean{
        val db=writableDatabase
        val v= ContentValues()

        v.put("name",name)
        v.put("age",age)
        v.put("gender",gender)
        v.put("date",date)
        v.put("time",time)
        v.put("dept",dept)

        val result=db.update("data",v,"id=?",arrayOf(id.toString()))
        return result>0
    }
}