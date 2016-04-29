package com.calculate;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by bone on 16/4/21.
 * 摘自：http://www.cnblogs.com/masterRoshi/archive/2011/09/07/2170295.html
 * 不支持括号优先级，计算结果不太正确。
 * 以后再自己写
 */
public class Calculate {
    private Stack<String> operandStack = new Stack<String>();//操作数堆栈
    private Stack<String> operatorStack = new Stack<String>();//操作符堆栈
    private Stack<Double> resultStack = new Stack<>();//计算结果
    private String expression;//算数表达式
    private double result = 0d;//计算结果
    private String resultString = "";
    private Map<String, Integer> priorityMap = new HashMap<String, Integer>();//用于存储操作符优先级的Map

    //初始化优先级约定(可根据计算的复杂程度扩展)
    public Calculate() {
        priorityMap.put("+", 1);
        priorityMap.put("-", 1);
        priorityMap.put("×", 2);
        priorityMap.put("÷", 2);
        priorityMap.put("#", 0);
        priorityMap.put("(", 0);
        priorityMap.put(")", 0);
        operatorStack.push("#");

    }

    public int getPriority(String op)//得到一个操作符的优先级
    {
        return priorityMap.get(op);
    }

    public boolean highPriority(String op)//判断操作符的优先级在堆栈里面是否最为高
    {
        int opPri = getPriority(op);//当前操作符的优先级
        String s = operatorStack.get(operatorStack.size() - 1);
        int priority = getPriority(s);
        return opPri > priority;
    }

    public double cal() {
        for (String s : operandStack) {
            if (s.matches("^[0-9]+.?[0-9]*$")) {
                resultStack.push(Double.parseDouble(s));
            } else {
                popOperand(s);
            }
        }
        return result;

    }

    public void popOperand(String operator) {
        double d1 = 0;
        double d2 = 0;
        if (resultStack.size() > 0) {
            d1 = resultStack.pop();

        }
        if (resultStack.size() > 0) {
            d2 = resultStack.pop();
        }
        if (operator.equals("+"))
            result = d2 + d1;
        if (operator.equals("-"))
            result = d2 - d1;
        if (operator.equals("×"))
            result = d2 * d1;
        if (operator.equals("÷")) {
            if (d1 != 0)
                result = d2 / d1;
            else
                resultString = "NaN";
        }
        if (resultString != "NaN") {
            NumberFormat ddf1 = NumberFormat.getNumberInstance();

            ddf1.setMaximumFractionDigits(9);
            resultString = ddf1.format(result);

        }
        resultStack.push(result);

    }

    public void expToIpn() {
        int index = 0;
        int end = 0;
        for (int i = 0; i < expression.length(); i++) {
            String temps = String.valueOf(expression.charAt(i));
            if (temps.matches("[0-9.]"))//检查是否是数字
            {
                end++;
            } else if (!temps.equals("!")) {
                if (index < end) {
                    String tempOperand = expression.substring(index, end);//得到操作数
                    operandStack.push(tempOperand);
                }
                String op = expression.substring(end, ++end);
                if (op.equals("(")) {
                    operatorStack.push(op);
                } else if (op.equals(")")) {
                    while (true) {
                        String tmop = operatorStack.pop();
                        if (tmop.equals("(")) {
                            break;
                        } else {
                            operandStack.push(tmop);
                        }
                    }
                } else {
                    String temop = operatorStack.get(operatorStack.size() - 1);
                    if (temop.equals("(")) {
                        operatorStack.push(op);
                    } else {
                        if (highPriority(op)) {
                            operatorStack.push(op);
                        } else {
                            while (true) {
                                String tmop = operatorStack.pop();
                                operandStack.push(tmop);
                                if (highPriority(op) || tmop.equals("(")) {
                                    operatorStack.push(op);
                                    break;
                                }
                            }
                        }
                    }
                }
                index = end;
            } else {
                if (index < end) {
                    String tempOperand = expression.substring(index, end);//得到操作数
                    operandStack.push(tempOperand.toString());
                }
            }

        }

        while (operatorStack.size() > 1) {
            String tmop = operatorStack.pop();
            operandStack.push(tmop);
        }
        System.out.println(operandStack);
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public double getResult() {
        return result;
    }

    public String getResultString() {
        return resultString;
    }
}
