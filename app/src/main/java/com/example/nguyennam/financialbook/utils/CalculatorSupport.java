package com.example.nguyennam.financialbook.utils;


import java.text.NumberFormat;
import java.util.Locale;

public class CalculatorSupport {

    public static String formatNumber(String str) {
        str = formatExpression(str);
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        return nf.format(Double.parseDouble(str));
    }

    public static String cutNumberAfter(String str) {
        String after = str;
        for (int i = str.length(); i > 0; i--) {
            if ("+".equals(String.valueOf(str.charAt(i - 1)))
                    || "-".equals(String.valueOf(str.charAt(i - 1)))
                    || "*".equals(String.valueOf(str.charAt(i - 1)))
                    || "/".equals(String.valueOf(str.charAt(i - 1)))) {
                after = str.substring(i, str.length());
                break;
            } else {
                //no operation
                after = " ";
            }
        }
        return after;
    }

    public static String formatExpression(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (".".equals(String.valueOf(str.charAt(i)))) {
                String[] mangSo = str.split("\\.");
                str = "";
                for (String mang : mangSo) {
                    str = str.concat(mang);
                }
            }
        }
        for (int i = 0; i < str.length(); i++) {
            if (",".equals(String.valueOf(str.charAt(i)))) {
                str = str.replace(",",".");
            }
        }
        return str;
    }

    public static String cutLastNumber(String str) {
        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public static boolean isLastOperation(String str){
        return (str.endsWith("+")||str.endsWith("-")
                ||str.endsWith("*")||str.endsWith("/"));
    }

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus
                double x = 0;
                int startPos = this.pos;
                if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                }
//                if (eat('(')) { // parentheses
//                    x = parseExpression();
//                    eat(')');
//                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
//                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
//                    x = Double.parseDouble(str.substring(startPos, this.pos));
//                } else if (ch >= 'a' && ch <= 'z') { // functions
//                    while (ch >= 'a' && ch <= 'z') nextChar();
//                    String func = str.substring(startPos, this.pos);
//                    x = parseFactor();
//                    if (func.equals("sqrt")) x = Math.sqrt(x);
//                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
//                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
//                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
//                    else throw new RuntimeException("Unknown function: " + func);
//                } else {
//                    throw new RuntimeException("Unexpected: " + (char)ch);
//                }
//
//                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation
                return x;
            }
        }.parse();
    }
}
