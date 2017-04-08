package com.example.nguyennam.financialbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nguyennam.financialbook.model.Income;

import java.util.ArrayList;
import java.util.List;

import static com.example.nguyennam.financialbook.database.DatabaseHandler.IncomeColumn.KEY_AMOUNTMONEY;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.IncomeColumn.KEY_DESCRIPTION;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.IncomeColumn.KEY_INCOMECATEGORY;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.IncomeColumn.KEY_DATE;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.IncomeColumn.KEY_EVENT;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.IncomeColumn.KEY_ACCOUNTNAME;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.TABLE_INCOME;


public class IncomeDAO {

    Context context;
    DatabaseHandler databaseHandler;

    public IncomeDAO(Context context) {
        this.context = context;
        databaseHandler = new DatabaseHandler(context);
    }

    public void addIncome(Income incomeBEAN) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_AMOUNTMONEY, incomeBEAN.get_amountMoney());
        values.put(KEY_INCOMECATEGORY, incomeBEAN.get_category());
        values.put(KEY_DESCRIPTION, incomeBEAN.get_description());
        values.put(KEY_ACCOUNTNAME, incomeBEAN.get_accountName());
        values.put(KEY_DATE, incomeBEAN.get_date());
        values.put(KEY_EVENT, incomeBEAN.get_event());
        // Inserting Row
        db.insert(TABLE_INCOME, null, values);
        db.close(); // Closing database connection
    }

    public Income getIncome(int id) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        Cursor cursor = db.query(TABLE_INCOME, new String[] { DatabaseHandler.IncomeColumn._ID,
                        KEY_AMOUNTMONEY, KEY_INCOMECATEGORY, KEY_DESCRIPTION,
                KEY_ACCOUNTNAME, KEY_DATE, KEY_EVENT }, DatabaseHandler.IncomeColumn._ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Income incomeBEAN = new Income(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6));
        // return income
        return incomeBEAN;
    }

    public List<Income> getAllIncome() {
        List<Income> incomeList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INCOME;
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Income incomeBEAN = new Income();
                incomeBEAN.set_id(Integer.parseInt(cursor.getString(0)));
                incomeBEAN.set_amountMoney(cursor.getString(1));
                incomeBEAN.set_category(cursor.getString(2));
                incomeBEAN.set_description(cursor.getString(3));
                incomeBEAN.set_accountName(cursor.getString(4));
                incomeBEAN.set_date(cursor.getString(5));
                incomeBEAN.set_event(cursor.getString(6));
                // Adding income to list
                incomeList.add(incomeBEAN);
            } while (cursor.moveToNext());
        }
        // return income list
        return incomeList;
    }

    public int getIncomeCount() {
        String countQuery = "SELECT  * FROM " + TABLE_INCOME;
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    public int updateIncome(Income incomeBEAN) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_AMOUNTMONEY, incomeBEAN.get_amountMoney());
        values.put(KEY_INCOMECATEGORY, incomeBEAN.get_category());
        values.put(KEY_DESCRIPTION, incomeBEAN.get_description());
        values.put(KEY_ACCOUNTNAME, incomeBEAN.get_accountName());
        values.put(KEY_DATE, incomeBEAN.get_date());
        values.put(KEY_EVENT, incomeBEAN.get_event());
        // updating row
        return db.update(TABLE_INCOME, values, DatabaseHandler.IncomeColumn._ID + " = ?",
                new String[] { String.valueOf(incomeBEAN.get_id()) });
    }

    public void deleteIncome(Income incomeBEAN) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        db.delete(TABLE_INCOME, DatabaseHandler.IncomeColumn._ID + " = ?",
                new String[] { String.valueOf(incomeBEAN.get_id()) });
        db.close();
    }
}
