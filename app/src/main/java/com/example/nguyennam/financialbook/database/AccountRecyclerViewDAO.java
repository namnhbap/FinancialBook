package com.example.nguyennam.financialbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.nguyennam.financialbook.model.AccountRecyclerView;
import java.util.ArrayList;
import java.util.List;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.AccountColumn.KEY_BALANCE;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.AccountColumn.KEY_NAME;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.TABLE_ACCOUNTS;


public class AccountRecyclerViewDAO {

    Context context;
    DatabaseHandler databaseHandler;

    public AccountRecyclerViewDAO(Context context) {
        this.context = context;
        databaseHandler = new DatabaseHandler(context);
    }

    public void addAccount(AccountRecyclerView accountRecyclerView) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, accountRecyclerView.getAccountType());
        values.put(KEY_BALANCE, accountRecyclerView.getAmountMoney());
        // Inserting Row
        db.insert(TABLE_ACCOUNTS, null, values);
        db.close(); // Closing database connection
    }

    public AccountRecyclerView getAccountById(int id) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();

        Cursor cursor = db.query(TABLE_ACCOUNTS, new String[]{DatabaseHandler.AccountColumn._ID,
                        KEY_NAME, KEY_BALANCE}, DatabaseHandler.AccountColumn._ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        AccountRecyclerView accountRecyclerView = new AccountRecyclerView(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Long.parseLong(cursor.getString(2)));
        // return accountRecyclerView
        return accountRecyclerView;
    }

    public List<AccountRecyclerView> getAllAccount() {
        List<AccountRecyclerView> accountList = new ArrayList<AccountRecyclerView>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ACCOUNTS;

        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AccountRecyclerView accountRecyclerView = new AccountRecyclerView();
                accountRecyclerView.setId(Integer.parseInt(cursor.getString(0)));
                accountRecyclerView.setAccountType(cursor.getString(1));
                accountRecyclerView.setAmountMoney(Long.parseLong(cursor.getString(2)));
                // Adding account to list
                accountList.add(accountRecyclerView);
            } while (cursor.moveToNext());
        }

        // return accountList
        return accountList;
    }

    public int getAccountsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ACCOUNTS;
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    public int updateAccount(AccountRecyclerView accountRecyclerView) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, accountRecyclerView.getAccountType());
        values.put(KEY_BALANCE, accountRecyclerView.getAmountMoney());

        // updating row
        return db.update(TABLE_ACCOUNTS, values, DatabaseHandler.AccountColumn._ID + " = ?",
                new String[]{String.valueOf(accountRecyclerView.getId())});
    }

    public void deleteAccount(AccountRecyclerView accountRecyclerView) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        db.delete(TABLE_ACCOUNTS, DatabaseHandler.AccountColumn._ID + " = ?",
                new String[]{String.valueOf(accountRecyclerView.getId())});
        db.close();
    }
}
