package com.example.nguyennam.financialbook.utils;

import android.content.Context;
import android.util.Log;

import com.example.nguyennam.financialbook.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by NguyenNam on 4/10/2017.
 */

public class CalendarSupport {
    public static String getDateOfWeek(Context context, String input_date) {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        Date dt1 = null;
        try {
            dt1 = format1.parse(input_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt1);
        String[] days = context.getResources().getStringArray(R.array.day_of_week);
        String day = days[calendar.get(Calendar.DAY_OF_WEEK)];
        return day;
    }

    public static String getDateOfMonth (String input_date) {
        String[] dateOfMonth = input_date.split("/");
        return dateOfMonth[0];
    }

    public static String getMonth (String input_date) {
        for (int i = 0; i < input_date.length(); i++) {
            if ("/".equals(String.valueOf(input_date.charAt(i)))) {
                input_date = input_date.substring(i+1, input_date.length());
                break;
            }
        }
        return input_date;
    }

    public static void sortDateList(List<String> dateExpenseList, List<String> dateIncomeList) {
        // remove date duplicate
        dateExpenseList.removeAll(dateIncomeList);
        // add date income to expense
        dateExpenseList.addAll(dateIncomeList);
        // sort date from now to past
        Collections.sort(dateExpenseList, new Comparator<String>() {
            @Override
            public int compare(String arg1, String arg0) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                int compareResult = 0;
                try {
                    Date arg0Date = format.parse(arg0);
                    Date arg1Date = format.parse(arg1);
                    compareResult = arg0Date.compareTo(arg1Date);
                } catch (ParseException e) {
                    e.printStackTrace();
                    compareResult = arg0.compareTo(arg1);
                }
                return compareResult;
            }
        });
    }
}
