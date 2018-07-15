package com.example.ankur.inventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ankur on 24-03-2018.
 */

public class ItemDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Inventory.db";
    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + ItemContract.ItemEntry.TABLE_NAME + " (" +
            ItemContract.ItemEntry._ID + " INT, " +
            ItemContract.ItemEntry.COLUMN_INAME + " VARCHAR(30), " +
            ItemContract.ItemEntry.COLUMN_QUANTITY + " INT, "+
            ItemContract.ItemEntry.COLUMN_PRICE + " VARCHAR(20), " +
            ItemContract.ItemEntry.COLUMN_IMAGE + " VARCHAR(100), " +
            ItemContract.ItemEntry.COLUMN_SUPPLIER + " VARCHAR(50))";

    public  ItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + ItemContract.ItemEntry.TABLE_NAME);
        onCreate(db);
    }

    public void insertItem(String itemName, int quantity, String price, String image, String supplier){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ItemContract.ItemEntry.COLUMN_INAME, itemName);
        values.put(ItemContract.ItemEntry.COLUMN_QUANTITY, quantity);
        values.put(ItemContract.ItemEntry.COLUMN_PRICE, price);
        values.put(ItemContract.ItemEntry.COLUMN_IMAGE, image);
        values.put(ItemContract.ItemEntry.COLUMN_SUPPLIER, supplier);
        db.insert(ItemContract.ItemEntry.TABLE_NAME, null ,values);
        db.close();
    }

    public void insertDefault(){
        insertItem("Apple iMac", 10, "1999",
                "android.resource://com.example.ankur.inventory/drawable/mac" , "supplier@gmail.com");
    }

    public int rows(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + ItemContract.ItemEntry.TABLE_NAME,null);
        return c.getCount();
    }

    public void updateItemQuantity(String name, int quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ItemContract.ItemEntry.COLUMN_QUANTITY, quantity);
        String selection = ItemContract.ItemEntry.COLUMN_INAME + "=?";
        String[] selectionArgs = new String[] { name };
        db.update(ItemContract.ItemEntry.TABLE_NAME,
                values, selection, selectionArgs);
        db.close();
    }

    public Cursor read(String name){
        SQLiteDatabase db = getReadableDatabase();
        String proj[] = {ItemContract.ItemEntry._ID,ItemContract.ItemEntry.COLUMN_INAME, ItemContract.ItemEntry.COLUMN_QUANTITY,
                ItemContract.ItemEntry.COLUMN_PRICE, ItemContract.ItemEntry.COLUMN_IMAGE, ItemContract.ItemEntry.COLUMN_SUPPLIER};
        String sel = ItemContract.ItemEntry.COLUMN_INAME + "=?";
        String[] selArg = new String[]{name};
        return db.query(ItemContract.ItemEntry.TABLE_NAME, proj, sel, selArg, null, null, null);
    }

    public void deleteItem(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String table = ItemContract.ItemEntry.TABLE_NAME;
        String whereClause = ItemContract.ItemEntry.COLUMN_INAME + "=?";
        String[] whereArgs = new String[] { String.valueOf(name) };
        db.delete(table, whereClause, whereArgs);
        db.close();
    }

    public Cursor readAllItems(){
        SQLiteDatabase db = getReadableDatabase();
        String proj[] = {ItemContract.ItemEntry._ID,ItemContract.ItemEntry.COLUMN_INAME, ItemContract.ItemEntry.COLUMN_QUANTITY,
                ItemContract.ItemEntry.COLUMN_PRICE, ItemContract.ItemEntry.COLUMN_IMAGE, ItemContract.ItemEntry.COLUMN_SUPPLIER};
        return db.query(ItemContract.ItemEntry.TABLE_NAME, proj, null, null, null, null, null);
    }
}
