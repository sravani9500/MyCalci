package com.example.mycalci;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText tvInput;
    private TextView tvOutput;
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private Button add, sub, div, multi, mod;
    private Button clear, dot, brac, equalTo;
    private ImageButton backSpace;
    private boolean stateError;
    private boolean number;
    private boolean lastDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeButtons();
        setOnclick();

        tvInput.setRawInputType(InputType.TYPE_NULL);
        tvInput.addTextChangedListener(textWatcher);

    }

    private void initializeButtons() {
        this.tvInput = findViewById(R.id.tvInput);
        this.tvOutput = findViewById(R.id.tvOutput);
        this.btn0 = findViewById(R.id.btn0);
        this.btn1 = findViewById(R.id.btn1);
        this.btn2 = findViewById(R.id.btn2);
        this.btn3 = findViewById(R.id.btn3);
        this.btn4 = findViewById(R.id.btn4);
        this.btn5 = findViewById(R.id.btn5);
        this.btn6 = findViewById(R.id.btn6);
        this.btn7 = findViewById(R.id.btn7);
        this.btn8 = findViewById(R.id.btn8);
        this.btn9 = findViewById(R.id.btn9);
        this.mod = findViewById(R.id.btnMod);
        this.add = findViewById(R.id.btnAdd);
        this.sub = findViewById(R.id.btnSub);
        this.multi = findViewById(R.id.btnMulti);
        this.div = findViewById(R.id.btnDiv);
        this.brac = findViewById(R.id.btnB);
        this.backSpace = findViewById(R.id.btnBS);
        this.clear = findViewById(R.id.C);
        this.equalTo = findViewById(R.id.equalTo);
        this.dot = findViewById(R.id.dot);

    }

    private void setOnclick() {
        this.btn0.setOnClickListener(this);
        this.btn1.setOnClickListener(this);
        this.btn2.setOnClickListener(this);
        this.btn3.setOnClickListener(this);
        this.btn4.setOnClickListener(this);
        this.btn5.setOnClickListener(this);
        this.btn6.setOnClickListener(this);
        this.btn7.setOnClickListener(this);
        this.btn8.setOnClickListener(this);
        this.btn9.setOnClickListener(this);
        this.mod.setOnClickListener(this);
        this.add.setOnClickListener(this);
        this.multi.setOnClickListener(this);
        this.sub.setOnClickListener(this);
        this.div.setOnClickListener(this);
        this.dot.setOnClickListener(this);
        this.equalTo.setOnClickListener(this);
        this.clear.setOnClickListener(this);
        this.backSpace.setOnClickListener(this);
        this.brac.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int Id = v.getId();
        switch (Id) {
            case R.id.btn0:
                append("0");
                number = true;
                break;
            case R.id.btn1:
                append("1");
                number = true;
                break;
            case R.id.btn2:
                append("2");
                number = true;
                break;
            case R.id.btn3:
                append("3");
                number = true;
                break;
            case R.id.btn4:
                append("4");
                number = true;
                break;
            case R.id.btn5:
                append("5");
                number = true;
                break;
            case R.id.btn6:
                append("6");
                number = true;
                break;
            case R.id.btn7:
                append("7");
                number = true;
                break;
            case R.id.btn8:
                append("8");
                number = true;
                break;
            case R.id.btn9:
                append("9");
                number = true;
                break;
            case R.id.btnMod:
                if (!isEmpty() && number)
                    append("%");
                break;
            case R.id.btnAdd:
                if (!isEmpty())
                    if (endsWithOperatore())
                        replace("+");
                    else
                        append("+");
                number = false;
                lastDot = false;
                break;
            case R.id.btnSub:
                if (endsWithOperatore())
                    replace("-");
                else
                    append("-");
                number = false;
                lastDot = false;
                break;
            case R.id.btnMulti:
                if (!isEmpty())
                    if (endsWithOperatore())
                        replace("x");
                    else
                        append("x");
                number = false;
                lastDot = false;
                break;
            case R.id.btnDiv:
                if (!isEmpty())
                    if (endsWithOperatore())
                        replace("\u00F7");
                    else
                        append("\u00F7");
                number = false;
                lastDot = false;
                break;
            case R.id.dot:
                if (number && !stateError && !lastDot) {
                    append(".");
                    number = false;
                    lastDot = true;
                } else if (isEmpty()) {
                    append("0.");
                    number = false;
                    lastDot = true;
                }
                break;
            case R.id.btnBS:
                delete();
                break;
            case R.id.C:
                clear();
                break;
            case R.id.btnB:
                bracket();
                break;
            case R.id.equalTo:
                calcule(true);
                break;
            default:
                break;
        }

    }

    private void bracket() {
        if ((!stateError && !isEmpty() && !endsWithbra() && number) || isclosed()) {
            append("x(");
        } else if (isEmpty() || endsWithOperatore() || endsWithbra()) {
            append("(");
        } else if (!isEmpty() && !endsWithbra()) {
            append(")");
        }
    }

    private boolean endsWithbra() {
        return getinput().endsWith("(");
    }

    private boolean isclosed() {
        return getinput().endsWith(")");
    }

    private boolean endsWithOperatore() {
        return getinput().endsWith("+") || getinput().endsWith("-") || getinput().endsWith("/") || getinput().endsWith("x");
    }

    private void replace(String str) {
        tvInput.getText().replace(getinput().length()-1, getinput().length(), str);
    }

    private void clear() {
        lastDot = false;
        number = false;
        stateError = false;
        tvInput.getText().clear();
    }

    private void append(String str) {
        this.tvInput.getText().append(str);
    }

    private void delete() {
        if (!isEmpty()) {
            this.tvInput.getText().delete(getinput().length()-1, getinput().length());

        } else clear();
    }

    private String getinput() {
        return this.tvInput.getText().toString();
    }

    private boolean isEmpty() {
        return getinput().isEmpty();
    }

    private void calcule(boolean isequlclick) {

        String input = getinput();
        try {
            if (!isEmpty() && !endsWithOperatore()) {
                if (input.contains("x")) {
                    input = input.replaceAll("x", "*");
                }

                if(input.contains("\u00F7")){
                    input=input.replaceAll("\u00F7","/");
                }
                Expression expression = new ExpressionBuilder(input).build();
                double result = expression.evaluate();
                if (isequlclick) {
                    tvInput.setText(String.valueOf(result));
                    tvOutput.setText("");
                } else
                    tvOutput.setText(String.valueOf(result));
            } else tvOutput.setText("");
        } catch (Exception e) {
            stateError = true;
            number = false;
        }

    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            calcule(false);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


}