package com.example.twotable

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class dbhelper(context: Context): SQLiteOpenHelper(context,"datadb",null,2) {
    override fun onCreate(db: SQLiteDatabase) {
        val category="""
            create table category(
                id integer primary key autoincrement,
                name text
            )
        """.trimIndent()
        val product="""
            create table product(
                id integer primary key autoincrement,
                name text,
                price text,
                categoryId int,
                foreign key (categoryId) references category(id)
            )
        """.trimIndent()
        db.execSQL(category)
        db.execSQL(product)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists product")
        db.execSQL("drop table if exists category")
        onCreate(db)
    }

    fun insertCategory(
        name: String
    ): Boolean{
        val db=writableDatabase
        val v= ContentValues()

        v.put("name",name)
        val result=db.insert("category",null,v)
        return result != -1L
    }

    fun getCategory(): ArrayList<CategoryModel>{
        val list= ArrayList<CategoryModel>();
        val db=readableDatabase

        val cursor=db.rawQuery("select * from category",null)

        if(cursor.moveToFirst()){
            do{
                val id=cursor.getInt(0)
                val name=cursor.getString(1)

                list.add(CategoryModel(id,name))
            }while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun insertProduct(
        name: String,
        price:String,
        categoryId: Int
    ): Boolean{
        val db=writableDatabase
        val v= ContentValues()

        v.put("name",name)
        v.put("price",price)
        v.put("categoryId",categoryId)

        val result=db.insert("product",null,v)
        return result != -1L
    }
    fun getProduct(): ArrayList<ProductModel>{
        val list= ArrayList<ProductModel>();
        val db=readableDatabase

        val query="""
            select product.id,product.name,product.price,product.categoryId,category.name
            from product
            inner join category
            on product.categoryId=category.id
        """.trimIndent()
        val cursor=db.rawQuery(query,null)

        if(cursor.moveToFirst()){
            do{
                val id=cursor.getInt(0)
                val name=cursor.getString(1)
                val price=cursor.getString(2)
                val categoryId=cursor.getInt(3)
                val categoryName=cursor.getString(4)

                list.add(
                    ProductModel(id,name,price,categoryId,categoryName)
                )

            }while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun deleteProduct(id: Int): Boolean{
        val db=writableDatabase
        val result=db.delete("product","id=?",arrayOf(id.toString()))
        return result>0
    }

    fun updateProduct(
        id:Int,
        name: String,
        price:String,
        categoryId: Int,
    ): Boolean{
        val db=writableDatabase
        val v= ContentValues()

        v.put("name",name)
        v.put("price",price)
        v.put("categoryId",categoryId)

        val result=db.update("product",v,"id=?",arrayOf(id.toString()))
        return result>0

    }

}