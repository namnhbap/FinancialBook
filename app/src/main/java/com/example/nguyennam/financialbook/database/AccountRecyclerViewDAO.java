package com.example.nguyennam.financialbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nguyennam.financialbook.model.AccountRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.nguyennam.financialbook.database.DatabaseHandler.AccountColumn.KEY_ACCOUNTTYPE;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.AccountColumn.KEY_AMOUNTMONEY;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.AccountColumn.KEY_ACCOUNTNAME;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.AccountColumn.KEY_DESCRIPTION;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.AccountColumn.KEY_MONEYSTART;
import static com.example.nguyennam.financialbook.database.DatabaseHandler.AccountColumn.KEY_MONEYTYPE;
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
        values.put(KEY_ACCOUNTNAME, accountRecyclerView.getAccountName());
        values.put(KEY_ACCOUNTTYPE, accountRecyclerView.getAccountType());
        values.put(KEY_MONEYTYPE, accountRecyclerView.getMoneyType());
        values.put(KEY_MONEYSTART, accountRecyclerView.getMoneyStart());
        values.put(KEY_DESCRIPTION, accountRecyclerView.getDescription());
        values.put(KEY_AMOUNTMONEY, accountRecyclerView.getAmountMoney());
        // Inserting Row
        db.insert(TABLE_ACCOUNTS, null, values);
        db.close(); // Closing database connection
    }

    public AccountRecyclerView getAccountById(int id) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();

        Cursor cursor = db.query(TABLE_ACCOUNTS, new String[]{DatabaseHandler.AccountColumn._ID,
                        KEY_ACCOUNTNAME, KEY_ACCOUNTTYPE, KEY_MONEYTYPE,
                        KEY_MONEYSTART, KEY_DESCRIPTION, KEY_AMOUNTMONEY}, DatabaseHandler.AccountColumn._ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        AccountRecyclerView accountRecyclerView = new AccountRecyclerView(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),
                cursor.getString(6));
        // return accountRecyclerView
        return accountRecyclerView;
    }

    public List<AccountRecyclerView> getAllAccount() {
        List<AccountRecyclerView> accountList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ACCOUNTS;

        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AccountRecyclerView accountRecyclerView = new AccountRecyclerView();
                accountRecyclerView.setId(Integer.parseInt(cursor.getString(0)));
                accountRecyclerView.setAccountName(cursor.getString(1));
                accountRecyclerView.setAccountType(cursor.getString(2));
                accountRecyclerView.setMoneyType(cursor.getString(3));
                accountRecyclerView.setMoneyStart(cursor.getString(4));
                accountRecyclerView.setDescription(cursor.getString(5));
                accountRecyclerView.setAmountMoney(cursor.getString(6));
                // Adding account to list
                accountList.add(accountRecyclerView);
            } while (cursor.moveToNext());
        }
        // return accountList
        return accountList;
    }

    public List<AccountRecyclerView> getAccountByAccountType(String accountType) {
        List<AccountRecyclerView> accountList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ACCOUNTS
                + " WHERE " + KEY_ACCOUNTTYPE + " = " + '"' + accountType + '"';
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AccountRecyclerView accountRecyclerView = new AccountRecyclerView();
                accountRecyclerView.setId(Integer.parseInt(cursor.getString(0)));
                accountRecyclerView.setAccountName(cursor.getString(1));
                accountRecyclerView.setAccountType(cursor.getString(2));
                accountRecyclerView.setMoneyType(cursor.getString(3));
                accountRecyclerView.setMoneyStart(cursor.getString(4));
                accountRecyclerView.setDescription(cursor.getString(5));
                accountRecyclerView.setAmountMoney(cursor.getString(6));
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
        values.put(KEY_ACCOUNTNAME, accountRecyclerView.getAccountName());
        values.put(KEY_ACCOUNTTYPE, accountRecyclerView.getAccountType());
        values.put(KEY_MONEYTYPE, accountRecyclerView.getMoneyType());
        values.put(KEY_MONEYSTART, accountRecyclerView.getMoneyStart());
        values.put(KEY_DESCRIPTION, accountRecyclerView.getDescription());
        values.put(KEY_AMOUNTMONEY, accountRecyclerView.getAmountMoney());
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

    public void deleteAllAccount() {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        db.delete(TABLE_ACCOUNTS, null, null);
        db.close();
    }
}
