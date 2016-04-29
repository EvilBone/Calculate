package com.calculate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText;//输入
    private TextView textView;//计算表达式
    private Button clear;//清除
    private Button left_bracket;//左括号
    private Button right_bracket;//右括号
    private Button back;//退格
    private Button division;//除法
    private Button multiply;//乘法
    private Button subtract;//减法
    private Button op_plus;//加法
    private Button equel;//等于
    private Button point;//小数点
    private Button nine;//9
    private Button eight;//8
    private Button seven;//7
    private Button six;//6
    private Button five;//5
    private Button four;//4
    private Button three;//3
    private Button two;//2
    private Button one;//1
    private Button zero;//0
    private StringBuffer expression = new StringBuffer();//
    private String result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //控件对象
        editText = (EditText) findViewById(R.id.edit);
        textView = (TextView) findViewById(R.id.text_view);
        clear = (Button) findViewById(R.id.clear);
        left_bracket = (Button) findViewById(R.id.left_bracket);
        right_bracket = (Button) findViewById(R.id.right_bracket);
        nine = (Button) findViewById(R.id.nine);
        eight = (Button) findViewById(R.id.eight);
        seven = (Button) findViewById(R.id.seven);
        six = (Button) findViewById(R.id.six);
        five = (Button) findViewById(R.id.five);
        four = (Button) findViewById(R.id.four);
        three = (Button) findViewById(R.id.three);
        two = (Button) findViewById(R.id.two);
        one = (Button) findViewById(R.id.one);
        zero = (Button) findViewById(R.id.zero);
        back = (Button) findViewById(R.id.back);
        multiply = (Button) findViewById(R.id.multiply);
        division = (Button) findViewById(R.id.division);
        subtract = (Button) findViewById(R.id.subtract);
        op_plus = (Button) findViewById(R.id.op_plus);
        equel = (Button) findViewById(R.id.equel);
        point = (Button) findViewById(R.id.point);

        editText.setSelection(editText.getText().length());
        editText.setTextIsSelectable(false);
        editText.setMaxLines(1);


        //点击事件
        clear.setOnClickListener(this);
        left_bracket.setOnClickListener(this);
        right_bracket.setOnClickListener(this);
        nine.setOnClickListener(this);
        eight.setOnClickListener(this);
        seven.setOnClickListener(this);
        six.setOnClickListener(this);
        five.setOnClickListener(this);
        four.setOnClickListener(this);
        three.setOnClickListener(this);
        two.setOnClickListener(this);
        one.setOnClickListener(this);
        zero.setOnClickListener(this);
        back.setOnClickListener(this);
        multiply.setOnClickListener(this);
        division.setOnClickListener(this);
        subtract.setOnClickListener(this);
        op_plus.setOnClickListener(this);
        equel.setOnClickListener(this);
        point.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear:
                if (expression.length() > 0) {
                    expression.delete(0, expression.length());
                }
                textView.setText("");
                editText.setText(expression);
                break;
            case R.id.left_bracket:
                arrangeText('(');
                break;
            case R.id.right_bracket:
                arrangeText(')');
                break;
            case R.id.back:
                if (expression.length() > 0) {
                    expression.deleteCharAt(expression.length() - 1);
                    editText.setText(expression.toString());
                    editText.setSelection(editText.getText().length());
                } else {
                    editText.setText("");
                }
                break;
            case R.id.nine:
                arrangeText('9');
                break;
            case R.id.eight:
                arrangeText('8');
                break;
            case R.id.seven:
                arrangeText('7');
                break;
            case R.id.six:
                arrangeText('6');
                break;
            case R.id.five:
                arrangeText('5');
                break;
            case R.id.four:
                arrangeText('4');
                break;
            case R.id.three:
                arrangeText('3');
                break;
            case R.id.two:
                arrangeText('2');
                break;
            case R.id.one:
                arrangeText('1');
                break;
            case R.id.zero:
                arrangeText('0');
                break;
            case R.id.multiply:
                arrangeText('×');
                break;
            case R.id.division:
                arrangeText('÷');
                break;
            case R.id.op_plus:
                arrangeText('+');
                break;
            case R.id.subtract:
                arrangeText('-');
                break;
            case R.id.point:
                arrangeText('.');
                break;
            case R.id.equel:
                calculate();
                break;
        }
    }

    private void arrangeText(char c) {
        expression.append(c);
        editText.setText(expression.toString());
        editText.setSelection(editText.getText().length());

    }

    private void calculate() {
        textView.setText(expression.toString());
        Calculate calculate = new Calculate();
        expression.append('!');
        calculate.setExpression(expression.toString());
        calculate.expToIpn();
        calculate.cal();
        String result = calculate.getResultString();
        if (expression.length() > 0) {
            expression.delete(0, expression.length());
        }
        if (!result.equals("NaN"))
            expression.append(result);
        editText.setText(result);
        editText.setSelection(editText.getText().length());
    }
}
