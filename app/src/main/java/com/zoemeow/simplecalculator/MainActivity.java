package com.zoemeow.simplecalculator;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zoemeow.simplecalculator.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;

    float result = 0.0F;
    String input = "0";
    Boolean inputModified = false;
    Integer mathType = -1;

    String history = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        binding.btn0.setOnClickListener(this);
        binding.btn1.setOnClickListener(this);
        binding.btn2.setOnClickListener(this);
        binding.btn3.setOnClickListener(this);
        binding.btn4.setOnClickListener(this);
        binding.btn5.setOnClickListener(this);
        binding.btn6.setOnClickListener(this);
        binding.btn7.setOnClickListener(this);
        binding.btn8.setOnClickListener(this);
        binding.btn9.setOnClickListener(this);
        binding.btnPlus.setOnClickListener(this);
        binding.btnMinus.setOnClickListener(this);
        binding.btnMulti.setOnClickListener(this);
        binding.btnDivide.setOnClickListener(this);
        binding.btnC.setOnClickListener(this);
        binding.btnDot.setOnClickListener(this);
        binding.btnCalc.setOnClickListener(this);
        binding.btnDel.setOnClickListener(this);
        binding.btnPM.setOnClickListener(this);
    }

    private void calcuateAndDisplay() {
        float previousResult = this.result;
        float num = Float.parseFloat(input);

        if (this.inputModified)
            switch (this.mathType) {
                case 0:
                    this.result = previousResult + num;
                    break;
                case 1:
                    this.result = previousResult - num;
                    break;
                case 2:
                    this.result = previousResult * num;
                    break;
                case 3:
                    this.result = previousResult / num;
                    break;
                default:
                    this.result = num;
                    break;
            }

        ArrayList<Character> query = new ArrayList<>(Arrays.asList(new Character[] {'0', '.'}));

        String str1 = Float.toString(previousResult);
        while (str1.indexOf('.') > -1 && query.contains(str1.charAt(str1.length() - 1))) {
            str1 = str1.substring(0, str1.length() - 1);
            if (str1.length() == 0) {
                str1 = "0";
                break;
            }
        }

        String str2 = Float.toString(num);
        while (str2.indexOf('.') > -1 && query.contains(str2.charAt(str2.length() - 1))) {
            str2 = str2.substring(0, str2.length() - 1);
            if (str2.length() == 0) {
                str2 = "0";
                break;
            }
        }

        String str3 = Float.toString(this.result);
        while (str3.indexOf('.') > -1 && query.contains(str3.charAt(str3.length() - 1))) {
            str3 = str3.substring(0, str3.length() - 1);
            if (str3.length() == 0) {
                str3 = "0";
                break;
            }
        }

        if (this.mathType >= 0 && this.mathType <= 3 && this.inputModified)
            appendHistory(String.format("%s%s%s=%s",
                    str1,
                    this.mathType == 0 ? "+" : this.mathType == 1 ? "-" : this.mathType == 2 ? "*" : "/",
                    str2,
                    str3)
            );

        this.mathType = -1;
        this.input = str3;
        this.inputModified = false;
    }

    private void appendHistory(String text) {
        history += " " + text;
        // TODO: Refresh view here
    }

    private void clearHistory() {
        history = "";
        // TODO: Refresh view here
    }

    private void appendInput(Character text) {
        if (this.input == "0" || this.inputModified == false)
            this.input = "";

        if (text == '.' && this.input.indexOf(text) >= 0) {

        }
        else {
            this.input += text;
            this.inputModified = true;
        }

        if (this.input.length() == 0) {
            this.input = "0";
            this.inputModified = false;
        }
    }

    private void pmInput() {
        if (this.input == "0" || this.input.length() == 0)
            return;

        if (this.input.indexOf('-') > -1)
            this.input = this.input.substring(1);
        else this.input = '-' + this.input;
        this.inputModified = true;
    }

    private void delInputOneChar() {
        if (this.input.length() > 0) {
            this.input = this.input.substring(0, this.input.length() - 1);
        }

        if (this.input.length() == 0) {
            this.input = "0";
            this.inputModified = false;
        }
    }

    private void clearInput() {
        if (this.input.length() == 0)
            clearStatus();
        else {
            this.input = "0";
            this.inputModified = false;
        }
    }

    private void clearStatus() {
        this.result = 0;
        this.input = "0";
        this.inputModified = false;
        this.mathType = -1;
        this.history = "";
    }

    private void setMathType(Integer mathType) {
        calcuateAndDisplay();
        this.mathType = mathType;
    }

    private void updateView() {
        binding.editTextInput.setText(input);
        binding.editTextHistory.setText(history);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDel: delInputOneChar(); break;
            case R.id.btnPM: pmInput(); break;
            case R.id.btnC: clearInput(); break;
            case R.id.btn0: appendInput('0'); break;
            case R.id.btn1: appendInput('1'); break;
            case R.id.btn2: appendInput('2'); break;
            case R.id.btn3: appendInput('3'); break;
            case R.id.btn4: appendInput('4'); break;
            case R.id.btn5: appendInput('5'); break;
            case R.id.btn6: appendInput('6'); break;
            case R.id.btn7: appendInput('7'); break;
            case R.id.btn8: appendInput('8'); break;
            case R.id.btn9: appendInput('9'); break;
            case R.id.btnDot: appendInput('.'); break;
            case R.id.btnPlus: setMathType(0); break;
            case R.id.btnMinus: setMathType(1); break;
            case R.id.btnMulti: setMathType(2); break;
            case R.id.btnDivide: setMathType(3); break;
            case R.id.btnCalc: calcuateAndDisplay(); break;
        }

        updateView();
    }

}