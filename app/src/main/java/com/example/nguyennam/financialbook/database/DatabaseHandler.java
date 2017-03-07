package com.example.nguyennam.financialbook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


/**
 * Created by Mac on 1/5/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "financialBook";
    public static final String TABLE_ACCOUNTS = "accounts";
    public static final String TABLE_EXPENSE = "expense";

    class AccountColumn implements BaseColumns {
        static final String KEY_NAME = "name";
        static final String KEY_BALANCE = "balance";
    }

    class ExpenseColumn implements BaseColumns {
        static final String KEY_AMOUNTMONEY = "amountmoney";
        static final String KEY_EXPENSECATEGORY = "expensecategory";
        static final String KEY_DESCRIPTION = "description";
        static final String KEY_FROMACCOUNT = "fromaccount";
        static final String KEY_EXPENSEDATE = "expensedate";
        static final String KEY_EXPENSEEVENT = "expenseevent";
    }

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + TABLE_ACCOUNTS + "("
            + AccountColumn._ID + " INTEGER PRIMARY KEY," + AccountColumn.KEY_NAME + " TEXT,"
            + AccountColumn.KEY_BALANCE + " TEXT" + ")";
    private static final String DROP_ACCOUNTS_TABLE = "DROP TABLE IF EXISTS " + TABLE_ACCOUNTS;
    private static final String CREATE_EXPENSE_TABLE = "CREATE TABLE " + TABLE_EXPENSE + "("
            + ExpenseColumn._ID + " INTEGER PRIMARY KEY," + ExpenseColumn.KEY_AMOUNTMONEY + " TEXT,"
            + ExpenseColumn.KEY_EXPENSECATEGORY + " TEXT," + ExpenseColumn.KEY_DESCRIPTION + " TEXT,"
            + ExpenseColumn.KEY_FROMACCOUNT + " TEXT," + ExpenseColumn.KEY_EXPENSEDATE + " TEXT,"
            + ExpenseColumn.KEY_EXPENSEEVENT + " TEXT" + ")";
    private static final String DROP_EXPENSE_TABLE = "DROP TABLE IF EXISTS " + TABLE_EXPENSE;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_ACCOUNTS_TABLE);
        sqLiteDatabase.execSQL(CREATE_EXPENSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DROP_ACCOUNTS_TABLE);
        sqLiteDatabase.execSQL(DROP_EXPENSE_TABLE);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }


}
