package com.myapps.diegogonzalez.contacts.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Diego Gonzalez on 7/23/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_FIRST_NAME = "fname";
    public static final String CONTACTS_COLUMN_SURNAME = "surname";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    public static final String CONTACTS_COLUMN_EMAIL = "email";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table contacts " +
                        "(id integer primary key AUTOINCREMENT, fname text,surname text,phone text, email text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertContact(String fName, String lName, String phone, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_FIRST_NAME, fName);
        contentValues.put(CONTACTS_COLUMN_SURNAME, lName);
        contentValues.put(CONTACTS_COLUMN_PHONE, phone);
        contentValues.put(CONTACTS_COLUMN_EMAIL, email);
        db.insert("contacts", null, contentValues);
        return true;
    }

    public  HashMap<String, String> getDataById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from contacts where id="+id+"", null );
        res.moveToFirst();
        HashMap<String, String> person = new HashMap<String, String>();
        String fullName;
        fullName = res.getString(res.getColumnIndex(CONTACTS_COLUMN_FIRST_NAME)) + " ";
        fullName += res.getString(res.getColumnIndex(CONTACTS_COLUMN_SURNAME));
        person.put("fullName", fullName);
        person.put("phone", res.getString(res.getColumnIndex(CONTACTS_COLUMN_PHONE)));
        person.put("email", res.getString(res.getColumnIndex(CONTACTS_COLUMN_EMAIL)));
        return person;
    }

    public boolean updateContact(int id, String fName, String lName, String phone, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_FIRST_NAME, fName);
        contentValues.put(CONTACTS_COLUMN_SURNAME, lName);
        contentValues.put(CONTACTS_COLUMN_PHONE, phone);
        contentValues.put(CONTACTS_COLUMN_EMAIL, email);
        db.update("contacts", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public int deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public int getSize() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, "contacts");
    }

    public ArrayList<HashMap<String, String>> getAllCotacts() {
        ArrayList<HashMap<String, String>> contacts = new ArrayList<HashMap<String, String>>();;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            HashMap<String, String> person = new HashMap<String, String>();
            String fullName;
            fullName = res.getString(res.getColumnIndex(CONTACTS_COLUMN_FIRST_NAME)) + " ";
            fullName += res.getString(res.getColumnIndex(CONTACTS_COLUMN_SURNAME));
            person.put("fullName", fullName);
            person.put("phone", res.getString(res.getColumnIndex(CONTACTS_COLUMN_PHONE)));
            person.put("email", res.getString(res.getColumnIndex(CONTACTS_COLUMN_EMAIL)));

            contacts.add(person);
            res.moveToNext();
        }
        return contacts;
    }


}
