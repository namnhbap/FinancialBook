package com.example.nguyennam.financialbook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "financialBook";

    // Table Name
    public static final String TABLE_ACCOUNTS = "accounts";
    public static final String TABLE_EXPENSE = "expense";
    public static final String TABLE_INCOME = "income";
    public static final String TABLE_BUDGET = "budget";

    // Table Column
    class AccountColumn implements BaseColumns {
        static final String KEY_ACCOUNTNAME = "accountname";
        static final String KEY_ACCOUNTTYPE = "accounttype";
        static final String KEY_MONEYTYPE = "moneytype";
        static final String KEY_MONEYSTART = "moneystart";
        static final String KEY_DESCRIPTION = "description";
        static final String KEY_AMOUNTMONEY = "amountmoney";
    }

    class ExpenseColumn implements BaseColumns {
        static final String KEY_AMOUNTMONEY = "amountmoney";
        static final String KEY_EXPENSECATEGORY = "expensecategory";
        static final String KEY_DESCRIPTION = "record_description";
        static final String KEY_ACCOUNTID = "accountid";
        static final String KEY_EXPENSEDATE = "expensedate";
        static final String KEY_EXPENSEEVENT = "expenseevent";
    }

    class IncomeColumn implements BaseColumns {
        static final String KEY_AMOUNTMONEY = "amountmoney";
        static final String KEY_INCOMECATEGORY = "incomecategory";
        static final String KEY_DESCRIPTION = "record_description";
        static final String KEY_ACCOUNTID = "accountid";
        static final String KEY_DATE = "date";
        static final String KEY_EVENT = "event";
    }

    class BudgetCollumn implements BaseColumns {
        static final String KEY_ACCOUNTNAME = "name";
        static final String KEY_AMOUNTMONEY = "balance";
    }

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create & Drop Table
    private static final String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + TABLE_ACCOUNTS + "("
            + AccountColumn._ID + " INTEGER PRIMARY KEY," + AccountColumn.KEY_ACCOUNTNAME + " TEXT,"
            + AccountColumn.KEY_ACCOUNTTYPE + " TEXT," + AccountColumn.KEY_MONEYTYPE + " TEXT,"
            + AccountColumn.KEY_MONEYSTART + " TEXT," + AccountColumn.KEY_DESCRIPTION + " TEXT,"
            + AccountColumn.KEY_AMOUNTMONEY + " TEXT" + ")";
    private static final String DROP_ACCOUNTS_TABLE = "DROP TABLE IF EXISTS " + TABLE_ACCOUNTS;

    private static final String CREATE_EXPENSE_TABLE = "CREATE TABLE " + TABLE_EXPENSE + "("
            + ExpenseColumn._ID + " INTEGER PRIMARY KEY," + ExpenseColumn.KEY_AMOUNTMONEY + " TEXT,"
            + ExpenseColumn.KEY_EXPENSECATEGORY + " TEXT," + ExpenseColumn.KEY_DESCRIPTION + " TEXT,"
            + ExpenseColumn.KEY_ACCOUNTID + " TEXT," + ExpenseColumn.KEY_EXPENSEDATE + " TEXT,"
            + ExpenseColumn.KEY_EXPENSEEVENT + " TEXT" + ")";
    private static final String DROP_EXPENSE_TABLE = "DROP TABLE IF EXISTS " + TABLE_EXPENSE;

    private static final String CREATE_INCOME_TABLE = "CREATE TABLE " + TABLE_INCOME + "("
            + IncomeColumn._ID + " INTEGER PRIMARY KEY," + IncomeColumn.KEY_AMOUNTMONEY + " TEXT,"
            + IncomeColumn.KEY_INCOMECATEGORY + " TEXT," + IncomeColumn.KEY_DESCRIPTION + " TEXT,"
            + IncomeColumn.KEY_ACCOUNTID + " TEXT," + IncomeColumn.KEY_DATE + " TEXT,"
            + IncomeColumn.KEY_EVENT + " TEXT" + ")";
    private static final String DROP_INCOME_TABLE = "DROP TABLE IF EXISTS " + TABLE_INCOME;

    private static final String CREATE_BUDGET_TABLE = "CREATE TABLE " + TABLE_BUDGET + "("
            + BudgetCollumn._ID + " INTEGER PRIMARY KEY," + BudgetCollumn.KEY_ACCOUNTNAME + " TEXT,"
            + BudgetCollumn.KEY_AMOUNTMONEY + " TEXT" + ")";
    private static final String DROP_BUDGET_TABLE = "DROP TABLE IF EXISTS " + TABLE_BUDGET;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create Table
        sqLiteDatabase.execSQL(CREATE_ACCOUNTS_TABLE);
        sqLiteDatabase.execSQL(CREATE_EXPENSE_TABLE);
        sqLiteDatabase.execSQL(CREATE_INCOME_TABLE);
        sqLiteDatabase.execSQL(CREATE_BUDGET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //Upgrade Table
        sqLiteDatabase.execSQL(DROP_ACCOUNTS_TABLE);
        sqLiteDatabase.execSQL(DROP_EXPENSE_TABLE);
        sqLiteDatabase.execSQL(DROP_INCOME_TABLE);
        sqLiteDatabase.execSQL(DROP_BUDGET_TABLE);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
