package com.example.employee_mg

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.employee_mg.Department.deptAdapter
import com.example.employee_mg.Department.deptModel
import com.example.employee_mg.Employee.employeeModel

class dbhelper(context: Context): SQLiteOpenHelper(context,"employeedb",null,2) {
    override fun onCreate(db: SQLiteDatabase) {
        val dept="""
            create table dept(
                id integer primary key autoincrement,
                name text,
                location text
            )
        """.trimIndent()
        val emp="""
            create table emp(
                id integer primary key autoincrement,
                name text,
                gender text,
                date text,
                deptId int,
                foreign key (deptId) references dept(id)
            )
        """.trimIndent()

        db.execSQL(dept)
        db.execSQL(emp)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("drop table if exists dept")
        db.execSQL("drop table if exists emp")
        onCreate(db)
    }

    fun insertEmployee(
        name: String,
        gender: String,
        date:String,
        deptId: Int
    ): Boolean{
        val db=writableDatabase
        val v= ContentValues()

        v.put("name",name)
        v.put("gender",gender)
        v.put("date",date)
        v.put("deptId",deptId)

        val result=db.insert("emp",null,v)
        return result !=-1L
    }

    fun updateEmployee(
        id: Int,
        name: String,
        gender: String,
        date:String,
        deptId: Int
    ): Boolean{
        val db=writableDatabase
        val v= ContentValues()

        v.put("name",name)
        v.put("gender",gender)
        v.put("date",date)
        v.put("deptId",deptId)

        val result=db.update("emp",v,"id=?",arrayOf(id.toString()))
        return result>0
    }

    fun deleteEmployee(id:Int): Boolean{
        val db=writableDatabase
        val result=db.delete("emp","id=?",arrayOf(id.toString()))
        return result>0
    }

    fun getAllEmployee(): ArrayList<employeeModel>{
        val db= readableDatabase
        val list= ArrayList<employeeModel>();

        val query="""
            select emp.id,emp.name,emp.gender,emp.date,emp.deptId,dept.name
            from emp
            inner join dept
            on emp.deptId=dept.id
        """.trimIndent()
        val cursor=db.rawQuery(query,null)

        if(cursor.moveToFirst()){
            do{
                val id=cursor.getInt(0)
                val name=cursor.getString(1)
                val gender=cursor.getString(2)
                val date=cursor.getString(3)
                val deptId=cursor.getInt(4)
                val deptName=cursor.getString(5)

                list.add(employeeModel(id,name,gender,date,deptId,deptName))

            }while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }
    fun getAllDept(): ArrayList<deptModel>{
        val list= ArrayList<deptModel>();
        val db=readableDatabase

        val cursor=db.rawQuery("select * from dept",null)

        if(cursor.moveToFirst()){
            do{
                val id=cursor.getInt(0)
                val name=cursor.getString(1)
                val location=cursor.getString(2)

                list.add(deptModel(id,name,location))
            }while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }
    fun deleteDept (id: Int): Boolean{
        val db=writableDatabase
        val result=db.delete("dept","id=?",arrayOf(id.toString()))
        return result >0
    }


    fun updateDept(
        id:Int,
        name:String,
        location:String
    ): Boolean{
        val db=writableDatabase
        val v= ContentValues()

        v.put("name",name)
        v.put("location",location)
        val result=db.update("dept",v,"id=?",arrayOf(id.toString()))
        return result >0
    }

    fun insertDept(
        name:String,
        location:String
    ): Boolean{
        val db=writableDatabase
        val v= ContentValues()

        v.put("name",name)
        v.put("location",location)
        val result=db.insert("dept",null,v)
        return result != -1L
    }


}