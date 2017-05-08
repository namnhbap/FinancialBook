package com.example.nguyennam.financialbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nguyennam.financialbook.model.BudgetRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.nguyennam.financialbook.database.DatabaseHandler.BudgetCollumn.KEY_ACCOUNTID;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.BudgetCollumn.KEY_AMOUNTMONEY;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.BudgetCollumn.KEY_BUDGETNAME;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.BudgetCollumn.KEY_CATEGORY;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.BudgetCollumn.KEY_DATE;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.BudgetCollumn.KEY_EXPENSEMONEY;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.BudgetCollumn.KEY_REMAINMONEY;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.TABLE_BUDGET;


public class BudgetRecyclerViewDAO {

    Context context;
    DatabaseHandler databaseHandler;

    public BudgetRecyclerViewDAO(Context context) {
        this.context = context;
        databaseHandler = new DatabaseHandler(context);
    }

    public void addBudget(BudgetRecyclerView budgetRecyclerView) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BUDGETNAME, budgetRecyclerView.getBudgetName());
        values.put(KEY_AMOUNTMONEY, budgetRecyclerView.getAmountMoney());
        values.put(KEY_ACCOUNTID, budgetRecyclerView.getAccountID());
        values.put(KEY_CATEGORY, budgetRecyclerView.getCategory());
        values.put(KEY_DATE, budgetRecyclerView.getDate());
        values.put(KEY_REMAINMONEY, budgetRecyclerView.getRemainMoney());
        values.put(KEY_EXPENSEMONEY, budgetRecyclerView.getExpenseMoney());
        // Inserting Row
        db.insert(TABLE_BUDGET, null, values);
        db.close(); // Closing database connection
    }

    public BudgetRecyclerView getBudgetById(int id) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();

        Cursor cursor = db.query(TABLE_BUDGET, new String[]{DatabaseHandler.BudgetCollumn._ID,
                        KEY_BUDGETNAME, KEY_AMOUNTMONEY, KEY_ACCOUNTID,
                        KEY_CATEGORY, KEY_DATE, KEY_REMAINMONEY,
                        KEY_EXPENSEMONEY}, DatabaseHandler.BudgetCollumn._ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        BudgetRecyclerView budgetRecyclerView = new BudgetRecyclerView(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7));
        // return budgetRecyclerView
        return budgetRecyclerView;
    }

    public List<BudgetRecyclerView> getAllBudget() {
        List<BudgetRecyclerView> budgetList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BUDGET;

        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BudgetRecyclerView budgetRecyclerView = new BudgetRecyclerView();
                budgetRecyclerView.setId(Integer.parseInt(cursor.getString(0)));
                budgetRecyclerView.setBudgetName(cursor.getString(1));
                budgetRecyclerView.setAmountMoney(cursor.getString(2));
                budgetRecyclerView.setAccountID(Integer.parseInt(cursor.getString(3)));
                budgetRecyclerView.setCategory(cursor.getString(4));
                budgetRecyclerView.setDate(cursor.getString(5));
                budgetRecyclerView.setRemainMoney(cursor.getString(6));
                budgetRecyclerView.setExpenseMoney(cursor.getString(7));
                // Adding account to list
                budgetList.add(budgetRecyclerView);
            } while (cursor.moveToNext());
        }

        // return budgetList
        return budgetList;
    }

    public int getBudgetCount() {
        String countQuery = "SELECT  * FROM " + TABLE_BUDGET;
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    public int updateBudget(BudgetRecyclerView budgetRecyclerView) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BUDGETNAME, budgetRecyclerView.getBudgetName());
        values.put(KEY_AMOUNTMONEY, budgetRecyclerView.getAmountMoney());
        values.put(KEY_ACCOUNTID, budgetRecyclerView.getAccountID());
        values.put(KEY_CATEGORY, budgetRecyclerView.getCategory());
        values.put(KEY_DATE, budgetRecyclerView.getDate());
        values.put(KEY_REMAINMONEY, budgetRecyclerView.getRemainMoney());
        values.put(KEY_EXPENSEMONEY, budgetRecyclerView.getExpenseMoney());

        // updating row
        return db.update(TABLE_BUDGET, values, DatabaseHandler.BudgetCollumn._ID + " = ?",
                new String[]{String.valueOf(budgetRecyclerView.getId())});
    }

    public void deleteBudget(BudgetRecyclerView budgetRecyclerView) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        db.delete(TABLE_BUDGET, DatabaseHandler.BudgetCollumn._ID + " = ?",
                new String[]{String.valueOf(budgetRecyclerView.getId())});
        db.close();
    }

    public void deleteAllBudget() {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        db.delete(TABLE_BUDGET, null, null);
        db.close();
    }
}
