package com.example.exam_demo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class dbhelper(context: Context): SQLiteOpenHelper(context,"datadb",null,1) {
    override fun onCreate(db: SQLiteDatabase) {
        val query="""
            create table data(
                id integer primary key autoincrement,
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
        gender: String,
        date:String,
        time:String,
        dept:String,
    ): Boolean{
        val db=writableDatabase
        val v= ContentValues()

        v.put("gender",gender)
        v.put("date",date)
        v.put("time",time)
        v.put("dept",dept)

        val result=db.insert("data",null,v)
        return result != -1L
    }

    fun getAllData(): ArrayList<DataModel>{
        val list= ArrayList<DataModel>();
        val db=readableDatabase

        val  cursor=db.rawQuery("select * from data",null)
        if(cursor.moveToFirst()){
            do{
                val id=cursor.getInt(0)
                val  gender=cursor.getString(1)
                val  date=cursor.getString(2)
                val  time=cursor.getString(3)
                val  dept=cursor.getString(4)

                list.add(DataModel(id,gender,date,time,dept))
            }while (cursor.moveToNext())
        }
        cursor.close()
        return  list
    }

    fun DeleteData(id:Int): Boolean{
        val db=writableDatabase
        val result=db.delete("data","id=?",arrayOf(id.toString()))
        return result>0
    }
    fun UpdateData(
        id: Int,
        gender: String,
        date:String,
        time:String,
        dept:String,
    ): Boolean{
        val db=writableDatabase
        val v= ContentValues()

        v.put("gender",gender)
        v.put("date",date)
        v.put("time",time)
        v.put("dept",dept)

        val result=db.update("data",v,"id=?",arrayOf(id.toString()))
        return result>0
    }
}