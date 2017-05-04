package com.example.nguyennam.financialbook.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class FileHelper {

    public static String readFile(Context context, String filename) {
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        } catch (IOException e) {
            return "";
        }
    }

    public static void writeFile(Context context, String fileName, String data) {
        FileOutputStream fos;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(Context context, String filename) {
        File file = new File(context.getFilesDir(), filename);
        file.delete();
    }

    public static void clearTempFile(Context context) {
        FileHelper.deleteFile(context, Constant.TEMP_CALCULATOR);
        FileHelper.deleteFile(context, Constant.TEMP_CATEGORY);
        FileHelper.deleteFile(context, Constant.TEMP_CATEGORY_CHILD);
        FileHelper.deleteFile(context, Constant.TEMP_DESCRIPTION);
        FileHelper.deleteFile(context, Constant.TEMP_EVENT);
        FileHelper.deleteFile(context, Constant.TEMP_ACCOUNT_ID_EDIT);
        FileHelper.deleteFile(context, Constant.TEMP_CALCULATOR_EDIT);
        FileHelper.deleteFile(context, Constant.TEMP_ISEXPENSE);
        FileHelper.deleteFile(context, Constant.TEMP_INCOME_ID);
        FileHelper.deleteFile(context, Constant.TEMP_EXPENSE_ID);
        FileHelper.deleteFile(context, Constant.TEMP_DATE);
        FileHelper.deleteFile(context, Constant.TEMP_VIEW_BY);
        FileHelper.deleteFile(context, Constant.TEMP_BUDGET_DATE);
        FileHelper.deleteFile(context, Constant.TEMP_BUDGET_ID);
    }
}
