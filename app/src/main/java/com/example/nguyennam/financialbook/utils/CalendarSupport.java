package com.example.nguyennam.financialbook.utils;

import android.content.Context;
import android.content.Intent;

import com.example.nguyennam.financialbook.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


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
        return days[calendar.get(Calendar.DAY_OF_WEEK)];
    }

    public static String getDateOfMonth (String input_date) {
        String[] dateOfMonth = input_date.split("/");
        return dateOfMonth[0];
    }

    public static String getDateAndMonth(String input_date) {
        for (int i = input_date.length() - 1; i >= 0 ; i--) {
            if ("/".equals(String.valueOf(input_date.charAt(i)))) {
                input_date = input_date.substring(0, i);
                break;
            }
        }
        return input_date;
    }

    public static String getMonthOfYear(String input_date) {
        for (int i = 0; i < input_date.length(); i++) {
            if ("/".equals(String.valueOf(input_date.charAt(i)))) {
                input_date = input_date.substring(i+1, input_date.length());
                break;
            }
        }
        return input_date;
    }

    public static String getMonth(String input_date) {
        input_date = getMonthOfYear(input_date);
        for (int i = 0; i < input_date.length(); i++) {
            if ("/".equals(String.valueOf(input_date.charAt(i)))) {
                input_date = input_date.substring(0, i);
                break;
            }
        }
        return input_date;
    }

    public static String getQuarter(String input_date) {
        input_date = getMonth(input_date);
        switch (Integer.parseInt(input_date)) {
            case 1:
            case 2:
            case 3:
                return "I";
            case 4:
            case 5:
            case 6:
                return "II";
            case 7:
            case 8:
            case 9:
                return "III";
        }
        return "IV";
    }

    public static String getYear (String input_date) {
        input_date = getMonthOfYear(input_date);
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

    public static void sortDateList(List<String> dateExpenseList) {
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

    public static Date convertStringToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateConvert = null;
        try {
            dateConvert = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateConvert;
    }

}
