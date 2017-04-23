package com.example.nguyennam.financialbook.recordtab;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nguyennam.financialbook.MainActivity;
import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.utils.CalculatorSupport;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.text.NumberFormat;
import java.util.Locale;


public class Calculator extends Fragment implements View.OnClickListener {

    Context context;
    Button btn0;
    Button btnDot;
    Button btn000;
    Button btnEqual;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btnMinus;
    Button btn7;
    Button btn8;
    Button btn9;
    Button btnPlus;
    Button btnC;
    Button btnMulti;
    Button btnDivide;
    ImageButton btnBack;
    EditText edtTinh;
    TextView txtExpression;
    String temp = "";
    String inputNumber;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_calculator, container, false);
        edtTinh = (EditText) view.findViewById(R.id.edtDisplay);
        txtExpression = (TextView) view.findViewById(R.id.txtExpression);
        btn0 = (Button) view.findViewById(R.id.btnKey0);
        btn0.setOnClickListener(this);
        btnDot = (Button) view.findViewById(R.id.btnKeyDot);
        btnDot.setOnClickListener(this);
        btn000 = (Button) view.findViewById(R.id.btnKey000);
        btn000.setOnClickListener(this);
        btnEqual = (Button) view.findViewById(R.id.btnKeyEqual);
        btnEqual.setOnClickListener(this);
        btn1 = (Button) view.findViewById(R.id.btnKey1);
        btn1.setOnClickListener(this);
        btn2 = (Button) view.findViewById(R.id.btnKey2);
        btn2.setOnClickListener(this);
        btn3 = (Button) view.findViewById(R.id.btnKey3);
        btn3.setOnClickListener(this);
        btn4 = (Button) view.findViewById(R.id.btnKey4);
        btn4.setOnClickListener(this);
        btn5 = (Button) view.findViewById(R.id.btnKey5);
        btn5.setOnClickListener(this);
        btn6 = (Button) view.findViewById(R.id.btnKey6);
        btn6.setOnClickListener(this);
        btnMinus = (Button) view.findViewById(R.id.btnKeyMinus);
        btnMinus.setOnClickListener(this);
        btn7 = (Button) view.findViewById(R.id.btnKey7);
        btn7.setOnClickListener(this);
        btn8 = (Button) view.findViewById(R.id.btnKey8);
        btn8.setOnClickListener(this);
        btn9 = (Button) view.findViewById(R.id.btnKey9);
        btn9.setOnClickListener(this);
        btnPlus = (Button) view.findViewById(R.id.btnKeyPlus);
        btnPlus.setOnClickListener(this);
        btnC = (Button) view.findViewById(R.id.btnKeyC);
        btnC.setOnClickListener(this);
        btnMulti = (Button) view.findViewById(R.id.btnKeyMulti);
        btnMulti.setOnClickListener(this);
        btnDivide = (Button) view.findViewById(R.id.btnKeyDivide);
        btnDivide.setOnClickListener(this);
        btnBack = (ImageButton) view.findViewById(R.id.btnKeyBack);
        btnBack.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        edtTinh.setText(FileHelper.readFile(context, Constant.TEMP_CALCULATOR));
    }

    @Override
    public void onClick(View v) {
        String xong = "OK";
        String bang = "=";
        switch (v.getId()) {
            case R.id.btnKey0:
                formatInput("0");
                break;
            case R.id.btnKey1:
                formatInput("1");
                break;
            case R.id.btnKey2:
                formatInput("2");
                break;
            case R.id.btnKey3:
                formatInput("3");
                break;
            case R.id.btnKey4:
                formatInput("4");
                break;
            case R.id.btnKey5:
                formatInput("5");
                break;
            case R.id.btnKey6:
                formatInput("6");
                break;
            case R.id.btnKey7:
                formatInput("7");
                break;
            case R.id.btnKey8:
                formatInput("8");
                break;
            case R.id.btnKey9:
                formatInput("9");
                break;
            case R.id.btnKey000:
                formatInput("000");
                break;
            case R.id.btnKeyDot:
                inputNumber = edtTinh.getText().toString() + ",";
                edtTinh.setText(inputNumber);
                break;
            case R.id.btnKeyC:
                edtTinh.setText("");
                txtExpression.setText("");
                temp = "";
                inputNumber = "";
                break;
            case R.id.btnKeyBack:
                if ("".equals(String.valueOf(txtExpression.getText()))) {
                    inputNumber = CalculatorSupport.cutLastNumber(String.valueOf(edtTinh.getText()));
                    clickBackButton();
                } else {
                    inputNumber = CalculatorSupport.cutLastNumber(String.valueOf(txtExpression.getText()));
                    clickBackButton();
                    txtExpression.setText("");
                }
                break;
            case R.id.btnKeyPlus:
                clickOperationButton("+");
                break;
            case R.id.btnKeyMinus:
                clickOperationButton("-");
                break;
            case R.id.btnKeyMulti:
                clickOperationButton("*");
                break;
            case R.id.btnKeyDivide:
                clickOperationButton("/");
                break;
            case R.id.btnKeyEqual:
                if (btnEqual.getText().toString().equals("=")) {
                    String result = CalculatorSupport.formatExpression(String.valueOf(edtTinh.getText()));
                    result = Double.toString((double) Math.round(CalculatorSupport.eval(result) * 10) / 10);
                    btnEqual.setText(xong);
                    NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
                    result = nf.format(Double.parseDouble(result));
                    edtTinh.setText(result);
                    txtExpression.setText(inputNumber);
                } else {
                    FileHelper.writeFile(context, Constant.TEMP_CALCULATOR, edtTinh.getText().toString());
                    getActivity().getSupportFragmentManager().popBackStack();
//                    ((MainActivity) context).replaceFragment(new RecordMain(), false);
                }
                break;
        }

        //doi phim = thanh chu xong
        String curText = String.valueOf(edtTinh.getText());
        if ((!curText.contains("+")) && (!curText.contains("-"))
                && (!curText.contains("*")) && (!curText.contains("/"))) {
            btnEqual.setText(xong);
        } else {
            btnEqual.setText(bang);
        }
        edtTinh.setSelection(edtTinh.length()); //set cursor cuoi text
    }

    private void clickOperationButton(String operation) {
        if (CalculatorSupport.isLastOperation(inputNumber)) {
            temp = inputNumber.substring(0, inputNumber.length() - 1) + operation;
            inputNumber = temp;
            edtTinh.setText(temp);
        } else {
            temp = edtTinh.getText().toString() + operation;
            inputNumber = temp;
            edtTinh.setText(temp);
        }
    }

    private void clickBackButton() {
        String numberAfter = CalculatorSupport.cutNumberAfter(inputNumber);
        if ("".equals(numberAfter)) {
            edtTinh.setText(inputNumber);
        } else if (" ".equals(numberAfter)) {
            inputNumber = CalculatorSupport.formatNumber(inputNumber);
            edtTinh.setText(inputNumber);
        } else {
            numberAfter = CalculatorSupport.formatNumber(numberAfter);
            for (int i = inputNumber.length(); i > 0; i--) {
                if ("+".equals(String.valueOf(inputNumber.charAt(i - 1)))
                        || "-".equals(String.valueOf(inputNumber.charAt(i - 1)))
                        || "*".equals(String.valueOf(inputNumber.charAt(i - 1)))
                        || "/".equals(String.valueOf(inputNumber.charAt(i - 1)))) {
                    temp = inputNumber.substring(0, i);
                    break;
                }
            }
            inputNumber = temp.concat(numberAfter);
            edtTinh.setText(inputNumber);
        }
    }

    private void formatInput(String str) {
        inputNumber = edtTinh.getText().toString() + str;
        String afterNumber = CalculatorSupport.cutNumberAfter(inputNumber);
        //if no operation -> no after number
        if ("".equals(afterNumber) || " ".equals(afterNumber)) {
            inputNumber = CalculatorSupport.formatNumber(inputNumber);
            edtTinh.setText(inputNumber);
        } else {
            afterNumber = CalculatorSupport.formatNumber(afterNumber);
            inputNumber = temp.concat(afterNumber);
            edtTinh.setText(inputNumber);
        }
    }
}
