package com.example.shoppinglist
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context): SQLiteOpenHelper(context,DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_NAME="ITMS_REPOSITORY"
        private val DATABASE_VERSION=1
        private val TABLE_NAME="Items"
        private val ID="id"
        private val ICON="icon"
        private val NAME="name"
        private val DETAIL="detail"
        private val QTY="qty"
        private val SIZE="size"
        private val DATE="date"
    }
    override fun onCreate(p0: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $ICON INTEGER, $NAME TEXT, $DETAIL TEXT, $QTY TEXT, $SIZE TEXT, $DATE TEXT DEFAULT ' ')"
        p0?.execSQL(query)
    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        // drop table
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(p0)
    }
    fun insertItem (itm: item)
    {
        val db = this.writableDatabase
        val contentValues=ContentValues()
        contentValues.put(ICON, itm.icon)
        contentValues.put(NAME, itm.name)
        contentValues.put(DETAIL, itm.detail)
        contentValues.put(QTY, itm.qty)
        contentValues.put(SIZE, itm.size)
        contentValues.put(DATE, itm.date)
        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }
    fun editItem (itm: item)
    {
        var arrayList = getNonBoughtList()
        for (i in arrayList) {

            if (i.id == itm.id) {
                var db = this.writableDatabase
                var query = "UPDATE $TABLE_NAME " +
                        "SET $ICON = ${itm.icon}, $NAME = '${itm.name}', $DETAIL = '${itm.detail}', $QTY = '${itm.qty}', $SIZE = '${itm.size}', $DATE = '${itm.date}'" +
                        " WHERE $ID = ${itm.id}"
                db.execSQL(query)
            }
        }
    }
    fun getNonBoughtList (): ArrayList<item> {
        return getListFromDatabase("SELECT * FROM $TABLE_NAME WHERE $ICON IN (${R.drawable.buy}, ${R.drawable.urgent}) ORDER BY $NAME ASC")
    }
    fun getUrgentList (): ArrayList<item> {
        return getListFromDatabase("SELECT * FROM $TABLE_NAME WHERE $ICON = ${R.drawable.urgent} ORDER BY $NAME ASC")
    }
    fun getItemBought (): ArrayList<item> {
        return getListFromDatabase("SELECT * FROM $TABLE_NAME WHERE $ICON = ${R.drawable.bought} ORDER BY $NAME ASC")
    }
    fun getListFromDatabase(query: String): ArrayList<item> {
        var itmList: ArrayList<item> = ArrayList()
        var db = this.readableDatabase
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val id = result.getInt(result.getColumnIndex(ID).toInt())
                val icon = result.getInt(result.getColumnIndex(ICON).toInt())
                val name = result.getString(result.getColumnIndex(NAME).toInt())
                val detail = result.getString(result.getColumnIndex(DETAIL).toInt())
                val qty = result.getString(result.getColumnIndex(QTY).toInt())
                val size = result.getString(result.getColumnIndex(SIZE).toInt())
                val date = result.getString(result.getColumnIndex(DATE).toInt())
                itmList.add(item(id, icon, name, detail, qty, size, date))
            } while (result.moveToNext())
        }
        db.close()
        println(itmList.size)
        return itmList
    }
    fun deleteItem(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "id=?", arrayOf(id.toString()))
        db.close()
    }
    fun deleteAll () {
        val db = this.writableDatabase
        var query = "DELETE FROM $TABLE_NAME"
        db.execSQL(query)
    }
}